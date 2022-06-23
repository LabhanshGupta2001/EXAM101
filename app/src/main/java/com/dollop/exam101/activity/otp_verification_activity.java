package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityForgetPasswordBinding;
import com.dollop.exam101.databinding.ActivityOtpVerificationBinding;

public class otp_verification_activity extends AppCompatActivity implements View.OnClickListener {
Activity activity=otp_verification_activity.this;
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
            Intent intent = new Intent(otp_verification_activity.this, reset_password_activity.class);
            startActivity(intent);
        }

    }
}