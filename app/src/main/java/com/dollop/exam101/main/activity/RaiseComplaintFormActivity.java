package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityRaiseComplaintFormBinding;

public class RaiseComplaintFormActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = RaiseComplaintFormActivity.this;
    ActivityRaiseComplaintFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.tvNormal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == binding.ivBack) {
            finish();
        }else if(view == binding.llSave) {
          finish();
        }else if(view==binding.tvNormal){

        }
    }
}