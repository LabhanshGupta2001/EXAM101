package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity= ForgotPasswordActivity.this;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_forget_password);*/
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.SendOtpId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.SendOtpId) {
            Intent intent = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
            startActivity(intent);

    }
}
}