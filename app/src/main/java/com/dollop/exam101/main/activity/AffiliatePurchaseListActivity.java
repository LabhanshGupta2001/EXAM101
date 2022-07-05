package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityAffiliatePurchaseListBinding;

public class AffiliatePurchaseListActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = AffiliatePurchaseListActivity.this;
    ActivityAffiliatePurchaseListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffiliatePurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }
    private void init(){

    }

    @Override
    public void onClick(View v) {

    }
}