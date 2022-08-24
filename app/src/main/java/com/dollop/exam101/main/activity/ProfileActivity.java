package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = ProfileActivity.this;
    ActivityProfileBinding binding;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onResume() {
        super.onResume();
        setData();
        if (SavedData.getBankStatus().equals(Constants.Key.pending)) {
            binding.llRequestAffilation.setVisibility(View.GONE);
            binding.llRequestPendingCode.setVisibility(View.VISIBLE);
        } else {
            binding.llRequestAffilation.setVisibility(View.VISIBLE);
            binding.llRequestPendingCode.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        setData();
        binding.ivBack.setOnClickListener(this);
        binding.llOderHistory.setOnClickListener(this);
        binding.llEditProfile.setOnClickListener(this);
        binding.llNotifications.setOnClickListener(this);
        binding.llRequestAffilation.setOnClickListener(this);
        binding.llNotifications.setOnClickListener(this);
        binding.llInviteFriend.setOnClickListener(this);
        binding.llWishList.setOnClickListener(this);
        binding.tvLogOut.setOnClickListener(this);
        binding.llRequestPendingCode.setOnClickListener(this);
        binding.llBankDetails.setOnClickListener(this);
        binding.llAffiliatePurchaseList.setOnClickListener(this);
        binding.tvInviteFriends.setOnClickListener(this);
        // binding.llMockTest.setOnClickListener(this);


    }

    private void setData() {

        binding.tvUserName.setText(Utils.GetSession().studentName);
        binding.tvUserEmail.setText(Utils.GetSession().studentEmail);
        if (Utils.GetSession().profilePic != null && (!Utils.GetSession().profilePic.equals(""))) {
            Utils.Picasso(Utils.GetSession().profilePic, binding.ivProfile, R.drawable.dummy);
        }
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
        } else if (view == binding.llRequestAffilation) {
          //  Utils.I(activity, AffilationBankDetailsActivity.class, null);
           Utils.I(activity, UpdateBankDetailsActivity.class, null);
        } else if (view == binding.tvInviteFriends) {
        } else if (view == binding.llBankDetails) {
            Utils.I(activity, BankDetailActivity.class, null);
        } else if (view == binding.llAffiliatePurchaseList) {
            Utils.I(activity, AffiliatePurchaseListActivity.class, null);
        } else if (view == binding.llWishList) {
            Utils.I(activity, MyWishlistActivity.class, null);
        } else if (view == binding.tvLogOut) {
            Utils.logoutAlertDialog(activity);
        }/*else if (view == binding.llMockTest) {
            Utils.I(activity, MockTestListActivity.class, null);
        }*/
    }


}