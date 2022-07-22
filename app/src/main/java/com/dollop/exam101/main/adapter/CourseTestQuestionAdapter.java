package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.exam101.databinding.ItemCourseTestQuestionListBinding;

import java.util.ArrayList;

public class CourseTestQuestionAdapter extends RecyclerView.Adapter<CourseTestQuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> list;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public CourseTestQuestionAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseTestQuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseTestQuestionListBinding binding = ItemCourseTestQuestionListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTestQuestionAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.binding.tvQuestion.setText(list.get(position));
        stringArrayList.clear();
        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        stringArrayList.add("4");
        holder.binding.rvOption.setAdapter(new CourseTestQuestionOptionAdapter(context, stringArrayList));
        holder.binding.rvOption.setLayoutManager(new LinearLayoutManager(context));

     //   holder.binding.rvOption.notifyAll();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseTestQuestionListBinding binding;

        public MyViewHolder(@NonNull ItemCourseTestQuestionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}