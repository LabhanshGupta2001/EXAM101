package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCheckEmailBinding;

public class CheckEmailActivity extends BaseActivity {
    Activity activity = CheckEmailActivity.this;
    ActivityCheckEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvReturnToSignIn.setOnClickListener(v -> Utils.I(activity, LoginActivity.class, null));
    }
}