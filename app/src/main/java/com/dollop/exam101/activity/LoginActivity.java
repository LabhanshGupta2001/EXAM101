package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity= LoginActivity.this;
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_login);*/
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.tvForgetPasswordId.setOnClickListener(this);
        binding.SignInId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.tvForgetPasswordId) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }else if (view==binding.SignInId){
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}

/* if (v == binding.cvViewCart) {
          Bundle bundle = new Bundle();
          bundle.putString(Constants.From, Constants.Restaurant);
          Utils.I(activity, DashboardActivity.class, bundle);
        }*/