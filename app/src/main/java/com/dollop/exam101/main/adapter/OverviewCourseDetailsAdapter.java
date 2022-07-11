package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemOverviewCourseDetailsBinding;

import java.util.ArrayList;

public class OverviewCourseDetailsAdapter  extends RecyclerView.Adapter<OverviewCourseDetailsAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> list;
    ArrayList<String> stringArrayList=new ArrayList<>();
    private Boolean dropdown = false;

    public OverviewCourseDetailsAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OverviewCourseDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOverviewCourseDetailsBinding binding=ItemOverviewCourseDetailsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new OverviewCourseDetailsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewCourseDetailsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.mcvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dropdown) {
                    holder.binding.llShowText.setVisibility(View.VISIBLE);
                    holder.binding.ivArrow.animate().rotation(0).setDuration(100).start();
                    dropdown = true;
                } else {
                    holder.binding.llShowText.setVisibility(View.GONE);
                    holder.binding.ivArrow.animate().rotation(180).setDuration(100).start();
                    dropdown = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemOverviewCourseDetailsBinding binding;
        public MyViewHolder(@NonNull ItemOverviewCourseDetailsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
