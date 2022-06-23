package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityLoginBinding;
import com.dollop.exam101.databinding.ActivitySignupBinding;

public class signup_activity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = signup_activity.this;
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_signup);*/
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.SignUPId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.SignUPId) {
            Intent intent = new Intent(signup_activity.this, Login_Activity.class);
            startActivity(intent);
        }
    }
}