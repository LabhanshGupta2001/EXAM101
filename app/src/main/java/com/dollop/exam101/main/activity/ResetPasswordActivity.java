package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity;
ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_reset_password);*/
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity= ResetPasswordActivity.this;
        init();
    }
    private void init(){
        binding.ChangePasswordId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Utils.I(ResetPasswordActivity.this, LoginActivity.class,null);
    }
}