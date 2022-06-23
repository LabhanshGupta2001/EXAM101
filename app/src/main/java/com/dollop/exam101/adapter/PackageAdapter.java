package com.dollop.exam101.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemCourseBinding;
import com.dollop.exam101.databinding.ItemPackagesBinding;
import com.dollop.exam101.model.CourseModel;
import com.dollop.exam101.model.PackageModel;

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