package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityAboutUsBinding;




public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = AboutUsActivity.this;
    ActivityAboutUsBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();


    }


    @Override
    public void onClick(View view) {

    }
}