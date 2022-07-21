package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemCategoriesFragmentBinding;
import com.dollop.exam101.databinding.ItemExamFragmentBinding;
import com.dollop.exam101.main.fragment.ItemClickListener;
import com.dollop.exam101.main.model.CourseModel;

import java.util.List;

public class CategoriesFragmentAdapter extends RecyclerView.Adapter<CategoriesFragmentAdapter.MyViewHolder>{

    Context context;
    List<CourseModel> list;
    int index = -1;



    public CategoriesFragmentAdapter(List<CourseModel> list,  Context context) {
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoriesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoriesFragmentBinding binding = ItemCategoriesFragmentBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CategoriesFragmentAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CourseModel courseModel=list.get(position);
        holder.binding.tvItem.setText(courseModel.examName);

        holder.binding.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position){
            holder.binding.radioButton.setChecked(true);

        /*    holder.binding.tvItem.setTextColor(ContextCompat.getColor(context, R.color.theme));
            holder.binding.ivDropdown.setRotation(180);
            holder.binding.ivDropdown.setColorFilter(ContextCompat.getColor(context,R.color.theme));
            holder.binding.llCheckBox.setVisibility(View.VISIBLE);*/

        } else {
            holder.binding.radioButton.setChecked(false);
       /*     holder.binding.tvItem.setTextColor(ContextCompat.getColor(context, R.color.full_black));
            holder.binding.ivDropdown.setRotation(0);
            holder.binding.ivDropdown.setColorFilter(ContextCompat.getColor(context,R.color.sub_text));
            holder.binding.llCheckBox.setVisibility(View.GONE);*/
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoriesFragmentBinding binding;

        public MyViewHolder(@NonNull ItemCategoriesFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
