package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCourseMaterialSubjectNameBinding;
import com.dollop.exam101.databinding.ItemPakagesDetailsPrimaryBinding;

import java.util.ArrayList;

public class CourseMaterialSubjectAdapter extends RecyclerView.Adapter<CourseMaterialSubjectAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;
    int row_index = -1;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public CourseMaterialSubjectAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseMaterialSubjectNameBinding binding = ItemCourseMaterialSubjectNameBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stringArrayList.clear();
        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        stringArrayList.add("4");

        holder.binding.rvSubjectModule.setAdapter(new CourseMaterialSubjectModulAdapter(context, stringArrayList));
        holder.binding.rvSubjectModule.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseMaterialSubjectNameBinding binding;

        public MyViewHolder(@NonNull ItemCourseMaterialSubjectNameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
