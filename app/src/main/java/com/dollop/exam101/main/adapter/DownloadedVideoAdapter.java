package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.PdfVideoTable;
import com.dollop.exam101.databinding.ItemDownloadedVideoBinding;

import java.util.ArrayList;

public class DownloadedVideoAdapter extends RecyclerView.Adapter<DownloadedVideoAdapter.MyHolder> {
    Context context;
   ArrayList<PdfVideoTable> videoList;
    int index=-1;

    public DownloadedVideoAdapter(Context context,ArrayList<PdfVideoTable> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public DownloadedVideoAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDownloadedVideoBinding binding = ItemDownloadedVideoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedVideoAdapter.MyHolder holder, @SuppressLint("RecycleView") int position) {
        PdfVideoTable pdfVideoTable = videoList.get(position);
        holder.binding.tvTopicName.setText(pdfVideoTable.topicName);
        holder.binding.tvTopicDescriptioin.setText(HtmlCompat.fromHtml(pdfVideoTable.topicDescription, 0));
        holder.binding.tvDate.setText(pdfVideoTable.date);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemDownloadedVideoBinding binding;

        public MyHolder(@NonNull ItemDownloadedVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
