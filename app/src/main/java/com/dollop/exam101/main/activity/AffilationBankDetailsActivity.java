package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityAffilationBankDetailsBinding;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AffilationBankDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = AffilationBankDetailsActivity.this;
    ActivityAffilationBankDetailsBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffilationBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        apiService= RetrofitClient.getClient();

        binding.llSubmit.setOnClickListener(this);
        binding.etHolderName.setOnClickListener(this);
        binding.etAccountNumber.setOnClickListener(this);
        binding.etIfscCode.setOnClickListener(this);
        binding.etBranch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    private void GetUserBankProfile(){
        apiService.getBankProfile("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    private void BankUserDetails(){
        HashMap<String,String>hashMap=new HashMap<>();
       apiService.UserBankDetails(hashMap).enqueue(new Callback<AllResponseModel>() {
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
        if (view == binding.llSubmit) {
            finish();
        } else if (view == binding.ivBack) {
            onBackPressed();
        }

    }
}