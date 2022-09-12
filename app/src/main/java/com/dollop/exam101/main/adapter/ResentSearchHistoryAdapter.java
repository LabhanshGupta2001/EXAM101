package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.SearchHistoryTable;
import com.dollop.exam101.databinding.ItemSearchHistoryBinding;
import com.dollop.exam101.main.activity.SearchHistoryActivity;

import java.util.ArrayList;

public class ResentSearchHistoryAdapter extends RecyclerView.Adapter<ResentSearchHistoryAdapter.MyHolder> {
    Context context;
    ArrayList<SearchHistoryTable> listItem;


    public ResentSearchHistoryAdapter(Context context, ArrayList list) {
        listItem = new ArrayList<>();
        listItem.clear();
        this.context = context;
        this.listItem = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchHistoryBinding binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        SearchHistoryTable historyTable = listItem.get(position);
        holder.binding.tvResentSearch.setText(historyTable.searchItem);
        holder.binding.llSearchHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchHistoryActivity) context).setSearchText(holder.binding.tvResentSearch.getText().toString().trim());
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = listItem.size();
        return (Math.min(size, 4));
        //return listItem.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemSearchHistoryBinding binding;

        public MyHolder(@NonNull ItemSearchHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
