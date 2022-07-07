package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityContactUsBinding;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=ContactUsActivity.this;
    ActivityContactUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.llSave.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.etMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==binding.llSave){
           finish();

        } else if(view==binding.ivBack){
            finish();
        } else if(view==binding.etMessage){
        }
    }
}