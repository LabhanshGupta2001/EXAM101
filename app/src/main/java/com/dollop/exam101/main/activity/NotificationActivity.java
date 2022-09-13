package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityNotificationBinding;
import com.dollop.exam101.main.adapter.NotificationPrimaryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        apiService = RetrofitClient.getClient();
        //getNotification();

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
        }
    }

    void getNotification() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getNotifications(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                    } else {
                        assert response.body() != null;
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            APIError message = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).charStream(), APIError.class);
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            APIError message = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).charStream(), APIError.class);
                            Utils.T(activity, message.message);
                            Utils.UnAuthorizationToken(activity);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMassage::" + t.getMessage());
            }
        });
    }

}