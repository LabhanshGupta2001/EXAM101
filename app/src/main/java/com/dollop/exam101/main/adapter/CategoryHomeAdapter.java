package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemMyExamBinding;
import com.dollop.exam101.main.activity.CoursesMaterial;
import com.dollop.exam101.main.model.Exam;

import java.text.ParseException;
import java.util.ArrayList;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.MyViewHolder> {
    private final ArrayList<Exam> courseModelList;
    Context context;
    int row_index = -1;

    public CategoryHomeAdapter(Context context, ArrayList<Exam> list) {
        this.context = context;
        this.courseModelList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyExamBinding binding = ItemMyExamBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Exam courseModel = courseModelList.get(position);
        holder.binding.tvPackageHeading.setText(courseModel.examName);
        try {
            holder.binding.tvPurchasedOn.setText("Purchased on: " + TimeFormatter.getDateTime(courseModel.purchaseDtm, context, "yyyy-MM-dd HH:mm:ss", "Date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            holder.binding.tvExpireOn.setText("Expire on: " + TimeFormatter.getDateTime(courseModel.packageExpiryDtm, context, "yyyy-MM-dd HH:mm:ss", "Date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.binding.llViewCourse.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.orderExamUuid, courseModel.orderExamUuid);
            Utils.I(context, CoursesMaterial.class, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMyExamBinding binding;

        public MyViewHolder(@NonNull ItemMyExamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
