package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemTransactionHistoryBinding;

import java.util.ArrayList;

public class TransactionHistoryAdapter  extends RecyclerView.Adapter<TransactionHistoryAdapter.Myholder> {
    Context context;
    ArrayList<String> list;

    public TransactionHistoryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionHistoryBinding binding = ItemTransactionHistoryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Myholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecycleView") int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        ItemTransactionHistoryBinding binding;

        public Myholder(@NonNull ItemTransactionHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
