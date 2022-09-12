package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemPackagesBinding;
import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.activity.SearchHistoryActivity;
import com.dollop.exam101.main.model.PackageModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyPackageViewHolder> {
    private final ArrayList<PackageModel> packageModelModelsList;
    private final Context context;
    ArrayList<PackageModel> FilterList;

    public PackageAdapter(Context context, ArrayList<PackageModel> packageModelModelsList) {
        this.packageModelModelsList = packageModelModelsList;
        this.context = context;
        this.FilterList = packageModelModelsList;
    }


    @NonNull
    @Override
    public PackageAdapter.MyPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageAdapter.MyPackageViewHolder(ItemPackagesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.MyPackageViewHolder holder, int position) {

        Utils.E("Size><??:::" + FilterList.size());
        PackageModel packageModel = FilterList.get(position);
        holder.itemPackagesBinding.tvPackageHeading.setText(packageModel.packageName);
        holder.itemPackagesBinding.tvPackageDescription.setText(packageModel.shortDesc);
        holder.itemPackagesBinding.tvRupees.setText(new DecimalFormat("##.##").format(Double.parseDouble(packageModel.discountedPrice)));
        holder.itemPackagesBinding.tvTotalRupees.setText(new DecimalFormat("##.##").format(Double.parseDouble(packageModel.actualPrice)));
        holder.itemPackagesBinding.tvDay.setText(packageModel.validity + context.getString(R.string.Days));

        if (packageModel.rating.equals(Constants.Key.blank)) {
            holder.itemPackagesBinding.tvRatingCount.setVisibility(View.GONE);
            holder.itemPackagesBinding.tvRatingNum.setVisibility(View.GONE);
            holder.itemPackagesBinding.ivStar.setVisibility(View.GONE);
            holder.itemPackagesBinding.verticalView.setVisibility(View.GONE);
        } else {
            holder.itemPackagesBinding.tvRatingCount.setVisibility(View.GONE);
            holder.itemPackagesBinding.tvRatingNum.setText(packageModel.rating);
            holder.itemPackagesBinding.tvRatingNum.setVisibility(View.VISIBLE);
            holder.itemPackagesBinding.ivStar.setVisibility(View.VISIBLE);
            holder.itemPackagesBinding.verticalView.setVisibility(View.VISIBLE);
        }

        holder.itemPackagesBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( context instanceof SearchHistoryActivity ) {
                   ((SearchHistoryActivity) context).setSearchText(packageModel.packageName.trim());
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.packageUuId, packageModel.packageUuid);
                Utils.I(context, PackagesDetailActivity.class, bundle);
                Utils.E("Bundle :::::: " + bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return FilterList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    FilterList = packageModelModelsList;
                } else {
                    ArrayList<PackageModel> filteredList = new ArrayList<>();
                    for (PackageModel row : packageModelModelsList) {
                        if (row.packageName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    FilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = FilterList;

                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                FilterList = (ArrayList<PackageModel>) results.values;
                if (FilterList.size() == 0){
                    ((SearchHistoryActivity)context).setPage();

                }
                else {
                    ((SearchHistoryActivity)context).gonePage();

                }

                notifyDataSetChanged();
            }
        };
    }

    public static class MyPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemPackagesBinding itemPackagesBinding;

        public MyPackageViewHolder(ItemPackagesBinding itemPackagesBinding) {
            super(itemPackagesBinding.getRoot());
            this.itemPackagesBinding = itemPackagesBinding;

        }
    }

}