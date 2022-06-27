package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomsheetRaiseComplaintsBinding;
import com.dollop.exam101.databinding.ItemRaiseComplaintBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class RaiseComplaintAdapter extends RecyclerView.Adapter<RaiseComplaintAdapter.MyViewHolder> {
    Context context;
    List<String> list;
    int row_index = -1;
    BottomSheetDialog bottomSheetDialog;
    BottomsheetRaiseComplaintsBinding binding;

    public RaiseComplaintAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRaiseComplaintBinding binding = ItemRaiseComplaintBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(context);
                binding = BottomsheetRaiseComplaintsBinding.inflate(LayoutInflater.from(context));
                bottomSheetDialog.setContentView(binding.getRoot());
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRaiseComplaintBinding binding;

        public MyViewHolder(@NonNull ItemRaiseComplaintBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
