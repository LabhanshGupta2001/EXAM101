package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.main.model.NewsModel;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyNewsViewHolder> {
    private ArrayList<NewsModel> newsModelArrayList;
    private Context context;

    public NewsAdapter(Context context, ArrayList<NewsModel> newsModelArrayList) {
        this.newsModelArrayList = newsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.MyNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsAdapter.MyNewsViewHolder(ItemCurrentAffairsNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyNewsViewHolder holder, int position) {
        NewsModel newsModel = newsModelArrayList.get(position);
        holder.binding.tvTopic.setText(String.valueOf(newsModel.topic));
        holder.binding.tvNewsHeading.setText(String.valueOf(newsModel.newsHeading));
        holder.binding.tvDate.setText(String.valueOf(newsModel.date));
        holder.binding.tvTime.setText(String.valueOf(newsModel.time));
        holder.binding.ivNewsIcon.setImageResource(newsModel.image);
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }

    public static class MyNewsViewHolder extends RecyclerView.ViewHolder {
        private ItemCurrentAffairsNewsBinding binding;

        public MyNewsViewHolder(ItemCurrentAffairsNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
