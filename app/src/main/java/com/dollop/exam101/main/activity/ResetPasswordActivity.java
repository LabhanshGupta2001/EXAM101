package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityResetPasswordBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivityResetPasswordBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_reset_password);*/
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ResetPasswordActivity.this;
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ChangePasswordId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Utils.I(ResetPasswordActivity.this, LoginActivity.class, null);
    }

    void resetPassword() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.resetPassword(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}