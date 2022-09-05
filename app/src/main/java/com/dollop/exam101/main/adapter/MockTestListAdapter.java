package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomSheetStartTestBinding;
import com.dollop.exam101.databinding.ItemMockTestListBinding;
import com.dollop.exam101.main.activity.MockTestHistoryActivity;
import com.dollop.exam101.main.activity.MockTestQuestionsActivity;
import com.dollop.exam101.main.model.StudentMockTest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MockTestListAdapter extends RecyclerView.Adapter<MockTestListAdapter.MyViewHolder> {
    Context context;
    ViewGroup viewGroup;
    List<StudentMockTest> list;

    @NonNull
    BottomSheetStartTestBinding bottomSheetStartTestBinding;
    BottomSheetDialog bottomSheetDialog;

    @SuppressLint("NotifyDataSetChanged")
    public MockTestListAdapter(Context context, List<StudentMockTest> list) {
        this.context = context;
        this.list= list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestListBinding binding = ItemMockTestListBinding.inflate(LayoutInflater.from(context), parent, false);
        viewGroup = parent;
        setBottomSheet();
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvRemainingDay.setText(list.get(position).remainingDays + " DAYS");
        holder.binding.tvName.setText(list.get(position).mockTestName);
        holder.binding.tvPurchasDate.setText(TimeFormatter.changeDateFormat(list.get(position).createdDtm));
        if (list.get(position).remainingDays.equalsIgnoreCase("0"))
        {
            holder.binding.tvExpired.setVisibility(View.VISIBLE);
            holder.binding.llDaysLeft.setVisibility(View.GONE);
            holder.binding.llItemMockTestList.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.low_white)));
        }else{
            holder.binding.tvExpired.setVisibility(View.GONE);
            holder.binding.llDaysLeft.setVisibility(View.VISIBLE);
            holder.binding.llItemMockTestList.setForeground(null);
            holder.binding.tvAttempts.setText(list.get(position).remainingAttempt);
            holder.binding.tvExpiryDate.setText("Expiry Date: " + TimeFormatter.changeDateFormat(list.get(position).packageExpiryDtm));
            holder.binding.tvHistory.setOnClickListener(v -> Utils.I(context, MockTestHistoryActivity.class, null));

            holder.binding.llStartTest.setOnClickListener(view ->
            {
                bottomSheetDialog.show();
                bottomSheetStartTestBinding.llBtnStartTest.setOnClickListener(view1 ->
                {
                    bottomSheetDialog.cancel();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.Key.orderMockTestId, list.get(position).orderMockTestId);
                    bundle.putString(Constants.Key.orderMockTestUuid, list.get(position).orderMockTestUuid);
                    Utils.I(context, MockTestQuestionsActivity.class, bundle);
                });
            });
        }
    }

    private void setBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetStartTestBinding = BottomSheetStartTestBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        bottomSheetDialog.setContentView(bottomSheetStartTestBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestListBinding binding;

        public MyViewHolder(@NonNull ItemMockTestListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
