package com.dollop.exam101.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMockTestListBinding;


import java.util.ArrayList;

public class MockTestListAdapter extends RecyclerView.Adapter<MockTestListAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;
    int row_index=-1;

    public MockTestListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestListBinding binding=ItemMockTestListBinding.inflate(LayoutInflater.from(context),parent,false);
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
        ItemMockTestListBinding binding;
        public MyViewHolder(@NonNull ItemMockTestListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
