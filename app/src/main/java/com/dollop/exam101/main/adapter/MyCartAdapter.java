package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemMyCartBinding;
import com.dollop.exam101.main.fragment.CartFragment;
import com.dollop.exam101.main.model.CartDatumModel;

import java.text.DecimalFormat;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyHolder> {
    Context context;
    List<CartDatumModel> myCartModelArrayList;
    CartFragment cartFragment;

    public MyCartAdapter(Context context, List<CartDatumModel> myCartModelArrayList, CartFragment cartFragment) {
        this.context = context;
        this.myCartModelArrayList = myCartModelArrayList;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public MyCartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyCartBinding binding = ItemMyCartBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CartDatumModel myCartModel = myCartModelArrayList.get(position);
        holder.binding.tvFundamentalDesignId.setText(myCartModel.packageName);
        holder.binding.tvRupees.setText(new DecimalFormat("##.##").format(Double.parseDouble(myCartModel.discountedPrice)));
        if (myCartModel.isWishListed.equals("1")) {
            holder.binding.tvWishListId.setText(R.string.remove_from_wishlist);
            holder.binding.ivHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_red));
            holder.binding.llAddToWishList.setOnClickListener(view -> {
                cartFragment.removeFromWishList(myCartModel.wishListUuid);
            });

        } else {
            holder.binding.ivHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plain_heart));
            holder.binding.tvWishListId.setText(R.string.save_for_later);
            holder.binding.llAddToWishList.setOnClickListener(view -> {
                cartFragment.addToWishList(myCartModel.packageUuid);
            });
        }

        holder.binding.llRemoveCart.setOnClickListener(view -> {
            cartFragment.removeFromCart(myCartModel.cartUuid);
        });



    }

    @Override
    public int getItemCount() {
        return myCartModelArrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemMyCartBinding binding;

        public MyHolder(@NonNull ItemMyCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
