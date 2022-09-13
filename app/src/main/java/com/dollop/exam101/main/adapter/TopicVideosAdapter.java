package com.dollop.exam101.main.adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.VideoBinding;
import com.dollop.exam101.main.activity.VideoViewActivity;
import com.dollop.exam101.main.model.Video;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TopicVideosAdapter extends RecyclerView.Adapter<TopicVideosAdapter.MyViewHolder> {
    private final Bundle bundle;
    ArrayList<Video> videoList;
    Activity context;

    public TopicVideosAdapter(ArrayList<Video> videoList, Activity context, Bundle bundle) {
        this.videoList = videoList;
        this.context = context;
        this.bundle = bundle;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoBinding binding = VideoBinding.inflate(LayoutInflater.from(context));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.binding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showPDFDialog(videoList);
                //downloadVideo(video.url);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.videoUrl, video.url);
                bundle.putString(Constants.Key.urlType, Constants.Key.video);
                bundle.putString(Constants.Key.For, video.type);
                Utils.I(context, VideoViewActivity.class, bundle);

            }
        });
        Uri uri = Uri.parse(video.url);


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    void downloadVideo(String url) {
        URL u = null;
        InputStream is = null;

        try {
            u = new URL(url);
            is = u.openStream();
            HttpURLConnection huc = (HttpURLConnection) u.openConnection(); //to know the size of video
            int size = huc.getContentLength();

            if (huc != null) {
                String fileName = "FILE.mp4";
                String storagePath = Environment.getExternalStorageDirectory().toString();
                File f = new File(storagePath, fileName);

                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int len1 = 0;
                if (is != null) {
                    while ((len1 = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, len1);
                    }
                }
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                // just going to ignore this one
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final VideoBinding binding;

        public MyViewHolder(@NonNull VideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
