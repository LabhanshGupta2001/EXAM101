package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemCategoryDetailBinding;
import com.dollop.exam101.main.activity.CategoryDetailsActivity;
import com.dollop.exam101.main.model.CourseModel;

import java.util.List;

public class CategoryDetailAdapter extends RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolder> {
    Context context;
    int pos ;
    List<CourseModel> ExamList;

    public CategoryDetailAdapter(Context context, List<CourseModel> list, int position) {
        this.context = context;
        this.ExamList = list;
        this.pos = position;
    }

    @NonNull
    @Override
    public CategoryDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryDetailBinding binding = ItemCategoryDetailBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CourseModel courseModel = ExamList.get(position);
        holder.binding.tvBlogHeading.setText(courseModel.examName);
        if (pos == position) {
            holder.binding.tvBlogHeading.setBackgroundResource(R.drawable.theme_backround);
            holder.binding.tvBlogHeading.setTextColor(ContextCompat.getColor(context, R.color.white));
            ((CategoryDetailsActivity)context).binding.tvToolbarName.setText(courseModel.examName);
            ((CategoryDetailsActivity)context).Positions = position;
        } else {
            holder.binding.tvBlogHeading.setBackgroundResource(R.drawable.grey_stroke_background);
            holder.binding.tvBlogHeading.setTextColor(ContextCompat.getColor(context, R.color.sub_text));
        }
        holder.binding.tvBlogHeading.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });


        holder.binding.tvBlogHeading.setOnClickListener(v -> {
            pos = position;
            ((CategoryDetailsActivity) context).getCategoryDetails(courseModel.examId);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return ExamList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryDetailBinding binding;

        public MyViewHolder(@NonNull ItemCategoryDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}


