package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
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


    public CountryAdapter(Context activity, ArrayList<CountryModel> contryItemArrayList, String from, String From) {
        this.context = activity;
        this.countryList = contryItemArrayList;
        this.filterList = contryItemArrayList;
        countryKey = from;
        Where = From;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @SuppressLint("RecycleView") int viewType) {
        CountryAdapterBinding binding = CountryAdapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CountryModel itemCountry = filterList.get(position);

        holder.binding.etCodeId.setText(itemCountry.phoneCode);
        holder.binding.tvNameId.setText(itemCountry.countryName);
        Picasso.get().load(Const.FLAG_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivCountryFlags);
        Picasso.get().load(Const.FLAG_URL + itemCountry.flag).error(R.drawable.ic_india).into(holder.binding.ivStartCountryFlags);

        if (TextUtils.equals("Login", Where)) {
            holder.binding.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    SavedData.saveCountryId(itemCountry.countryId);
                    ((SignUpActivity) context).onCountrySelected(itemCountry.countryId, itemCountry.phoneCode, itemCountry.countryName, itemCountry.flag);
                }
            });
        } else if (TextUtils.equals("EditProfile", Where)) {
            holder.binding.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    SavedData.saveCountryId(itemCountry.countryId);
                    ((EditProfileActivity) context).onCountrySelectedE(itemCountry.countryId, itemCountry.phoneCode, itemCountry.countryName, itemCountry.flag);
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
                }else {
                    List<CountryModel> filteredList = new ArrayList<>();
                    for (CountryModel row : countryList) {
                        if (row.countryName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        } else if (row.phoneCode.toLowerCase().contains(charString.toLowerCase())){
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        CountryAdapterBinding binding;

        public ViewHolder(@NonNull CountryAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
