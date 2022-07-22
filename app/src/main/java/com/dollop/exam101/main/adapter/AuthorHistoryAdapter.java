package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemAuthoreSearchBinding;

import java.util.ArrayList;

public class AuthorHistoryAdapter extends RecyclerView.Adapter<AuthorHistoryAdapter.MyHolder> {
    Context context;
    ArrayList<String> list;
    int index = -1;

    public AuthorHistoryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAuthoreSearchBinding binding = ItemAuthoreSearchBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint({"RecycleView", "RecyclerView"}) int position) {
        holder.binding.mcvSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position) {
            holder.binding.mcvSearch.setCardBackgroundColor(ContextCompat.getColor(context, R.color.TvBgColor));
            holder.binding.tvSearchHistory.setTextColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.mcvSearch.setStrokeColor(ContextCompat.getColor(context, R.color.theme));

        } else {
            holder.binding.mcvSearch.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background));
            holder.binding.tvSearchHistory.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
            holder.binding.mcvSearch.setStrokeColor(ContextCompat.getColor(context, R.color.StrokeColorLightBlue));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemAuthoreSearchBinding binding;

        public MyHolder(@NonNull ItemAuthoreSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
