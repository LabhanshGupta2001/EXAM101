package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPackagesDetailTernaryBinding;
import com.dollop.exam101.databinding.ItemPakagesDetailsSecondryBinding;

import java.util.ArrayList;

public class PakageDetailTernaryAdapter extends RecyclerView.Adapter<PakageDetailTernaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list=new ArrayList<>();

    public PakageDetailTernaryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPackagesDetailTernaryBinding binding=ItemPackagesDetailTernaryBinding.inflate(LayoutInflater.from(context),parent,false);
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
        ItemPackagesDetailTernaryBinding binding;
        public MyViewHolder(@NonNull ItemPackagesDetailTernaryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
