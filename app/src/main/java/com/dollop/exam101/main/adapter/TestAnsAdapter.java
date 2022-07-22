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
import com.dollop.exam101.databinding.ItemTestAnsBinding;


import java.util.ArrayList;

public class TestAnsAdapter extends RecyclerView.Adapter<TestAnsAdapter.MyHolder> {
    Context context;
    ArrayList<String> list;
    int index = -1;

    public TestAnsAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTestAnsBinding binding=ItemTestAnsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAnsAdapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.radioBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position) {
            holder.binding.radioBtn.setTextColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.mcvAns.setStrokeColor(ContextCompat.getColor(context, R.color.theme));

        } else {
            holder.binding.radioBtn.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemTestAnsBinding binding;

        public MyHolder(@NonNull ItemTestAnsBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
    }

}
