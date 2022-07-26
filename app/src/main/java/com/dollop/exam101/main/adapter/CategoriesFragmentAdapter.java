package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCategoriesFragmentBinding;
import com.dollop.exam101.main.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragmentAdapter extends RecyclerView.Adapter<CategoriesFragmentAdapter.MyViewHolder> {
    public int index = -1;
    public String examId = "";
    String From;
    Context context;
    List<CourseModel> examChecklist;


    public CategoriesFragmentAdapter(ArrayList<CourseModel> list, FragmentActivity context, String from) {
        this.context = context;
        this.examChecklist = list;
        From = from;
    }

    @NonNull
    @Override
    public CategoriesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoriesFragmentBinding binding = ItemCategoriesFragmentBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CategoriesFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CourseModel courseModel = examChecklist.get(position);
        holder.binding.tvItem.setText(courseModel.examName);
        holder.binding.radioButton.setChecked(index == position);
        holder.binding.materialCardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                index = position;
                examId = examChecklist.get(position).examId;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return examChecklist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoriesFragmentBinding binding;

        public MyViewHolder(@NonNull ItemCategoriesFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
