package com.dollop.exam101.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.databinding.ActivityDownloadHistoryBinding;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.DonloadPdfFragment;
import com.dollop.exam101.main.fragment.DownloadVideoFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DownloadHistory extends AppCompatActivity implements View.OnClickListener {
    ActivityDownloadHistoryBinding binding;
    ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(this);
        fragments.add(new DownloadVideoFragment());
        fragments.add(new DonloadPdfFragment());
        title.clear();
        title.add(Constants.Key.courseVideo);
        title.add(Constants.Key.coursePDF);
        binding.viewPagerHome.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivBack) {
            onBackPressed();
        }
    }
}