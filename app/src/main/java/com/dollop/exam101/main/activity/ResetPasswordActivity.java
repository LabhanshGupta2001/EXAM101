package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity= ResetPasswordActivity.this;
ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_reset_password);*/
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.ChangePasswordId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}