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

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemStateBinding;
import com.dollop.exam101.main.activity.EditProfileActivity;
import com.dollop.exam101.main.activity.SignUpActivity;
import com.dollop.exam101.main.model.StateModel;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder> implements Filterable {
    Context context;
    List<StateModel> stateList;
    List<StateModel> FilterList;
    int Position = 0;
    String Click;


    public StateAdapter(Context activity, ArrayList<StateModel> stateItemArrayList,String from) {
        this.context = activity;
        this.stateList = stateItemArrayList;
        this.FilterList = stateItemArrayList;
        Click = from;
        Utils.E("sl" + stateList.size());
        Utils.E("sil" + stateItemArrayList.size());
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStateBinding binding=ItemStateBinding.inflate(LayoutInflater.from(context),parent,false);
        return new StateAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        StateModel itemState = FilterList.get(position);
        holder.binding.tvState.setText(itemState.stateName);


            holder.binding.llState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    if (Constants.Key.ClickProfile.equals(Click)) {
                        ((EditProfileActivity) context).onStateSelectedE(itemState.stateName);
                    } else if (Constants.Key.ClickSign.equals(Click))
                         ((SignUpActivity)context).onStateSelected(itemState.stateName);
                }
            });

    }

    @Override
    public int getItemCount() {
        return FilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    FilterList = stateList;
                }else {
                    List<StateModel> filteredList = new ArrayList<>();
                    for (StateModel row : stateList) {
                        if (row.stateName.toLowerCase().contains(charString.toLowerCase())) {
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
                FilterList = (List<StateModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemStateBinding binding;

        public ViewHolder(@NonNull ItemStateBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
    }

}
