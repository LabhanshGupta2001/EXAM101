package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityAffilationBankDetailsBinding;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;

import okhttp3.internal.Util;

public class AffilationBankDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = AffilationBankDetailsActivity.this;
    ActivityAffilationBankDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffilationBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.llSubmit.setOnClickListener(this);
        binding.etHolderName.setOnClickListener(this);
        binding.etAccountNumber.setOnClickListener(this);
        binding.etIfscCode.setOnClickListener(this);
        binding.etBranch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.llSubmit) {
            Utils.I(activity,BankDetailActivity.class,null);
        } else if(view==binding.ivBack){
            onBackPressed();
        }

    }
}