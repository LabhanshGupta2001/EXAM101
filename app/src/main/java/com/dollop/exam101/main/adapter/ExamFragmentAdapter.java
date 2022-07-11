package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemExamFragmentBinding;

import java.util.List;

public class ExamFragmentAdapter extends RecyclerView.Adapter<ExamFragmentAdapter.MyViewHolder> {

    Context context;
    List<String> list;
    int index = -1;


    public ExamFragmentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ExamFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExamFragmentBinding binding = ItemExamFragmentBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.mcvExamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position){
            holder.binding.tvAllExam.setTextColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.mcvExamName.setCardBackgroundColor(ContextCompat.getColor(context,R.color.TvBgColor));
            holder.binding.mcvExamName.setStrokeColor(ContextCompat.getColor(context,R.color.theme));

        } else {
            holder.binding.tvAllExam.setTextColor(ContextCompat.getColor(context, R.color.full_black));
            holder.binding.mcvExamName.setCardBackgroundColor(ContextCompat.getColor(context,R.color.background));
            holder.binding.mcvExamName.setStrokeColor(ContextCompat.getColor(context,R.color.StrokeColorLightBlue));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemExamFragmentBinding binding;

        public MyViewHolder(@NonNull ItemExamFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
