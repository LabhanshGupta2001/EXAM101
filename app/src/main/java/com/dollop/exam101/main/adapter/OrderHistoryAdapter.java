package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.databinding.ItemOrderHistoryBinding;
import com.dollop.exam101.main.activity.OrderHistoryActivity;
import com.dollop.exam101.main.model.OrderHistoryModel;
import com.dollop.exam101.main.model.StudentOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyHolder> {
    Context context;
    List<StudentOrder> orderHistoryModelArrayList;

    public OrderHistoryAdapter(Context context, List<StudentOrder> orderHistoryModelArrayList) {
        this.context = context;
        this.orderHistoryModelArrayList = orderHistoryModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        StudentOrder historyModel= orderHistoryModelArrayList.get(position);

        holder.binding.tvDate.setText(TimeFormatter.changeDateFormat(historyModel.createdDtm));
        holder.binding.tvTransactionId.setText(historyModel.transactionId);
        holder.binding.orderId.setText(historyModel.orderId);
        holder.binding.tvRupees.setText(historyModel.finalPackageAmt);
        holder.binding.tvTotalRupees.setText(Constants.Key.RupeeSign+historyModel.finalPackageAmt);
        holder.binding.tvTotalRupees.setPaintFlags(holder.binding.tvTotalRupees.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       // holder.binding.tvDay.setText(historyModel.Day);

        holder.binding.llInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderHistoryActivity)context).downloadInvoice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryModelArrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemOrderHistoryBinding binding;

        public MyHolder(@NonNull ItemOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
