package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPakagesDetailsPrimaryBinding;
import com.dollop.exam101.databinding.ItemPakagesDetailsSecondryBinding;

import java.util.ArrayList;

public class PakageDetailSecondaryAdapter extends RecyclerView.Adapter<PakageDetailSecondaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;
    ArrayList<String> stringArrayList=new ArrayList<>();

    public PakageDetailSecondaryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPakagesDetailsSecondryBinding binding=ItemPakagesDetailsSecondryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stringArrayList.clear();
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");


        holder.binding.rvThird.setAdapter(new PakageDetailTernaryAdapter(context,stringArrayList));
        holder.binding.rvThird.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPakagesDetailsSecondryBinding binding;
        public MyViewHolder(@NonNull ItemPakagesDetailsSecondryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
