package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMyCartBinding;
import com.dollop.exam101.main.model.CartDatumModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyHolder> {
    Context context;
    List<CartDatumModel> myCartModelArrayList;

    public MyCartAdapter(Context context, List<CartDatumModel> myCartModelArrayList) {
        this.context = context;
        this.myCartModelArrayList =myCartModelArrayList;
    }
    @NonNull
    @Override
    public MyCartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyCartBinding binding =ItemMyCartBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CartDatumModel myCartModel= myCartModelArrayList.get(position);
        holder.binding.tvFundamentalDesignId.setText(myCartModel.packageName);
        holder.binding.tvRupees.setText(myCartModel.discountedPrice);
        holder.binding.tvWishListId.setText(myCartModel.isWishListed);
    }

    @Override
    public int getItemCount() {
        return myCartModelArrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemMyCartBinding binding;
        public MyHolder(@NonNull ItemMyCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
