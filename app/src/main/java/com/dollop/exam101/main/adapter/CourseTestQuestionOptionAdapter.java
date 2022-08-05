package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;

import com.dollop.exam101.databinding.ItemCourseTestQuestionOptionListBinding;
import com.dollop.exam101.main.model.QuestionModel;

import java.util.ArrayList;

public class CourseTestQuestionOptionAdapter extends RecyclerView.Adapter<CourseTestQuestionOptionAdapter.MyViewHolder> {
    Context context;
    ArrayList<QuestionModel> questionModelArrayList = new ArrayList<>();
    int row_index = -1;

    public CourseTestQuestionOptionAdapter(Context context, ArrayList<QuestionModel> list) {
        this.context = context;
        this.questionModelArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseTestQuestionOptionListBinding binding = ItemCourseTestQuestionOptionListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Utils.E("questionModelArrayListOPPPPPPP:::++"+questionModelArrayList);
        QuestionModel questionModel = questionModelArrayList.get(position);

        holder.binding.tvOption.setText(questionModel.options);
/*
        holder.binding.cardOption.setStrokeColor(R.color.green);
        holder.binding.tvOption.setBackgroundColor(R.color.white);
*/
        holder.binding.tvOption.setOnClickListener(view ->
        {
            row_index = position;
            notifyDataSetChanged();
        });
        if (row_index == position) {
            holder.binding.cardOption.setStrokeColor(ContextCompat.getColor(context,R.color.green));
            holder.binding.cardOption.setBackgroundColor(ContextCompat.getColor(context,R.color.status_bar));

          /*  holder.binding.tvOption.setDrawingCacheBackgroundColor(R.drawable.select_option_background);
            Log.d("Position Not Click"," "+position);
            Toast.makeText(context, "Postion Not Click "+position, Toast.LENGTH_SHORT).show();*/
        } else {
            holder.binding.cardOption.setStrokeColor(ContextCompat.getColor(context,R.color.color_gray3));
            holder.binding.cardOption.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
           /* Log.d("Position On Click", " " + position);
            Toast.makeText(context, "Postion On Click " + position, Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public int getItemCount() {
        return questionModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseTestQuestionOptionListBinding binding;

        public MyViewHolder(@NonNull ItemCourseTestQuestionOptionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}