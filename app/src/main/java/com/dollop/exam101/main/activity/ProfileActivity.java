package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        binding.tvLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            Utils.I_clear(activity, DashboardScreenActivity.class, null);
        } else if (view == binding.llOderHistory) {
            Utils.I(activity, OrderHistoryActivity.class, null);
        } else if (view == binding.llEditProfile) {
            Utils.I(activity, EditProfileActivity.class, null);
        } else if (view == binding.llCourseList) {
            Utils.I(activity, CourseListActivity.class, null);
        } else if (view == binding.llNotifications) {
            Utils.I(activity, NotificationActivity.class, null);
        } else if (view == binding.llRequestAffilation) {
            Utils.I(activity, AffilationBankDetailsActivity.class, null);
            //  Utils.I(activity, OrderHistoryActivity.class,null);
        } else if (view == binding.llInviteFriend) {
            /* Utils.I(activity, SettingActivity.class, null);*/
        } else if (view == binding.llWishList) {
            Utils.I(activity, MyWishlistActivity.class, null);
        } else if (view == binding.llMockTest) {
            Utils.I(activity, MockTestListActivity.class, null);
        } else if (view == binding.tvLogOut) {
            Utils.I_clear(activity, LoginActivity.class, null);
        }
    }


}