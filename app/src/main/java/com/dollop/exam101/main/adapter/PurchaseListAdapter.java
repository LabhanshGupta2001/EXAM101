package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPurchaseListBinding;

import java.util.ArrayList;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.Myholder> {
    Context context;
    ArrayList<String> list;
    boolean dropdown = false;

    public PurchaseListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPurchaseListBinding binding = ItemPurchaseListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Myholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecycleView") int position) {

        holder.binding.llUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dropdown) {
                    holder.binding.llPrice.setVisibility(View.VISIBLE);
                    holder.binding.ivDropdown.animate().rotation(-90).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.llPrice.setVisibility(View.GONE);
                    holder.binding.ivDropdown.animate().rotation(90).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {
        ItemPurchaseListBinding binding;
        public Myholder(@NonNull  ItemPurchaseListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
