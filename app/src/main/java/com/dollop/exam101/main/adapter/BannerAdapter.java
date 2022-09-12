package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemVpBannerBinding;
import com.dollop.exam101.main.model.BannerModel;
import com.dollop.exam101.main.model.HomeBannerOfferModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    Context context;
    ArrayList<HomeBannerOfferModel> banners;
    ArrayList<BannerModel> bannerModelArrayList ;

    public BannerAdapter(Context context,   ArrayList<BannerModel>  banners) {
        this.bannerModelArrayList = banners;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVpBannerBinding binding = ItemVpBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BannerModel bannerModel = bannerModelArrayList.get(position);

        Picasso.get().load(Const.Url.HOST_URL + bannerModel.bannerImage)
                .placeholder(R.drawable.vpbannerimage).error(R.drawable.vpbannerimage).into(holder.binding.banner);

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
