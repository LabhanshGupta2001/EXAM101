package com.dollop.exam101.main.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemTopicPdfBinding;
import com.dollop.exam101.main.activity.PdfViewActivity;
import com.dollop.exam101.main.activity.VideoViewActivity;
import com.dollop.exam101.main.model.TopicDetailModel;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TopicPdfadapter extends RecyclerView.Adapter<TopicPdfadapter.MyViewHolder> {
    TopicDetailModel topicDetailModel;
    Context context;
    private String fileName;
    private String For;

    public TopicPdfadapter(TopicDetailModel topicDetailModel, Context context, String For) {
        this.topicDetailModel = topicDetailModel;
        this.context = context;
        this.For = For;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopicPdfBinding binding = ItemTopicPdfBinding.inflate(LayoutInflater.from(context));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (For == "pdf") {
            String pdf = topicDetailModel.pdfFile.get(position);
            holder.binding.tvUpload.setText(pdf);
            holder.binding.cvPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  showPDFDialog(pdf);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.Key.pdf, pdf);
                    bundle.putString(Constants.Key.urlType, Constants.Key.pdf);
                    Utils.I(context, PdfViewActivity.class, bundle);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (For == "video") {
            return topicDetailModel.video.size();
        }
        if (For == "pdf") {
            return topicDetailModel.pdfFile.size();
        }
        return 0;
    }

    private void getFileName(String uri) {
        Cursor returnCursor =
                context.getContentResolver().query(Uri.parse(uri), null, null, null, null);
        try {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            fileName = returnCursor.getString(nameIndex);

            Utils.E("file name : " + fileName);
        } catch (Exception e) {
            Utils.E("error: " + e);
            //handle the failure cases here
        } finally {
            returnCursor.close();
        }
    }

    public void showPDFDialog(String pdf) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        assert dialog.getWindow() != null;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pdf_dialog);
        PDFView pdfView = dialog.findViewById(R.id.pdfView);
        Button cancelButton = dialog.findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        AsyncTask.execute(() -> {
            try {
                final InputStream input = new URL(Const.Url.HOST_URL + pdf).openStream();
                ((Activity) context).runOnUiThread(() -> pdfView.fromStream(input)
                        .enableSwipe(true)
                        .fitEachPage(true)
                        .pageFitPolicy(FitPolicy.BOTH)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(context))
                        .load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        dialog.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemTopicPdfBinding binding;

        public MyViewHolder(@NonNull ItemTopicPdfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

/*
    private void displayFromUri(ComplaintModel model) {
        AsyncTask.execute(() -> {
            try {


                final InputStream input = new URL(Const.Url.HOST_URL + model.attachment).openStream();
                ((Activity)context).runOnUiThread(() -> bottomsheetRaiseComplaintsBinding.pvImagePdf.fromStream(input)
                        .enableSwipe(true)
                        .fitEachPage(true)
                        .pageFitPolicy(FitPolicy.BOTH)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(context))
                        .load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
*/
}
