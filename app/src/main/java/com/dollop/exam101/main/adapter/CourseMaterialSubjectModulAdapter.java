package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCourseMaterialSubjectModuleListBinding;
import com.dollop.exam101.main.model.Module;
import com.dollop.exam101.main.model.Topic;

import java.util.ArrayList;

public class CourseMaterialSubjectModulAdapter extends RecyclerView.Adapter<CourseMaterialSubjectModulAdapter.MyViewHolder> {

    Context context;
    ArrayList<Module> moduleArrayList;
    ArrayList<Topic> topicArrayList = new ArrayList<>();

    public CourseMaterialSubjectModulAdapter(Context context, ArrayList<Module> moduleArrayList) {
        this.context = context;
        this.moduleArrayList = moduleArrayList;
    }

    @NonNull
    @Override
    public CourseMaterialSubjectModulAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseMaterialSubjectModuleListBinding binding = ItemCourseMaterialSubjectModuleListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseMaterialSubjectModulAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Module module = moduleArrayList.get(position);
        holder.binding.tvModuleName.setText(module.moduleName);
        topicArrayList.clear();
        topicArrayList.addAll(module.topics);
        if (topicArrayList.isEmpty()) {
            holder.binding.rvChapter.setVisibility(View.GONE);
        } else {
            holder.binding.rvChapter.setAdapter(new CourseMaterialChapterAdapter(context, topicArrayList));
            holder.binding.rvChapter.setLayoutManager(new LinearLayoutManager(context));
        }


        holder.binding.mcvZtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.rvChapter.getVisibility() == View.VISIBLE) {
                    holder.binding.rvChapter.setVisibility(View.GONE);
                    holder.binding.ivRotedArrow.animate().rotation(180).setDuration(100).start();
                } else {
                    holder.binding.rvChapter.setVisibility(View.VISIBLE);
                    holder.binding.ivRotedArrow.animate().rotation(0).setDuration(100).start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseMaterialSubjectModuleListBinding binding;

        public MyViewHolder(@NonNull ItemCourseMaterialSubjectModuleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
