package com.dollop.exam101.main.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;

public class TopicVideosAdapter extends RecyclerView.Adapter<TopicVideosAdapter.MyViewHolder> {
    ArrayList<Video> videoList;
    Activity context;

    public TopicVideosAdapter(ArrayList<Video> videoList, Activity context) {
        this.videoList = videoList;
        this.context = context;
    }

/*
    public static Bitmap createThumbnail(Activity activity, Uri uri) {
        MediaMetadataRetriever mediaMetadataRetriever = null;
        Bitmap bitmap = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(activity, uri);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
*/

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final VideoBinding binding;

        public MyViewHolder(@NonNull VideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
