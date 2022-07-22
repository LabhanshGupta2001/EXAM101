package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMockTestPackageBinding;

import java.util.ArrayList;

public class PakageDetailMockTestFragmentAdapter extends RecyclerView.Adapter<PakageDetailMockTestFragmentAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> list;

    public PakageDetailMockTestFragmentAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public PakageDetailMockTestFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestPackageBinding binding = ItemMockTestPackageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PakageDetailMockTestFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestPackageBinding binding;

        public MyViewHolder(@NonNull ItemMockTestPackageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
