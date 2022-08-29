package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBankDetailBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.BankDetailsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BankDetailActivity.this;
    ActivityBankDetailBinding binding;
    ApiService apiService;
    List<BankDetailsModel> bankDetailsModels = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetBankDetail();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {

        } else {
            Utils.InternetDialog(activity);
        }
        binding.llUpdateBankDetail.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.llUpdateBankDetail) {
            Utils.I(activity, UpdateBankDetailsActivity.class, null);
        } else if (view == binding.ivBack) {
            finish();
        } else if (view == binding.tvCopy) {
            binding.tvCopy.setBackground(ContextCompat.getDrawable(activity, R.drawable.flex_bg));
            binding.tvAffiliateCodeId.setTextIsSelectable(true);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", binding.tvAffiliateCodeId.getText().toString().trim());
            clipboard.setPrimaryClip(clip);
            Utils.T(activity, Constants.Key.Copy);
        }
    }

    private void GetBankDetail() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBankDetails(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        bankDetailsModels.clear();
                        assert response.body() != null;
                        BankDetailsModel bankDetailsModel = response.body().bankDetailsModel;
                        bankDetailsModels.add(response.body().bankDetailsModel);
                        binding.tvAccountNumber.setText(bankDetailsModel.accNumber);
                        binding.tvAccountHolderName.setText(bankDetailsModel.accPayeeName);
                        binding.tvIfscCode.setText(bankDetailsModel.ifscCode);
                        binding.tvAffiliateCodeId.setText(bankDetailsModel.affiliateCode);
                        binding.tvAccountType.setText(bankDetailsModel.accType + Constants.Key.Account);

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