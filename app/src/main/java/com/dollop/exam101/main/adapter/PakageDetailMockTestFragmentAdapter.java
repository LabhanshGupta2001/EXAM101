package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMockTestPackageBinding;
import com.dollop.exam101.main.model.MockTestModel;

import java.util.ArrayList;
import java.util.List;

public class PakageDetailMockTestFragmentAdapter extends RecyclerView.Adapter<PakageDetailMockTestFragmentAdapter.MyViewHolder> {

    Context context;
    List<MockTestModel> mockTestModels;

    public PakageDetailMockTestFragmentAdapter(Context context, List<MockTestModel> mockTestModels) {
        this.context = context;
        this.mockTestModels = mockTestModels;
    }


    @NonNull
    @Override
    public PakageDetailMockTestFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestPackageBinding binding = ItemMockTestPackageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PakageDetailMockTestFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MockTestModel mockTestModel=mockTestModels.get(position);
        holder.binding.tvTitle.setText(mockTestModel.mockTestName);
        holder.binding.tvAttemptsNumber.setText(mockTestModel.attempts);
        holder.binding.tvDesc.setText(mockTestModel.duration);
        holder.binding.tvAttempts.setText(mockTestModel.questionCnt);
    }

    @Override
    public int getItemCount() {
        return mockTestModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMockTestPackageBinding binding;

        public MyViewHolder(@NonNull ItemMockTestPackageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
