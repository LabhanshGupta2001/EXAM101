package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityThankYouPgBinding;

public class ThankYouPgActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ThankYouPgActivity.this;
    ActivityThankYouPgBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThankYouPgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        binding.tvBtnBackToHome.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

    }
}