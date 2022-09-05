package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemMockTestHistoryAllresultsBinding;
import com.dollop.exam101.main.model.MockTestHistory;

import java.util.ArrayList;
import java.util.List;

public class MockHistoryAllResultsAdapter extends RecyclerView.Adapter<MockHistoryAllResultsAdapter.MyViewHolder> {
    Context context;
    List<MockTestHistory> list;

    @SuppressLint("NotifyDataSetChanged")
    public MockHistoryAllResultsAdapter(Context context, List<MockTestHistory> list) {
        list.clear();
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestHistoryAllresultsBinding binding = ItemMockTestHistoryAllresultsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.binding.tvCourseName.setText(list.get(position).mockTestName);
        holder.binding.tvResult.setText(list.get(position).correctAnsCnt+"/"+list.get(position).questionCnt);
        holder.binding.tvAttempt.setText(list.get(position).testAttemptId+" Attempt");
        holder.binding.tvTestDate.setText("Test Date : "+TimeFormatter.changeDateFormat(list.get(position).createdDtm));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestHistoryAllresultsBinding binding;

        public MyViewHolder(@NonNull ItemMockTestHistoryAllresultsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
