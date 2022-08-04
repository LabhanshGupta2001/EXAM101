package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityPaymentFailedBinding;

public class PaymentFailedActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = PaymentFailedActivity.this;
    ActivityPaymentFailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentFailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.tvProceedToCheckoutId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvProceedToCheckoutId) {

        }
    }
}