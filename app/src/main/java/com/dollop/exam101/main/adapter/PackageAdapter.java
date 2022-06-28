package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemPackagesBinding;
import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.model.PackageModel;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyPackageViewHolder> {
    private ArrayList<PackageModel> packageModelsList;
    private Context context;

    public PackageAdapter(Context context, ArrayList<PackageModel> packageModelsList) {
        this.packageModelsList = packageModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PackageAdapter.MyPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageAdapter.MyPackageViewHolder(ItemPackagesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.MyPackageViewHolder holder, int position) {
        PackageModel packageModel = packageModelsList.get(position);
        holder.itemPackagesBinding.tvPackageHeading.setText(packageModel.packageHeading);
        holder.itemPackagesBinding.tvPackageDescription.setText(packageModel.packageDescription);

        holder.itemPackagesBinding.llInvoiceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PackagesDetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageModelsList.size();
    }

    public static class MyPackageViewHolder extends RecyclerView.ViewHolder {
        private ItemPackagesBinding itemPackagesBinding;

        public MyPackageViewHolder(ItemPackagesBinding itemPackagesBinding) {
            super(itemPackagesBinding.getRoot());
            this.itemPackagesBinding = itemPackagesBinding;

        }
    }
}