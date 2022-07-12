package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivityForgotPasswordBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ForgotPasswordActivity.this;
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.SendOtpId.setOnClickListener(this);
    }
    private void ForgetPassword(){
        HashMap<String, String> hm = new HashMap<>();
        apiService.ForgetPassword(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.SendOtpId) {
            //Utils.I(activity, CheckEmailActivity.class, null);
            Utils.I(activity,CheckEmailActivity.class,null);
        }
    }
}