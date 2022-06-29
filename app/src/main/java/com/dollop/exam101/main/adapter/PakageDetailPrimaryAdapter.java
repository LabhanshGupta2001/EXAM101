package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PakageDetailPrimaryAdapter extends RecyclerView.Adapter<PakageDetailPrimaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;
    int row_index=-1;
    ArrayList<String> stringArrayList=new ArrayList<>();


    public PakageDetailPrimaryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPakagesDetailsPrimaryBinding binding=ItemPakagesDetailsPrimaryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stringArrayList.clear();
        stringArrayList.add("1");

        holder.binding.rvSecond.setAdapter(new PakageDetailSecondaryAdapter(context,stringArrayList));
        holder.binding.rvSecond.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPakagesDetailsPrimaryBinding binding;
        public MyViewHolder(@NonNull ItemPakagesDetailsPrimaryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
