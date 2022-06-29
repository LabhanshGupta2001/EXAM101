package com.dollop.exam101.main.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.main.model.HomeBannerOfferModel;
import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    Context context;
    ArrayList<HomeBannerOfferModel> banners;

    public BannerAdapter(Context context, ArrayList<HomeBannerOfferModel> banners) {
        this.banners = banners;
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

        HomeBannerOfferModel offer = banners.get(position);
        holder.binding.banner.setImageResource(offer.bannerImage);
      /*  Picasso.get().load(Const.HOST_URL + offer.bannerImage)
                .placeholder(R.drawable.image_default_one).error(R.drawable.image_default_one).into(holder.binding.banner);*/

    }


    @Override
    public int getItemCount() {
        return banners.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemVpBannerBinding binding;

        public MyViewHolder(@NonNull ItemVpBannerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }


}
