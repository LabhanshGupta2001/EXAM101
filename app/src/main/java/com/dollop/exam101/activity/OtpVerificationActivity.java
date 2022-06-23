package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.databinding.ActivityOtpVerificationBinding;

public class OtpVerificationActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity= OtpVerificationActivity.this;
ActivityOtpVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_otp_verification);*/
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.VerifyOTPId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.VerifyOTPId) {
            Intent intent = new Intent(OtpVerificationActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        }

    }
}