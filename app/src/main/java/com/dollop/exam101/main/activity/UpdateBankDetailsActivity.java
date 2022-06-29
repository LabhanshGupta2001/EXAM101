package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityUpdateBankDetailsBinding;

public class UpdateBankDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=UpdateBankDetailsActivity.this;
    ActivityUpdateBankDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==binding.llSave){
            Utils.I(activity,ContactUsActivity.class,null);
        } else if(view==binding.ivBack){
            finish();
        }
    }
}