package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ForgotPasswordActivity.this;
        init();
    }

    private void init() {
        binding.SendOtpId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.SendOtpId) {
            //Utils.I(activity, CheckEmailActivity.class, null);
            Utils.I(activity,CheckEmailActivity.class,null);
        }
    }
}