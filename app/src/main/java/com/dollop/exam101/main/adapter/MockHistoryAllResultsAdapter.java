package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMockTestHistoryAllresultsBinding;
import com.dollop.exam101.databinding.ItemMockTestListBinding;

import java.util.ArrayList;

public class MockHistoryAllResultsAdapter extends RecyclerView.Adapter<MockHistoryAllResultsAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;
    int row_index=-1;

    public MockHistoryAllResultsAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestHistoryAllresultsBinding binding=ItemMockTestHistoryAllresultsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestHistoryAllresultsBinding binding;
        public MyViewHolder(@NonNull ItemMockTestHistoryAllresultsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}