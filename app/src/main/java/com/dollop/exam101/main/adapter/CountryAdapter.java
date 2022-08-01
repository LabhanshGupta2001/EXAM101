package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.CountryAdapterBinding;
import com.dollop.exam101.main.activity.EditProfileActivity;
import com.dollop.exam101.main.activity.SignUpActivity;
import com.dollop.exam101.main.model.CountryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {
    public String countryKey, Where;
    Context context;
    List<CountryModel> countryList;
    List<CountryModel> filterList;
    int Position = 0;


    public CountryAdapter(Context activity, ArrayList<CountryModel> contryItemArrayList, String CountryCode, String From) {
        this.context = activity;
        this.countryList = contryItemArrayList;
        this.filterList = contryItemArrayList;
        countryKey = CountryCode;
        Where = From;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @SuppressLint("RecycleView") int viewType) {
        CountryAdapterBinding binding = CountryAdapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CountryModel itemCountry = filterList.get(position);
        holder.binding.etCodeId.setText(itemCountry.phoneCode);
        holder.binding.tvNameId.setText(itemCountry.countryName);
        Picasso.get().load(Const.HOST_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivCountryFlags);
        Picasso.get().load(Const.HOST_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivStartCountryFlags);
        holder.binding.llItem.setOnClickListener(view -> {
            Position = holder.getAdapterPosition();
            SavedData.saveCountryUuId(itemCountry.uuid);
            SavedData.saveCountryKey(itemCountry.sortName);
            notifyDataSetChanged();
            if (Constants.Key.Login.equals(Where)){
                ((SignUpActivity) context).onCountrySelected(itemCountry.uuid , itemCountry.countryName /*itemCountry.phoneCode, itemCountry.flag*/);
            } else if (Constants.Key.EditProfile.equals(Where)){
                ((EditProfileActivity) context).onCountrySelectedE(itemCountry.uuid , itemCountry.countryName /*itemCountry.phoneCode, itemCountry.flag*/);
            }
        });
        if (Constants.Key.Country_Code_Nan.equals(countryKey)){
            holder.binding.cvCountryFlagStart.setVisibility(View.VISIBLE);
            holder.binding.etCodeId.setVisibility(View.GONE);
            holder.binding.cvCountryFlag.setVisibility(View.GONE);
        } else {
            holder.binding.cvCountryFlagStart.setVisibility(View.GONE);
            holder.binding.etCodeId.setVisibility(View.VISIBLE);
            holder.binding.cvCountryFlag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterList = countryList;
                } else {
                    List<CountryModel> filteredList = new ArrayList<>();
                    for (CountryModel row : countryList) {
                        if (row.countryName.toLowerCase().contains(charString.toLowerCase()) || row.phoneCode.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (List<CountryModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CountryAdapterBinding binding;

        public ViewHolder(@NonNull CountryAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
