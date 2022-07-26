package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemCategoryHomeBinding;
import com.dollop.exam101.main.activity.CategoryDetailsActivity;
import com.dollop.exam101.main.model.CourseModel;

import java.util.ArrayList;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.MyViewHolder> {
    private final ArrayList<CourseModel> courseModelList;
    Context context;
    int row_index = -1;

    public CategoryHomeAdapter(Context context, ArrayList<CourseModel> list) {
        this.context = context;
        this.courseModelList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryHomeBinding binding = ItemCategoryHomeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CourseModel courseModel = courseModelList.get(position);
        holder.binding.tvExamName.setText(courseModel.examName);

        holder.binding.llPhotography.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.Key.Position,position);
            bundle.putString(Constants.Key.examId, courseModel.examId);
            Utils.I(context, CategoryDetailsActivity.class, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryHomeBinding binding;

        public MyViewHolder(@NonNull ItemCategoryHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
