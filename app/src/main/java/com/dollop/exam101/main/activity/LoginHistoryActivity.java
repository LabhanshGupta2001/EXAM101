package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityLoginHistoryBinding;
import com.dollop.exam101.main.adapter.LoginHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHistoryActivity extends BaseActivity implements View.OnClickListener {
    ApiService apiService;
    Activity activity = LoginHistoryActivity.this;
    ActivityLoginHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        apiService = RetrofitClient.getClient();
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvLoginHistory.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvLoginHistory.setAdapter(new LoginHistoryAdapter(activity, list));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
            /*  Utils.I(activity,MockTestHistoryActivity.class,null);*/
        }
    }

    void getloginHistory() {
        HashMap<String, String> hashMap = new HashMap<>();
        apiService.getloginHistory(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

}