package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = BankDetailActivity.this;
    ActivityBankDetailBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        apiService= RetrofitClient.getClient();

        binding.UpdateBankDetail.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    private void GetBankUserDetails(){
        apiService.GetBankUserDetails("").enqueue(new Callback<AllResponseModel>() {
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
        if (view == binding.UpdateBankDetail) {
            Utils.I(activity,UpdateBankDetailsActivity.class,null);
        }
        else if(view==binding.ivBack){
            finish();
        }
    }
}