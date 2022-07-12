package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityOtpVerificationBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity implements View.OnClickListener {
Activity activity;
ActivityOtpVerificationBinding binding;
ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_otp_verification);*/
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity= OtpVerificationActivity.this;
        init();
    }
    private void init(){
        apiService= RetrofitClient.getClient();
        binding.VerifyOTPId.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.VerifyOTPId) {
            Utils.I(OtpVerificationActivity.this, ResetPasswordActivity.class,null);
        }

    }
    void otpVerification(){
        HashMap<String, String> hm = new HashMap<>();
        apiService.otpVerification(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

}