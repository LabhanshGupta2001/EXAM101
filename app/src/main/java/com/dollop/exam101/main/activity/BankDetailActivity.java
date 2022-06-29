package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;

public class BankDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = BankDetailActivity.this;
    ActivityBankDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.UpdateBankDetail.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.UpdateBankDetail) {
            Utils.I(activity,UpdateBankDetailsActivity.class,null);
        }
        else if(view==binding.ivBack){
            finish();
        }
    }
}