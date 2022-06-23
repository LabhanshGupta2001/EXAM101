package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityLoginBinding;
import com.dollop.exam101.databinding.ActivityResetPasswordBinding;

public class reset_password_activity extends AppCompatActivity implements View.OnClickListener {
Activity activity=reset_password_activity.this;
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
        Intent intent=new Intent(reset_password_activity.this,Login_Activity.class);
        startActivity(intent);
    }
}