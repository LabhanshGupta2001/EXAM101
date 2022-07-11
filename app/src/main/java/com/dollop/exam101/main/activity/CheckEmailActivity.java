package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCheckEmailBinding;

public class CheckEmailActivity extends AppCompatActivity {
    Activity activity = CheckEmailActivity.this;
    ActivityCheckEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvReturnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(activity,LoginActivity.class,null);
            }
        });
    }
}