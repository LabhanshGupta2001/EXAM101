package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityNotificationBinding;
import com.dollop.exam101.main.adapter.NotificationPrimaryAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = NotificationActivity.this;
    ActivityNotificationBinding binding;
    NotificationPrimaryAdapter notificationAdapter;
    List<String> notificationList = new ArrayList<>();

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
        else if(view ==binding.llToolbar){
            Utils.I(activity,AffilationBankDetailsActivity.class,null);
        }
    }
}