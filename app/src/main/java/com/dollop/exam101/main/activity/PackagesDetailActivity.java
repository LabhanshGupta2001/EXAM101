package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityPackagesDetailBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.fragment.CourseMaterialFragment;
import com.dollop.exam101.main.fragment.MockTestFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = PackagesDetailActivity.this;
    ActivityPackagesDetailBinding binding;
    ApiService apiService;
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

        apiService = RetrofitClient.getClient();
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
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    private void getPakageDetails() {
        apiService.getPakageDetails("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}