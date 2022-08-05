package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemPakagesDetailsPrimaryBinding;
import com.dollop.exam101.main.model.ModuleModel;
import com.dollop.exam101.main.model.SubjectModel;

import java.util.ArrayList;

public class PakageDetailPrimaryAdapter extends RecyclerView.Adapter<PakageDetailPrimaryAdapter.MyViewHolder> {
    Context context;
    ArrayList<SubjectModel> list;
    ArrayList<ModuleModel> stringArrayList=new ArrayList<>();
    public PakageDetailPrimaryAdapter(Context context, ArrayList<SubjectModel> arrayList) {
        this.context = context;
        this.list = arrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPakagesDetailsPrimaryBinding binding= ItemPakagesDetailsPrimaryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectModel subjectModel =  list.get(position);
       /* stringArrayList.clear();
        stringArrayList.add("1");*/
        stringArrayList.addAll(stringArrayList);
        Utils.E("stringArrayList::"+stringArrayList);
        holder.binding.tvSubject1.setText(subjectModel.subjectName);
        holder.binding.rvSecond.setAdapter(new PakageDetailSecondaryAdapter(context,stringArrayList));
        holder.binding.rvSecond.setLayoutManager(new LinearLayoutManager(context));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPakagesDetailsPrimaryBinding binding;
        public MyViewHolder(@NonNull ItemPakagesDetailsPrimaryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
