package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemWishlistBinding;
import com.dollop.exam101.main.activity.MyWishlistActivity;
import com.dollop.exam101.main.model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class MyWishListAdapter extends RecyclerView.Adapter<MyWishListAdapter.MyHolder> {
    Context context;
    List<WishListModel> Wishlist;


    public MyWishListAdapter(Context context, ArrayList<WishListModel> list) {
        this.context = context;
        this.Wishlist = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWishlistBinding binding = ItemWishlistBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        WishListModel wishListModel = Wishlist.get(position);
        holder.binding.tvActhualPrise.setText(wishListModel.actualPrice);
        holder.binding.tvRupees.setText("₹" + wishListModel.discountedPrice);
        holder.binding.tvPowerPointId.setText(wishListModel.packageName);
        holder.binding.tvFundamentalDesignId.setText(wishListModel.shortDesc);
        holder.binding.tvRating.setText(wishListModel.rating);
        holder.binding.llRemove.setOnClickListener(view -> {
            ((MyWishlistActivity) context).removeFromWishList(wishListModel.wishListUuid);
        });
        holder.binding.tvAddCardId.setOnClickListener(view -> {
          //  ((MyWishlistActivity) context).addCart(wishListModel.packageUuid,wishListModel.packageUuid);
        });
    }

    @Override
    public int getItemCount() {
        return Wishlist.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemWishlistBinding binding;

        public MyHolder(@NonNull ItemWishlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
