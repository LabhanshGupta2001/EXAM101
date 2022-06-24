package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.dollop.exam101.main.activity.OrderHistoryActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ProfileActivity.this;
    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llOderHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack){
            onBackPressed();
        } else if (view == binding.llOderHistory){
           Intent intent=new Intent(ProfileActivity.this, OrderHistoryActivity.class);
           startActivity(intent);
        }
    }
}
