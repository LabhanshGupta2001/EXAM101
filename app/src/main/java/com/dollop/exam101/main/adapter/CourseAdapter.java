package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.main.model.CourseModel;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyPostViewHolder>
{
    private ArrayList<CourseModel> courseModelList;
    private Context context;

    public CourseAdapter(Context context, ArrayList<CourseModel> userPostModelList)
    {
        this.courseModelList=userPostModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MyPostViewHolder(ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position)
    {
        CourseModel courseModel=courseModelList.get(position);
        holder.itemCourseBinding.tvCourseName.setText(String.valueOf(courseModel.name));
        holder.itemCourseBinding.ivCourseImageView.setImageResource(courseModel.image);
    }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class MyPostViewHolder extends RecyclerView.ViewHolder {
        private ItemCourseBinding itemCourseBinding;
        public MyPostViewHolder(ItemCourseBinding itemCourseBinding)
        {
            super(itemCourseBinding.getRoot());
            this.itemCourseBinding=itemCourseBinding;
        }
    }
}
