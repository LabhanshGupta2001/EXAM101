package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemCourseTestQuestionListBinding;
import com.dollop.exam101.main.model.OptionModel;
import com.dollop.exam101.main.model.QuestionListModel;
import com.dollop.exam101.main.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class CourseTestQuestionAdapter extends RecyclerView.Adapter<CourseTestQuestionAdapter.MyViewHolder> {

    Context context;
    List<QuestionModel> questionModelArrayList;
    List<OptionModel> optionModelArrayList = new ArrayList<>();

    public CourseTestQuestionAdapter(Context context, ArrayList<QuestionModel> list) {
        this.context = context;
        this.questionModelArrayList = list;
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
        Utils.E("questionModelArrayList::"+questionModelArrayList);
        QuestionModel questionListModel = questionModelArrayList.get(position);
        holder.binding.tvQuestion.setText(HtmlCompat.fromHtml(questionListModel.question,0));

        optionModelArrayList.addAll(questionListModel.options);
        holder.binding.rvOption.setAdapter(new CourseTestQuestionOptionAdapter(context, optionModelArrayList));
        holder.binding.rvOption.setLayoutManager(new LinearLayoutManager(context));

     //   holder.binding.rvOption.notifyAll();
    }

    @Override
    public int getItemCount() {
        return questionModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseTestQuestionListBinding binding;

        public MyViewHolder(@NonNull ItemCourseTestQuestionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}