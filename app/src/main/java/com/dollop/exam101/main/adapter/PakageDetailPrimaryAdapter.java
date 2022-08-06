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
import java.util.List;

public class PakageDetailPrimaryAdapter extends RecyclerView.Adapter<PakageDetailPrimaryAdapter.MyViewHolder> {
    Context context;
    List<SubjectModel> subjectModelArrayList;
    List<ModuleModel> stringArrayList=new ArrayList<>();
    public PakageDetailPrimaryAdapter(Context context, List<SubjectModel> arrayList) {
        this.context = context;
        this.subjectModelArrayList = arrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPakagesDetailsPrimaryBinding binding= ItemPakagesDetailsPrimaryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectModel subjectModel =  subjectModelArrayList.get(position);
       /* stringArrayList.clear();
        stringArrayList.add("1");*/
        holder.binding.tvSubject1.setText(subjectModel.subjectName);
      //  stringArrayList.addAll(subjectModel.modules);
        Utils.E("stringArrayListModule::"+stringArrayList);

        holder.binding.rvSecond.setAdapter(new PakageDetailSecondaryAdapter(context,stringArrayList));
        holder.binding.rvSecond.setLayoutManager(new LinearLayoutManager(context));
    }
    @Override
    public int getItemCount() {
        return subjectModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPakagesDetailsPrimaryBinding binding;
        public MyViewHolder(@NonNull ItemPakagesDetailsPrimaryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
