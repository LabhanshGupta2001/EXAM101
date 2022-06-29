package com.dollop.exam101.main.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCategoryDetailBinding;
import com.dollop.exam101.databinding.ItemCategoryDetailsSecondaryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailSecondaryAdapter extends RecyclerView.Adapter<CategoryDetailSecondaryAdapter.MyViewHolder> {
    Context context;
    List<String> list;
    List<String> stringList = new ArrayList<>();
    int row_index = -1;

    public CategoryDetailSecondaryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryDetailsSecondaryBinding binding = ItemCategoryDetailsSecondaryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryDetailsSecondaryBinding binding;

        public MyViewHolder(@NonNull ItemCategoryDetailsSecondaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
