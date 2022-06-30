package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.databinding.ActivityPackagesDetailBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.fragment.CourseMaterialFragment;
import com.dollop.exam101.main.fragment.MockTestFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PackagesDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = PackagesDetailActivity.this;
    ActivityPackagesDetailBinding binding;
    MockTestViewPagerAdapter mockTestViewPagerAdapter;
    ArrayList<String> Tittle = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackagesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);

        fragments.add(new CourseMaterialFragment());
        fragments.add(new MockTestFragment());
        Tittle.add("Course Material");
        Tittle.add("Mock Test");
        mockTestViewPagerAdapter = new MockTestViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.ViewPagerPackageDetailId.setAdapter(mockTestViewPagerAdapter);

        new TabLayoutMediator(binding.tlPackageDetailTabLayoutId, binding.ViewPagerPackageDetailId, (tab, position) -> {
            tab.setText(Tittle.get(position));
        }).attach();
    }

    @Override
    public void onClick(View view) {
        if(view==binding.ivBack){
            onBackPressed();
        }

    }
}