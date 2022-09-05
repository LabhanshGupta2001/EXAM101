package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemMockTestQuestionBinding;
import com.dollop.exam101.databinding.ItemTestAnsBinding;
import com.dollop.exam101.main.model.Option;


import java.util.ArrayList;
import java.util.List;

public class TestAnsAdapter extends RecyclerView.Adapter<TestAnsAdapter.MyHolder> {
    Context context;
    List<Option> optionList;
    MockTestQuestionAdapter mockTestQuestionAdapter;
    int QuestionPosition;
    ItemMockTestQuestionBinding binding;

    @SuppressLint("NotifyDataSetChanged")
    public TestAnsAdapter(Context context, List<Option> optionList, MockTestQuestionAdapter mockTestQuestionAdapter, int position,
                          ItemMockTestQuestionBinding binding) {
        this.context = context;
        this.optionList = optionList;
        this.mockTestQuestionAdapter = mockTestQuestionAdapter;
        this.QuestionPosition = position;
        this.binding = binding;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTestAnsBinding binding = ItemTestAnsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAnsAdapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        Option option = optionList.get(position);
        holder.binding.radioBtn.setText(String.valueOf(HtmlCompat.fromHtml(option.options, 0)).trim());
        if (option.Selected) {
            holder.binding.radioBtn.setTextColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.mcvAns.setStrokeColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.ivSelected.setVisibility(View.VISIBLE);
            holder.binding.ivUnselected.setVisibility(View.GONE);
        } else {
            holder.binding.radioBtn.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
            holder.binding.mcvAns.setStrokeColor(ContextCompat.getColor(context, R.color.primaryColor));
            holder.binding.ivSelected.setVisibility(View.GONE);
            holder.binding.ivUnselected.setVisibility(View.VISIBLE);
        }

        holder.binding.radioBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                mockTestQuestionAdapter.SelectOption(QuestionPosition,position,binding);
            }
        });

    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemTestAnsBinding binding;

        public MyHolder(@NonNull ItemTestAnsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

