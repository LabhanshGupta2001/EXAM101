package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.databinding.ItemLanguageBinding;
import com.dollop.exam101.main.model.LanguageModel;

import java.util.ArrayList;
import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyHolder> {
    public String languageId = "";
    Context context;
    String From;
    List<LanguageModel> languageModels;
    public int index = -1;

    public LanguageAdapter(List<LanguageModel> list, Context context, String from) {
        this.context = context;
        this.languageModels =  list;
        From = from;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding binding = ItemLanguageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        LanguageModel languageModel = languageModels.get(position);
        holder.binding.tvLanguage.setText(languageModel.languageName);
        holder.binding.checkBox.setChecked(index == position);
        holder.binding.llLanguage.setOnClickListener(v -> {
            index = position;
            languageId = languageModels.get(position).languageId;
            notifyDataSetChanged();

        });

    }

    @Override
    public int getItemCount() {
        return languageModels.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemLanguageBinding binding;

        public MyHolder(@NonNull ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
