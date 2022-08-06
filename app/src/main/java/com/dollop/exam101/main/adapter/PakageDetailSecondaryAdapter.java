package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemPakagesDetailsSecondryBinding;
import com.dollop.exam101.main.model.ModuleModel;
import com.dollop.exam101.main.model.TopicDetailModel;

import java.util.ArrayList;
import java.util.List;

public class PakageDetailSecondaryAdapter extends RecyclerView.Adapter<PakageDetailSecondaryAdapter.MyViewHolder> {
    Context context;
    List<ModuleModel> moduleModelArrayList;
    List<TopicDetailModel> stringArrayList=new ArrayList<>();
    private Boolean dropdown = true;

    public PakageDetailSecondaryAdapter(Context context, List<ModuleModel> list) {
        this.context = context;
        this.moduleModelArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPakagesDetailsSecondryBinding binding=ItemPakagesDetailsSecondryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModuleModel moduleModel = moduleModelArrayList.get(position);
       /* stringArrayList.clear();
        stringArrayList.add("1");*/
        holder.binding.tvModule.setText(moduleModel.moduleName);

        stringArrayList = (ArrayList<TopicDetailModel>) moduleModel.topics;
        Utils.E("stringArrayListTopic::"+stringArrayList);
        holder.binding.rvThird.setAdapter(new PakageDetailTernaryAdapter(context,stringArrayList));
        holder.binding.rvThird.setLayoutManager(new LinearLayoutManager(context));

        holder.binding.mcvZtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dropdown) {
                    holder.binding.rvThird.setVisibility(View.VISIBLE);
                    holder.binding.ivRotedArrow.animate().rotation(0).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.rvThird.setVisibility(View.GONE);
                    holder.binding.ivRotedArrow.animate().rotation(180).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPakagesDetailsSecondryBinding binding;
        public MyViewHolder(@NonNull ItemPakagesDetailsSecondryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
