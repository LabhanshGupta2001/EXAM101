package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemResultQuestionBinding;

import java.util.List;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.MyHolder> {
    Context context;
    List<String> list;
    int pos = -1;
    private Boolean dropdown = false;


    public TestResultAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResultQuestionBinding binding = ItemResultQuestionBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,  @SuppressLint("RecyclerView") int position) {
        holder.binding.llIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });
        holder.binding.llIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dropdown) {
                    holder.binding.llBelow.setVisibility(View.VISIBLE);
                    holder.binding.ivDropdown.animate().rotation(90).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.llBelow.setVisibility(View.GONE);
                    holder.binding.ivDropdown.animate().rotation(-90).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemResultQuestionBinding binding;
        public MyHolder(@NonNull ItemResultQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
