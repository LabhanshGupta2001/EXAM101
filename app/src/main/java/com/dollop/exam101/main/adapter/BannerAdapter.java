package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemVpBannerBinding;
import com.dollop.exam101.main.activity.BlogDetailActivity;
import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.model.BannerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    Context context;
    ArrayList<BannerModel> bannerModelArrayList;

    public BannerAdapter(Context context, ArrayList<BannerModel> banners) {
        this.bannerModelArrayList = banners;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVpBannerBinding binding = ItemVpBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BannerModel bannerModel = bannerModelArrayList.get(position);
        Picasso.get().load(Const.Url.HOST_URL + bannerModel.bannerImage)
                .error(R.drawable.vpbannerimage).into(holder.binding.banner);
        holder.binding.banner.setOnClickListener(view -> {

            if(!bannerModel.redirectUuid.equals("")){
                if (context.getString(R.string.custom).equals(bannerModel.bannerFor)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(bannerModel.redirectUuid));
                    context.startActivity(i);
                } else if (context.getString(R.string.blog).equals(bannerModel.bannerFor)) {
                    Bundle blogBundle = new Bundle();
                    blogBundle.putString(Constants.Key.uuid, bannerModel.redirectUuid);
                    Utils.I(context, BlogDetailActivity.class, blogBundle);
                } else if (context.getString(R.string.packageBanner).equals(bannerModel.bannerFor)) {
                    Bundle packageBundle = new Bundle();
                    packageBundle.putString(Constants.Key.packageUuId,  bannerModel.redirectUuid);
                    Utils.I(context, PackagesDetailActivity.class, packageBundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerModelArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemVpBannerBinding binding;

        public MyViewHolder(@NonNull ItemVpBannerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

}
