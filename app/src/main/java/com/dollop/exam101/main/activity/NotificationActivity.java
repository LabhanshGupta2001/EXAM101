package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityNotificationBinding;
import com.dollop.exam101.main.adapter.NotificationPrimaryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = NotificationActivity.this;
    ActivityNotificationBinding binding;
    NotificationPrimaryAdapter notificationAdapter;
    List<String> notificationList = new ArrayList<>();
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llToolbar.setOnClickListener(this);
        apiService = RetrofitClient.getClient();


        notificationList.clear();
        notificationList.add("1");
        notificationList.add("1");
        notificationList.add("1");
        notificationAdapter = new NotificationPrimaryAdapter(activity, notificationList);
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llToolbar) {
            Utils.I(activity, AffilationBankDetailsActivity.class, null);
        }
    }

    void getNotification() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getNotification(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

}