package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivitySearchHistoryBinding;
import com.dollop.exam101.main.adapter.FlexBoxAdapter;
import com.dollop.exam101.main.adapter.ResentSearchHistoryAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class SearchHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = SearchHistoryActivity.this;
    ActivitySearchHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();

    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    FlexBoxAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvSearchHistory.setAdapter(new ResentSearchHistoryAdapter(activity, list));
        binding.rvSearchHistory.setHasFixedSize(true);
        binding.rvSearchHistory.setLayoutManager(new LinearLayoutManager(activity));


        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        binding.rvFlexBox.setHasFixedSize(true);
        binding.rvFlexBox.setLayoutManager(layoutManager);
        binding.rvFlexBox.setAdapter(new FlexBoxAdapter(activity, arrayList));

    }

    private void init(){

    }

    @Override
    public void onClick(View view) {

    }
}