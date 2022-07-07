package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ChangePasswordActivity.this;
    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        binding.ivBack.setOnClickListener(this);
        binding.llSavePassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==binding.ivBack){
            onBackPressed();
        }
        if (view == binding.llSavePassword)
        {
            onBackPressed();
        }
    }
}