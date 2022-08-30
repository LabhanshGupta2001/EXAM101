package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCourseMaterialSubjectNameBinding;
import com.dollop.exam101.main.model.Module;
import com.dollop.exam101.main.model.Subject;

import java.util.ArrayList;

public class CourseMaterialSubjectAdapter extends RecyclerView.Adapter<CourseMaterialSubjectAdapter.MyViewHolder> {
    Context context;
    ArrayList<Subject> subjectArrayList;

    ArrayList<Module> moduleArrayList = new ArrayList<>();

    public CourseMaterialSubjectAdapter(Context context, ArrayList<Subject> subjectArrayList) {
        this.context = context;
        this.subjectArrayList = subjectArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseMaterialSubjectNameBinding binding = ItemCourseMaterialSubjectNameBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Subject subject = subjectArrayList.get(position);
        holder.binding.tvSubject1.setText(subject.subjectName);
        moduleArrayList.addAll(subject.modules);
        if (moduleArrayList.isEmpty()) {
            holder.binding.rvSubjectModule.setVisibility(View.GONE);
        } else {
            holder.binding.rvSubjectModule.setLayoutManager(new LinearLayoutManager(context));
            holder.binding.rvSubjectModule.setAdapter(new CourseMaterialSubjectModulAdapter(context, moduleArrayList));
        }
    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseMaterialSubjectNameBinding binding;

        public MyViewHolder(@NonNull ItemCourseMaterialSubjectNameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
