package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityMockTestHistoryBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.fragment.AllResultsFragment;
import com.dollop.exam101.main.fragment.PerformanceFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MockTestHistoryActivity extends BaseActivity implements View.OnClickListener {

    MockTestViewPagerAdapter mockTestViewPagerAdapter;
    Activity activity = MockTestHistoryActivity.this;
    ActivityMockTestHistoryBinding binding;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> Tittle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMockTestHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        Tittle.add("All Results");
        Tittle.add("Performance");

        fragments.add(new AllResultsFragment());
        fragments.add(new PerformanceFragment());
        mockTestViewPagerAdapter = new MockTestViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.viewpagertwo.setAdapter(mockTestViewPagerAdapter);

        new TabLayoutMediator(binding.tlMain, binding.viewpagertwo, (tab, position) -> {
            tab.setText(Tittle.get(position));
        }).attach();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }
}