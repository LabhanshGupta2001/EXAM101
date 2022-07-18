package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.OnRecycleViewItemCountryClick;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.CountryAdapterBinding;
import com.dollop.exam101.main.activity.EditProfileActivity;
import com.dollop.exam101.main.activity.LoginActivity;
import com.dollop.exam101.main.activity.SignUpActivity;
import com.dollop.exam101.main.model.CountryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    public String countryKey,Where;
    Context context;
    List<CountryModel> countryList;
    int Position = 0;


    public CountryAdapter(Context activity, ArrayList<CountryModel> contryItemArrayList,String from,String From){
        this.context = activity;
        this.countryList = contryItemArrayList;
        countryKey = from;
        Where = From;
        Utils.E("cl" + countryList.size());
        Utils.E("cIl" + contryItemArrayList.size());
        Utils.E("cIllll" + countryKey);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @SuppressLint("RecycleView") int viewType) {
        CountryAdapterBinding binding = CountryAdapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CountryModel itemCountry = countryList.get(position);


        holder.binding.etCodeId.setText(itemCountry.phoneCode);
        holder.binding.tvNameId.setText(itemCountry.countryName);
        Picasso.get().load(Const.FLAG_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivCountryFlags);
        Picasso.get().load(Const.FLAG_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivStartCountryFlags);

        if (TextUtils.equals("Login",Where)){
            holder.binding.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    ((SignUpActivity)context).onCountrySelected(itemCountry.countryId,itemCountry.phoneCode,itemCountry.countryName,itemCountry.flag);
                }
            });
        } else if (TextUtils.equals("EditProfile",Where)){
            holder.binding.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    ((EditProfileActivity)context).onCountrySelectedE(itemCountry.countryId,itemCountry.phoneCode,itemCountry.countryName,itemCountry.flag);
                }
            });
        } else if (TextUtils.equals("No", countryKey)) {
            holder.binding.cvCountryFlagStart.setVisibility(View.VISIBLE);
            holder.binding.etCodeId.setVisibility(View.GONE);
            holder.binding.cvCountryFlag.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CountryAdapterBinding binding;

        public ViewHolder(@NonNull CountryAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
