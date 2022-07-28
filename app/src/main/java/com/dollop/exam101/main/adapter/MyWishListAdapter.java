package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemWishlistBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.List;

public class MyWishListAdapter extends RecyclerView.Adapter<MyWishListAdapter.MyHolder> {
    Context context;
    List<String> list;


    public MyWishListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWishlistBinding binding = ItemWishlistBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemWishlistBinding binding;
        public MyHolder(@NonNull ItemWishlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
