package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemResultQuestionBinding;
import com.dollop.exam101.main.model.TestResultQuestion;

import java.util.List;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.MyHolder> {
    Context context;
    List<TestResultQuestion> list;
    int pos = -1;
    private Boolean dropdown = false;


    public TestResultAdapter(Context context, List<TestResultQuestion> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResultQuestionBinding binding = ItemResultQuestionBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.tvQuestion.setText(HtmlCompat.fromHtml(list.get(position).question, 0));
        holder.binding.tvCorrectAns.setText(String.valueOf(HtmlCompat.fromHtml(list.get(position).correctAns, 0)).trim() + "");

        if (list.get(position).questionResult.equalsIgnoreCase("Wrong")) {
            holder.binding.tvWrongAnwar.setVisibility(View.VISIBLE);
            holder.binding.tvCurrentAns.setVisibility(View.GONE);
            if (list.get(position).studentAnswer != null && !list.get(position).studentAnswer.isEmpty())
                holder.binding.tvWrongAns.setText(String.valueOf(HtmlCompat.fromHtml(list.get(position).studentAnswer, 0)).trim() + "");
            else
                holder.binding.tvWrongAns.setText("Not Selected");
            holder.binding.mcvWrong.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvWrongAnwar.setVisibility(View.GONE);
            holder.binding.tvCurrentAns.setVisibility(View.VISIBLE);
        }

        holder.binding.llIn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });
        holder.binding.llMain.setOnClickListener(v2 -> {
            if (!dropdown) {
                holder.binding.llBelow.setVisibility(View.VISIBLE);
                holder.binding.ivDropdown.animate().rotation(-90).setDuration(100).start();
                dropdown = true;
            } else {
                holder.binding.llBelow.setVisibility(View.GONE);
                holder.binding.ivDropdown.animate().rotation(90).setDuration(100).start();
                dropdown = false;
            }
        });
        holder.binding.llIn.setOnClickListener(v1 -> {
            if (!dropdown) {
                holder.binding.llBelow.setVisibility(View.VISIBLE);
                holder.binding.ivDropdown.animate().rotation(-90).setDuration(100).start();
                dropdown = true;
            } else {
                holder.binding.llBelow.setVisibility(View.GONE);
                holder.binding.ivDropdown.animate().rotation(90).setDuration(100).start();
                dropdown = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemResultQuestionBinding binding;

        public MyHolder(@NonNull ItemResultQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
