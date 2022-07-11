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

import java.util.ArrayList;

public class CourseMaterialSubjectModulAdapter extends RecyclerView.Adapter<CourseMaterialSubjectModulAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> list;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private Boolean dropdown = true;

    public CourseMaterialSubjectModulAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseMaterialSubjectModulAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseMaterialSubjectModuleListBinding binding = ItemCourseMaterialSubjectModuleListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CourseMaterialSubjectModulAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseMaterialSubjectModulAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stringArrayList.clear();
        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        stringArrayList.add("4");
        holder.binding.rvChapter.setAdapter(new CourseMaterialChapterAdapter(context, stringArrayList));
        holder.binding.rvChapter.setLayoutManager(new LinearLayoutManager(context));


      /*  if (!dropdown)
        {
            holder.binding.rvChapter.setVisibility(View.GONE);
            holder.binding.ivRotedArrow.animate().rotation(0).setDuration(100).start();
        }*/
        holder.binding.mcvZtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dropdown) {
                    holder.binding.rvChapter.setVisibility(View.GONE);
                    holder.binding.ivRotedArrow.animate().rotation(180).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.rvChapter.setVisibility(View.VISIBLE);
                    holder.binding.ivRotedArrow.animate().rotation(0).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseMaterialSubjectModuleListBinding binding;

        public MyViewHolder(@NonNull ItemCourseMaterialSubjectModuleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
