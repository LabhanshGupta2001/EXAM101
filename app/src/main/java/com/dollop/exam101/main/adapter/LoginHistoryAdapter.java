package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.databinding.ItemLoginHistoryBinding;
import com.dollop.exam101.main.model.LoginHistoryDatum;

import java.text.ParseException;
import java.util.ArrayList;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<LoginHistoryDatum> list;
    private Boolean dropdown = true;


    public LoginHistoryAdapter(Context context, ArrayList<LoginHistoryDatum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoginHistoryBinding binding = ItemLoginHistoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LoginHistoryDatum loginHistoryDatum = list.get(position);
        try {
            holder.binding.tvDate.setText(TimeFormatter.getDateTime(loginHistoryDatum.loginHistoryDtm, context, "yyyy-MM-dd HH:mm:ss", "Date"));
            holder.binding.tvTime.setText(TimeFormatter.getDateTime(loginHistoryDatum.loginHistoryDtm, context, "yyyy-MM-dd hh:mm:ss", "time"));
            holder.binding.tvPlatform.setText(loginHistoryDatum.platform);
            holder.binding.tvIp.setText(loginHistoryDatum.machineIp);
            holder.binding.tvAgentString.setText(loginHistoryDatum.agentString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.binding.llMain.setOnClickListener(view -> {
                if (!dropdown) {
                    holder.binding.llLogoutDevice.setVisibility(View.VISIBLE);
                    holder.binding.ivUpperarrow.animate().rotation(180).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.llLogoutDevice.setVisibility(View.GONE);
                    holder.binding.ivUpperarrow.animate().rotation(0).setDuration(100).start();
                    dropdown = false;
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemLoginHistoryBinding binding;

        public MyViewHolder(@NonNull ItemLoginHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
