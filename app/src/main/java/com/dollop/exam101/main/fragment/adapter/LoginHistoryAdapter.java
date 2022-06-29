package com.dollop.exam101.main.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.MyViewHolder> {
    Context context;
    List<String> list;
    int row_index = -1;
    private Boolean dropdown = true;


    public LoginHistoryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoginHistoryBinding binding = ItemLoginHistoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dropdown) {
                    holder.binding.llLogoutDevice.setVisibility(View.VISIBLE);
                    holder.binding.ivUpperarrow.animate().rotation(180).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.llLogoutDevice.setVisibility(View.GONE);
                    holder.binding.ivUpperarrow.animate().rotation(0).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemLoginHistoryBinding binding;

        public MyViewHolder(@NonNull ItemLoginHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
