package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemCategoryDetailsSecondaryBinding;
import com.dollop.exam101.main.model.PackageModel;

import java.util.ArrayList;

public class CategoryDetailSecondaryAdapter extends RecyclerView.Adapter<CategoryDetailSecondaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<PackageModel> packageModelList;
    int row_index = -1;

    public CategoryDetailSecondaryAdapter(Context context, ArrayList<PackageModel> list) {
        this.context = context;
        this.packageModelList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryDetailsSecondaryBinding binding = ItemCategoryDetailsSecondaryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PackageModel packageModel = packageModelList.get(position);
        holder.binding.packageName.setText(packageModel.packageName);
        holder.binding.tvDescriptionId.setText(packageModel.shortDesc);
        holder.binding.tvRupees.setText(packageModel.discountedPrice);
        holder.binding.tvTotalRupees.setText(packageModel.actualPrice);
        holder.binding.tvDay.setText(packageModel.validity + context.getString(R.string.Days));
        if (packageModel.rating.equals(Constants.Key.blank)) {
            holder.binding.tvRatingCount.setVisibility(View.GONE);
            holder.binding.tvRatingNum.setVisibility(View.GONE);
            holder.binding.ivStar.setVisibility(View.GONE);
        } else {
            holder.binding.tvRatingCount.setVisibility(View.GONE);
            holder.binding.tvRatingNum.setText(packageModel.rating);
            holder.binding.tvRatingNum.setVisibility(View.VISIBLE);
            holder.binding.ivStar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return packageModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryDetailsSecondaryBinding binding;

        public MyViewHolder(@NonNull ItemCategoryDetailsSecondaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
