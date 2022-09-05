package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemPurchaseListBinding;
import com.dollop.exam101.main.model.AffiliatePurchaseModel;
import com.dollop.exam101.main.model.AffilliatPurchaseListModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.Myholder> {
    Context context;
    List<AffiliatePurchaseModel> affiliatePurchaseModelArrayList;
    boolean dropdown = false;

    public PurchaseListAdapter(Context context, List<AffiliatePurchaseModel> list) {
        this.context = context;
        this.affiliatePurchaseModelArrayList = list;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPurchaseListBinding binding = ItemPurchaseListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Myholder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecycleView") int position) {
        AffiliatePurchaseModel affiliatePurchaseModel = affiliatePurchaseModelArrayList.get(position);
        holder.binding.tvUserName.setText(affiliatePurchaseModel.studentName);
        holder.binding.tvEmail.setText(affiliatePurchaseModel.studentEmail);
        holder.binding.tvMobileNo.setText(affiliatePurchaseModel.studentMobileNo);
        holder.binding.tvPackageName.setText(affiliatePurchaseModel.packageName);
        holder.binding.tvPkgPrice.setText("₹"+new DecimalFormat("##.##").format(Double.parseDouble(affiliatePurchaseModel.pkgPrice)));
        holder.binding.tvPercentage.setText(new DecimalFormat("##.##").format(Double.parseDouble(affiliatePurchaseModel.pkgAffCommisionPercent))+"%");
        holder.binding.tvTotalAmt.setText("₹"+new DecimalFormat("##.##").format(Double.parseDouble(affiliatePurchaseModel.pkgAffCommisionAmt)));

        holder.binding.llUserCard.setOnClickListener(v -> {
            if (!dropdown) {
                holder.binding.llPrice.setVisibility(View.VISIBLE);
                holder.binding.ivDropdown.animate().rotation(-90).setDuration(100).start();
                dropdown = true;
            } else {
                holder.binding.llPrice.setVisibility(View.GONE);
                holder.binding.ivDropdown.animate().rotation(90).setDuration(100).start();
                dropdown = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return affiliatePurchaseModelArrayList.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {
        ItemPurchaseListBinding binding;
        public Myholder(@NonNull  ItemPurchaseListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
