package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityChangePasswordBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ChangePasswordActivity.this;
    ActivityChangePasswordBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){

        apiService= RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
        binding.llSavePassword.setOnClickListener(this);
    }

    private void ChangePassword(){
        apiService.ChangePassword("").enqueue(new Callback<AllResponseModel>() {
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
        if(view==binding.ivBack){
            onBackPressed();
        }
        if (view == binding.llSavePassword)
        {
            onBackPressed();
        }
    }
}