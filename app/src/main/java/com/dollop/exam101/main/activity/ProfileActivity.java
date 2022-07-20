package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = ProfileActivity.this;
    ActivityProfileBinding binding;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.tvUserName.setText(Utils.GetSession().studentName);
        binding.tvUserEmail.setText(Utils.GetSession().studentEmail);
        //binding.ivProfile.setImageResource();

        binding.ivBack.setOnClickListener(this);
        binding.llOderHistory.setOnClickListener(this);
        binding.llEditProfile.setOnClickListener(this);
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
            Utils.I(activity, DashboardScreenActivity.class, null);
            activity.finish();
        } else if (view == binding.llOderHistory) {
            Utils.I(activity, OrderHistoryActivity.class, null);
        } else if (view == binding.llEditProfile) {
            Utils.I(activity, EditProfileActivity.class, null);
        } else if (view == binding.llNotifications) {
            Utils.I(activity, NotificationActivity.class, null);
        } else if (view == binding.llMockTest) {
            Utils.I(activity, MockTestListActivity.class, null);
        } else if (view == binding.llRequestAffilation) {
            Utils.I(activity, AffilationBankDetailsActivity.class, null);
            //  Utils.I(activity, OrderHistoryActivity.class,null);
        } else if (view == binding.llInviteFriend) {
            // Utils.I(activity, SettingActivity.class, null);
        } else if (view == binding.llWishList) {
            Utils.I(activity, MyWishlistActivity.class, null);
        } else if (view == binding.tvLogOut) {
            Utils.UnAuthorizationToken(activity);

        }
    }



}