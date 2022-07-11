package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityUpdateBankDetailsBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBankDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ApiService apiService;
    Activity activity = UpdateBankDetailsActivity.this;
    ActivityUpdateBankDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.llSave) {
            Utils.I(activity, ContactUsActivity.class, null);
        } else if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    void userLogin() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.updateBankDetails(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }


}