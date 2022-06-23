package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity;
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_login);*/
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity= LoginActivity.this;
        init();
    }
    private void init(){
        binding.tvForgetPasswordId.setOnClickListener(this);
        binding.SignInId.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.tvForgetPasswordId) {
            Utils.I(activity, ForgotPasswordActivity.class,null);
        }else if (view==binding.SignInId){
            Utils.I(activity, DashboardScreenActivity.class,null);
        }else if (view==binding.tvSignUp){
            Utils.I(activity, SignUpActivity.class,null);
        }
    }
}

/* if (v == binding.cvViewCart) {
          Bundle bundle = new Bundle();
          bundle.putString(Constants.From, Constants.Restaurant);
          Utils.I(activity, DashboardActivity.class, bundle);
        }*/