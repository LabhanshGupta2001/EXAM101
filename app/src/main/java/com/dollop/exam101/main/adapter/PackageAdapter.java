package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemPackagesBinding;
import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.model.PackageModel;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyPackageViewHolder> {
    private ArrayList<PackageModel> packageModelModelsList;
    private Context context;

    public PackageAdapter(Context context, ArrayList<PackageModel> packageModelModelsList) {
        this.packageModelModelsList = packageModelModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PackageAdapter.MyPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageAdapter.MyPackageViewHolder(ItemPackagesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.MyPackageViewHolder holder, int position) {
        PackageModel packageModel = packageModelModelsList.get(position);
        holder.itemPackagesBinding.tvPackageHeading.setText(packageModel.packageName);
        holder.itemPackagesBinding.tvPackageDescription.setText(packageModel.shortDesc);
        holder.itemPackagesBinding.tvRupees.setText(packageModel.discountedPrice);
        holder.itemPackagesBinding.tvTotalRupees.setText(packageModel.actualPrice);
        holder.itemPackagesBinding.tvDay.setText(packageModel.validity+context.getString(R.string.Days));
        if (packageModel.rating.equals(Constants.Key.blank)){
            holder.itemPackagesBinding.tvRatingCount.setVisibility(View.GONE);
            holder.itemPackagesBinding.tvRatingNum.setVisibility(View.GONE);
            holder.itemPackagesBinding.ivStar.setVisibility(View.GONE);
        } else {
            holder.itemPackagesBinding.tvRatingCount.setVisibility(View.GONE);
            holder.itemPackagesBinding.tvRatingNum.setText(packageModel.rating);
            holder.itemPackagesBinding.tvRatingNum.setVisibility(View.VISIBLE);
            holder.itemPackagesBinding.ivStar.setVisibility(View.VISIBLE);
        }

        holder.itemPackagesBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.I(context,PackagesDetailActivity.class,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageModelModelsList.size();
    }

    public static class MyPackageViewHolder extends RecyclerView.ViewHolder {
        private ItemPackagesBinding itemPackagesBinding;

        public MyPackageViewHolder(ItemPackagesBinding itemPackagesBinding) {
            super(itemPackagesBinding.getRoot());
            this.itemPackagesBinding = itemPackagesBinding;

        }
    }
}