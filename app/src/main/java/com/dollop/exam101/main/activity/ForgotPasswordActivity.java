package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_forget_password);*/
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity= ForgotPasswordActivity.this;
        init();
    }
    private void init(){
        binding.SendOtpId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.SendOtpId) {
            Utils.I(ForgotPasswordActivity.this, OtpVerificationActivity.class,null);
        }
}
}