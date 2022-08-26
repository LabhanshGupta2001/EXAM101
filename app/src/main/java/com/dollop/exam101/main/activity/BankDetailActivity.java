package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.dollop.exam101.main.model.AffilliatDetailModel;
import com.dollop.exam101.main.model.AllResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BankDetailActivity.this;
    ActivityBankDetailBinding binding;
    AffilliatDetailModel affilliatDetailModel;


    ApiService apiService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //affilliatDetailModel = bundle.getParcelable(Constants.Key.BankDetails);
            affilliatDetailModel = (AffilliatDetailModel) bundle.getSerializable(Constants.Key.BankDetails);
        }
        Utils.E("affilliatDetailModelBankDetail::"+affilliatDetailModel);
        if (AppController.getInstance().isOnline()) {

        } else {
            //Utils.InternetDialog(activity);
        }
        setUserDetails();
        binding.llUpdateBankDetail.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    private void setUserDetails() {
        binding.tvAccountHolderName.setText(affilliatDetailModel.accPayeeName);
        binding.tvAccountNumber.setText(affilliatDetailModel.accNumber);
        binding.tvAccountType.setText(affilliatDetailModel.accType);
        binding.tvIfscCode.setText(affilliatDetailModel.ifscCode);
    }


    private void GetBankUserDetails() {
        apiService.GetBankUserDetails("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.llUpdateBankDetail) {
            Utils.I(activity, UpdateBankDetailsActivity.class, null);
        } else if (view == binding.ivBack) {
            finish();
        }
    }
}