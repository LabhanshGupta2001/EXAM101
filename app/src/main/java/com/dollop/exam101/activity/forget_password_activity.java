package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityForgetPasswordBinding;
import com.dollop.exam101.databinding.ActivityLoginBinding;

public class forget_password_activity extends AppCompatActivity implements View.OnClickListener {
    Activity activity=forget_password_activity.this;
    ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_forget_password);*/
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.SendOtpId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.SendOtpId) {
            Intent intent = new Intent(forget_password_activity.this, otp_verification_activity.class);
            startActivity(intent);

    }
}
}