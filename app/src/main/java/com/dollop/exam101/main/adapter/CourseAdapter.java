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
import com.dollop.exam101.databinding.ItemCourseBinding;
import com.dollop.exam101.main.activity.CategoryDetailsActivity;
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
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        CourseModel courseModel=courseModelList.get(position);
        holder.itemCourseBinding.tvCourseName.setText(courseModel.examName);

        holder.itemCourseBinding.llExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.Key.Position,position);
                bundle.putString(Constants.Key.examId, courseModel.examId);
                bundle.putString(Constants.Key.examName, courseModel.examName);
                Utils.I(context, CategoryDetailsActivity.class, bundle);
            }
        });
        }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class MyPostViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseBinding itemCourseBinding;
        public MyPostViewHolder(ItemCourseBinding itemCourseBinding)
        {
            super(itemCourseBinding.getRoot());
            this.itemCourseBinding=itemCourseBinding;
        }
    }
}
