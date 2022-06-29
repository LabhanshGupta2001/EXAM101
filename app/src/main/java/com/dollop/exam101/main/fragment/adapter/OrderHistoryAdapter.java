package com.dollop.exam101.main.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.main.model.OrderHistoryModel;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyHolder> {
    Context context;
    ArrayList<OrderHistoryModel> orderHistoryModelArrayList;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistoryModel> orderHistoryModelArrayList) {
        this.context = context;
        this.orderHistoryModelArrayList = orderHistoryModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding binding =ItemOrderHistoryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        OrderHistoryModel historyModel= orderHistoryModelArrayList.get(position);
        holder.binding.tvDate.setText(historyModel.Data);
        holder.binding.tvTransactionId.setText(historyModel.TransactionId);
        holder.binding.orderId.setText(historyModel.OrderId);
        holder.binding.tvRupees.setText(historyModel.Rupees);
        holder.binding.tvTotalRupees.setText(historyModel.TotalRupees);
        holder.binding.tvDay.setText(historyModel.Day);
    }

    @Override
    public int getItemCount() {
        return orderHistoryModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemOrderHistoryBinding binding;

        public MyHolder(@NonNull ItemOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
