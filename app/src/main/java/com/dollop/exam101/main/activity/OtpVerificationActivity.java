package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityOtpVerificationBinding;

public class OtpVerificationActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity;
ActivityOtpVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_otp_verification);*/
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity= OtpVerificationActivity.this;
        init();
    }
    private void init(){
        binding.VerifyOTPId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.VerifyOTPId) {
            Utils.I(OtpVerificationActivity.this, ResetPasswordActivity.class,null);
        }

    }
}