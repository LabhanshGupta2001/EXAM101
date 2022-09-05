package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityMockTestHistoryBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.fragment.AllResultsFragment;
import com.dollop.exam101.main.fragment.PerformanceFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setFragments();
    }

    //Guru
    void setFragments() {
        Tittle.add(getString(R.string.all_results));
        Tittle.add(getString(R.string.performance));
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