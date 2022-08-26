package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.Basics.UtilityTools.Utils;

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
    List<StudentMockTest> list=new ArrayList<>();

    @NonNull
    BottomSheetStartTestBinding bottomSheetStartTestBinding;
    BottomSheetDialog bottomSheetDialog;

    public MockTestListAdapter(Context context, List<StudentMockTest> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestListBinding binding=ItemMockTestListBinding.inflate(LayoutInflater.from(context),parent,false);
        viewGroup=parent;
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
      //  StudentMockTest studentMockTest= ;
        holder.binding.tvExpired.setText(list.get(position).packageExpiryDtm);
        holder.binding.tvAttempts.setText(list.get(position).attemptRemaining);
        holder.binding.tvName.setText(list.get(position).mockTestName);
        holder.binding.tvRemainingDay.setText(list.get(position).remainingDays+" DAYS");
        holder.binding.tvExpiryDate.setText("Expiry Date: "+TimeFormatter.changeDateFormat(list.get(position).packageExpiryDtm));
        holder.binding.tvPurchasDate.setText(TimeFormatter.changeDateFormat(list.get(position).createdDtm));

        holder.binding.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(context, MockTestHistoryActivity.class,null);
            }
        });
        holder.binding.llStartTest.setOnClickListener(view ->
        {
            bottomSheetDialog =new BottomSheetDialog(context);
            bottomSheetStartTestBinding = BottomSheetStartTestBinding.inflate(LayoutInflater.from(context),viewGroup,false);
            bottomSheetDialog.setContentView(bottomSheetStartTestBinding.getRoot());
            bottomSheetDialog.  show();

            bottomSheetStartTestBinding.llBtnStartTest.setOnClickListener(view1 ->
            {
                Utils.I(context, MockTestQuestionsActivity.class,null);

            });


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestListBinding binding;
        public MyViewHolder(@NonNull ItemMockTestListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}
