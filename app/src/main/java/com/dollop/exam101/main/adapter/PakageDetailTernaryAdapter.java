package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPackagesDetailTernaryBinding;
import com.dollop.exam101.main.model.TopicDetailModel;

import java.util.ArrayList;

public class PakageDetailTernaryAdapter extends RecyclerView.Adapter<PakageDetailTernaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<TopicDetailModel> topicDetailModels;

    public PakageDetailTernaryAdapter(Context context, ArrayList<TopicDetailModel> list) {
        this.context = context;
        this.topicDetailModels = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPackagesDetailTernaryBinding binding = ItemPackagesDetailTernaryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TopicDetailModel topicDetailModel = topicDetailModels.get(position);
        holder.binding.tvTopic.setText(topicDetailModel.topicName);
    }

    @Override
    public int getItemCount() {
        return topicDetailModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPackagesDetailTernaryBinding binding;

        public MyViewHolder(@NonNull ItemPackagesDetailTernaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
