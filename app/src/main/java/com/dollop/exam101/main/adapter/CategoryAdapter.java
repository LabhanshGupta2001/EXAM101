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

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {
    Context context;
    ArrayList<String> list;
    int index=-1;

    public CategoryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyHolder holder, @SuppressLint("RecycleView") int position) {

        holder.binding.mcvCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position){
            holder.binding.mcvCategoryItem.setCardBackgroundColor(ContextCompat.getColor(context,R.color.TvBgColor));
            holder.binding.tvItem.setTextColor(ContextCompat.getColor(context,R.color.theme));
            holder.binding.mcvCategoryItem.setStrokeColor(ContextCompat.getColor(context,R.color.theme));

        } else{
            holder.binding.mcvCategoryItem.setCardBackgroundColor(ContextCompat.getColor(context,R.color.background));
            holder.binding.tvItem.setTextColor(ContextCompat.getColor(context,R.color.primaryColor));
            holder.binding.mcvCategoryItem.setStrokeColor(ContextCompat.getColor(context,R.color.StrokeColorLightBlue));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public MyHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
