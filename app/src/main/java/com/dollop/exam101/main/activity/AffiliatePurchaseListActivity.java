package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityAffiliatePurchaseListBinding;
import com.dollop.exam101.databinding.BottomSheetAffiliatePurchaseListBinding;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.PurchaseListFragment;
import com.dollop.exam101.main.fragment.TransactionHistoryFragment;
import com.dollop.exam101.main.model.AffilliatDetailModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AffiliatePurchaseListActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = AffiliatePurchaseListActivity.this;
    ActivityAffiliatePurchaseListBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetAffiliatePurchaseListBinding bottomSheetAffiliatePurchaseListBinding;
    ApiService apiService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffiliatePurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);

        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add(Constants.Key.PurchaseList);
        title.add(Constants.Key.TransactionHistory);

        fragments.add(new PurchaseListFragment());
        fragments.add(new TransactionHistoryFragment());

        binding.vpLaunchId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.tvFilter) {
            bottomSheetFilterTask();
        }
    }

    private void bottomSheetFilterTask() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetAffiliatePurchaseListBinding = BottomSheetAffiliatePurchaseListBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetAffiliatePurchaseListBinding.getRoot());
        bottomSheetDialog.show();

    }
}