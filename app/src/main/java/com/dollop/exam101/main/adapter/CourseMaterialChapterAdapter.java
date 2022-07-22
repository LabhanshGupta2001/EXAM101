package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemCourseMaterialChapterNameListBinding;
import com.dollop.exam101.main.activity.CoursesDetailActivity;

import java.util.ArrayList;

public class CourseMaterialChapterAdapter extends RecyclerView.Adapter<CourseMaterialChapterAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list = new ArrayList<>();

    public CourseMaterialChapterAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseMaterialChapterNameListBinding binding = ItemCourseMaterialChapterNameListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.binding.mcvChapter.setOnClickListener( view ->
        {
            Utils.I(context, CoursesDetailActivity.class,null);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseMaterialChapterNameListBinding binding;

        public MyViewHolder(@NonNull ItemCourseMaterialChapterNameListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
