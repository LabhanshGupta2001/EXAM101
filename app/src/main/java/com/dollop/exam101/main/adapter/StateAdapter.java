package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemStateBinding;
import com.dollop.exam101.main.activity.EditProfileActivity;
import com.dollop.exam101.main.activity.SignUpActivity;
import com.dollop.exam101.main.model.CountryModel;
import com.dollop.exam101.main.model.StateModel;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
    Context context;
    List<StateModel> stateList;
    int Position = 0;
    String Click;


    public StateAdapter(Context activity, ArrayList<StateModel> stateItemArrayList,String from) {
        this.context = activity;
        this.stateList = stateItemArrayList;
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
        StateModel itemState = stateList.get(position);
        holder.binding.tvState.setText(itemState.stateName);

        if (TextUtils.equals("ClickProfile",Click)){
            holder.binding.llState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    ((EditProfileActivity)context).onStateSelectedE(itemState.stateName);
                }
            });
        } else if (TextUtils.equals("ClickSign",Click)){
            holder.binding.llState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    ((SignUpActivity)context).onStateSelected(itemState.stateName);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemStateBinding binding;

        public ViewHolder(@NonNull ItemStateBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
    }

}
