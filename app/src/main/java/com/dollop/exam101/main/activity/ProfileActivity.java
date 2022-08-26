package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.dollop.exam101.main.model.AffilliatDetailModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = ProfileActivity.this;
    ActivityProfileBinding binding;
    ApiService apiService;
    AffilliatDetailModel affilliatDetailModel;



    @Override
    protected void onResume() {
        super.onResume();
        setData();
        GetAffiliateStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
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
            Utils.I(activity, AffilationBankDetailsActivity.class, null);
            // Utils.I(activity, UpdateBankDetailsActivity.class, null);
        } else if (view == binding.tvInviteFriends) {
        } else if (view == binding.llBankDetails) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.Key.BankDetails,affilliatDetailModel);
            Utils.I(activity, BankDetailActivity.class, bundle);
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

    private void GetAffiliateStatus() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getAffiliateStatus(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {

                        assert response.body() != null;
                         affilliatDetailModel = response.body().affilliatDetailModel;
                        if (affilliatDetailModel.reqStatus.equals(Constants.Key.Pending)) {
                            binding.llRequestPendingCode.setVisibility(View.VISIBLE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                        } else if (affilliatDetailModel.reqStatus.equals(Constants.Key.Approved)) {
                            binding.llRequestPendingCode.setVisibility(View.GONE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                            binding.llBankDetailsAndAffiliation.setVisibility(View.VISIBLE);
                        } else {
                            binding.llRequestAffilation.setVisibility(View.VISIBLE);
                        }

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

}