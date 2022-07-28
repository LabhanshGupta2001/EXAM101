package com.dollop.exam101.main.activity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityAffilationBankDetailsBinding;
import com.dollop.exam101.databinding.ActivityProfileBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AffilationBankDetailsActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = AffilationBankDetailsActivity.this;
    ActivityAffilationBankDetailsBinding binding;
    ApiService apiService;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffilationBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {

        apiService = RetrofitClient.getClient();

        binding.llSubmit.setOnClickListener(this);
        binding.etHolderName.setOnClickListener(this);
        binding.etAccountNumber.setOnClickListener(this);
        binding.etIfscCode.setOnClickListener(this);
        binding.etBranch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        if (AppController.getInstance().isOnline()) {

        } else {
           // Utils.InternetDialog(activity);
        }
    }

    private void GetUserBankProfile() {
        apiService.getBankProfile("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void BankUserDetails() {
        HashMap<String, String> hashMap = new HashMap<>();
        apiService.UserBankDetails(hashMap).enqueue(new Callback<AllResponseModel>() {
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
        if (view == binding.llSubmit) {
            SavedData.SaveBankStatus(Constants.Key.pending);
            finish();

        } else if (view == binding.ivBack) {
            onBackPressed();
        }

    }
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                final boolean globalVisibleRect;
                globalVisibleRect = v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}