package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemPackageDetailBinding;

import com.dollop.exam101.main.model.ReviewRating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PakageDetailRatingAdapter extends RecyclerView.Adapter<PakageDetailRatingAdapter.MyViewHolder> {
    Context context;
    ArrayList<ReviewRating> list;


    public PakageDetailRatingAdapter(Context context, ArrayList<ReviewRating> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPackageDetailBinding binding= ItemPackageDetailBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ReviewRating reviewRatingModel=list.get(position);
        holder.binding.tvTitleId.setText(reviewRatingModel.studentName);
        holder.binding.tvrating.setText(reviewRatingModel.rating);
        holder.binding.tvDesc.setText(reviewRatingModel.review);
        holder.binding.tvDateTime.setText(reviewRatingModel.createdDtm);
        Utils.Picasso(reviewRatingModel.profilePic,holder.binding.ivProfileUSer, R.drawable.dummy);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPackageDetailBinding binding;
        public MyViewHolder(@NonNull ItemPackageDetailBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
