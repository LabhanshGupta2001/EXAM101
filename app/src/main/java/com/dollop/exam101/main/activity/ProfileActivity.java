package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ProfileActivity.this;
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llOderHistory.setOnClickListener(this);
        binding.llEditProfile.setOnClickListener(this);
        binding.llCourseList.setOnClickListener(this);
        binding.llNotifications.setOnClickListener(this);
        binding.llRequestAffilation.setOnClickListener(this);
        binding.llNotifications.setOnClickListener(this);
        binding.llInviteFriend.setOnClickListener(this);
        binding.llWishList.setOnClickListener(this);
        binding.llMockTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack){
            onBackPressed();
        } else if (view == binding.llOderHistory){
            Utils.I(activity,OrderHistoryActivity.class,null);
        }
        else if (view == binding.llEditProfile){
            Utils.I(activity,EditProfileActivity.class,null);
        }
        else if (view == binding.llCourseList){
            Utils.I(activity,CourseListActivity.class,null);
        }
        else if (view == binding.llNotifications){
            Utils.I(activity,NotificationActivity.class,null);
        }else if (view == binding.llRequestAffilation){
            Utils.I(activity,AffilationBankDetailsActivity.class,null);
          //  Utils.I(activity, OrderHistoryActivity.class,null);
        } else if (view == binding.llNotifications){
            Utils.I(activity, BlogsListActivity.class,null);
        }else if (view == binding.llInviteFriend){
            Utils.I(activity, SettingActivity.class,null);
        } else if (view == binding.llWishList){
       //     Utils.I(activity, MyW.class,null);
        }
    }


}