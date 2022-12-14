package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemCourseListBinding;
import com.dollop.exam101.main.activity.CoursesMaterial;

import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.model.CourseListModel;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyCourseListViewHolder> {
    private ArrayList<CourseListModel> courseModelList;
    private Context context;

    public CourseListAdapter(Context context, ArrayList<CourseListModel> courseModelList) {
        this.courseModelList = courseModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseListAdapter.MyCourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseListAdapter.MyCourseListViewHolder(ItemCourseListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyCourseListViewHolder holder, int position) {
        CourseListModel courseModel = courseModelList.get(position);
        //holder.itemCourseListBinding.tvCourseName.setText(String.valueOf(courseModel.name));
        //holder.itemCourseListBinding.ivCourseImageView.setImageResource(courseModel.image);

        if (courseModel.LeftDate.equalsIgnoreCase("0"))
        {
         //  holder.itemCourseListBinding.cardCourse.setForeground(new ColorDrawable(ContextCompat.getColor(context,Integer.valueOf(R.color.dark_gray))));
            holder.itemCourseListBinding.tvTopicName.setText(String.valueOf(courseModel.TopicName));
            holder.itemCourseListBinding.tvCourseName.setText(String.valueOf(courseModel.CourseName));
            holder.itemCourseListBinding.tvExpireDate.setText(String.valueOf(courseModel.ExpireDate));
            holder.itemCourseListBinding.tvPackageDurationDay.setText(String.valueOf(courseModel.PackageDurationDay));
            holder.itemCourseListBinding.llDaysLeft.setVisibility(View.GONE);
            holder.itemCourseListBinding.tvExpired.setVisibility(View.VISIBLE);
            holder.itemCourseListBinding.tvViewCourse.setVisibility(View.GONE);
            holder.itemCourseListBinding.tvRepurchase.setVisibility(View.VISIBLE);

            holder.itemCourseListBinding.llViewCourse.setOnClickListener(view ->
            {
                // Toast.makeText(context, "Click On View Course ", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.packageUuid,"3cfd38ef-0e23-11ed-9754-000c291151eb");
                Utils.I(context,PackagesDetailActivity.class,bundle);
            });
        }
        else
        {
            holder.itemCourseListBinding.tvTopicName.setText(String.valueOf(courseModel.TopicName));
            holder.itemCourseListBinding.tvCourseName.setText(String.valueOf(courseModel.CourseName));
            holder.itemCourseListBinding.tvExpireDate.setText(String.valueOf(courseModel.ExpireDate));
            holder.itemCourseListBinding.tvDaysLeft.setText(String.valueOf(courseModel.LeftDate));
            holder.itemCourseListBinding.tvPackageDurationDay.setText(String.valueOf(courseModel.PackageDurationDay));
            holder.itemCourseListBinding.llDaysLeft.setVisibility(View.VISIBLE);
            holder.itemCourseListBinding.tvExpired.setVisibility(View.GONE);
            holder.itemCourseListBinding.llViewCourse.setOnClickListener(view ->
            {
                // Toast.makeText(context, "Click On View Course ", Toast.LENGTH_SHORT).show();
                Utils.I(context,CoursesMaterial.class,null);
            });
        }
    }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class MyCourseListViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseListBinding itemCourseListBinding;

        public MyCourseListViewHolder(ItemCourseListBinding itemCourseListBinding) {
            super(itemCourseListBinding.getRoot());
            this.itemCourseListBinding = itemCourseListBinding;
        }
    }
}