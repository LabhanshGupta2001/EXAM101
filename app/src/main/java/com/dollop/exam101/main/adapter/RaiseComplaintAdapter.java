package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.databinding.BottomsheetRaiseComplaintsBinding;
import com.dollop.exam101.databinding.ItemRaiseComplaintBinding;
import com.dollop.exam101.main.activity.RaiseComplaintActivity;
import com.dollop.exam101.main.model.ComplaintModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class RaiseComplaintAdapter extends RecyclerView.Adapter<RaiseComplaintAdapter.MyViewHolder> {
    Context context;
    ArrayList<ComplaintModel> complaintModelArrayList;


    public RaiseComplaintAdapter(Context context, ArrayList<ComplaintModel> list) {
        this.context = context;
        this.complaintModelArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRaiseComplaintBinding binding = ItemRaiseComplaintBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ComplaintModel complaintModel  = complaintModelArrayList.get(position);
        holder.binding.tvComplaintId.setText(Constants.Key.ComplaintID+complaintModel.complaintId);
        holder.binding.tvComplaintSubject.setText(complaintModel.complaintSubject);
        holder.binding.tvPriority.setText(complaintModel.complaintPriority);
        if (complaintModel.complaintStatus.equals(Constants.Key.Active)){
            holder.binding.mcvActive.setVisibility(View.VISIBLE);
            holder.binding.mcvClosed.setVisibility(View.GONE);
        }else {
            holder.binding.Dote.setVisibility(View.GONE);
            holder.binding.mcvActive.setVisibility(View.GONE);
            holder.binding.mcvClosed.setVisibility(View.VISIBLE);
        }

        holder.binding.tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RaiseComplaintActivity)context).BottomSheetTask(complaintModel);
               /* bottomSheetDialog = new BottomSheetDialog(context);
                binding = BottomsheetRaiseComplaintsBinding.inflate(LayoutInflater.from(context));
                bottomSheetDialog.setContentView(binding.getRoot());
                bottomSheetDialog.show();
                binding.llSave.setOnClickListener(view1 ->
                {
                    bottomSheetDialog.cancel();
                });*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRaiseComplaintBinding binding;

        public MyViewHolder(@NonNull ItemRaiseComplaintBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
