package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityOrderConfirmedBinding;

public class OrderConfirmedActivity extends BaseActivity {
    Activity activity = OrderConfirmedActivity.this;
    ActivityOrderConfirmedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderConfirmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSeePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(activity, CourseListActivity.class, null);
            }
        });
    }
}