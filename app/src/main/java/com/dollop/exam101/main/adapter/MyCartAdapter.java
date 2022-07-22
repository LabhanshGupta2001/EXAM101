package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMyCartBinding;
import com.dollop.exam101.main.model.MyCartModel;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyHolder> {
    Context context;
    ArrayList<MyCartModel> myCartModelArrayList;

    public MyCartAdapter(Context context, ArrayList<MyCartModel> myCartModelArrayList) {
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
        MyCartModel myCartModel= myCartModelArrayList.get(position);
       /* holder.binding.ivPhotoId.setImageResource(myCartModel.Photo);*/
        holder.binding.tvPowerPointId.setText(myCartModel.Point);
        holder.binding.tvFundamentalDesignId.setText(myCartModel.FundamentalsDesign);
        holder.binding.tvRupees.setText(myCartModel.Rupees);
        holder.binding.tvWishListId.setText(myCartModel.Wishlist);
        holder.binding.tvRemoveId.setText(myCartModel.Remove);
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
