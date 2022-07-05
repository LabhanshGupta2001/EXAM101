package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemMockTestQuestionBinding;

import java.util.ArrayList;
import java.util.List;

public class MockTestQuestionAdapter extends RecyclerView.Adapter<MockTestQuestionAdapter.MyHolder> {
    ArrayList<String> arrayList = new ArrayList<>();
    Context context;
    private List<String> list;
    private ViewPager2 viewPager;

    public MockTestQuestionAdapter(List list, ViewPager2 viewPager, Context context) {
        this.list = list;
        this.viewPager = viewPager;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestQuestionBinding binding = ItemMockTestQuestionBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        holder.binding.rvRadioClickAns.setAdapter(new TestAnsAdapter(context, arrayList));
        holder.binding.rvRadioClickAns.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemMockTestQuestionBinding binding;


        public MyHolder(@NonNull ItemMockTestQuestionBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
