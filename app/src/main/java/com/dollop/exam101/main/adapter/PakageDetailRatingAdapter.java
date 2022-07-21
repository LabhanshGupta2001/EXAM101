package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPackageDetailBinding;
import com.dollop.exam101.main.model.ReviewRating;

import java.util.ArrayList;

public class PakageDetailRatingAdapter extends RecyclerView.Adapter<PakageDetailRatingAdapter.MyViewHolder> {
    Context context;
    ArrayList<ReviewRating> list;
    int row_index=-1;



    public PakageDetailRatingAdapter(Context context, ArrayList<ReviewRating> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPackageDetailBinding binding= ItemPackageDetailBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {/*
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPackageDetailBinding binding;
        public MyViewHolder(@NonNull ItemPackageDetailBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
