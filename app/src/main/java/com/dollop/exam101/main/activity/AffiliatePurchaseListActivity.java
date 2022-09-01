package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

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
import com.dollop.exam101.main.model.AffilliatPurchaseSummaryModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AffiliatePurchaseListActivity extends BaseActivity implements View.OnClickListener {
    public String fromDate = "", toDate = "", strDate = "";
    public ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    Activity activity = AffiliatePurchaseListActivity.this;
    ApiService apiService;
    ActivityAffiliatePurchaseListBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetAffiliatePurchaseListBinding bottomSheetAffiliatePurchaseListBinding;
    AffilliatPurchaseSummaryModel affilliatPurchaseSummaryModel;
    ArrayList<AllResponseModel> list = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

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
        if (AppController.getInstance().isOnline()) {
            getAffiliatePurchaseSummary();
        } else {
            // InternetDialog();
        }

        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(c.getTime());


        title.add(Constants.Key.PurchaseList);
        fragments.add(new PurchaseListFragment(Constants.Key.blank, Constants.Key.blank));
        title.add(Constants.Key.TransactionHistory);
        fragments.add(new TransactionHistoryFragment(list));
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.vpLaunchId.setAdapter(viewPagerFragmentAdapter);

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
        bottomSheetAffiliatePurchaseListBinding.tvToDate.setText(strDate);
        bottomSheetAffiliatePurchaseListBinding.tvFromDate.setText(strDate);

        bottomSheetAffiliatePurchaseListBinding.mcvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickDate(bottomSheetAffiliatePurchaseListBinding.tvFromDate);
            }
        });


        bottomSheetAffiliatePurchaseListBinding.mcvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickDate(bottomSheetAffiliatePurchaseListBinding.tvToDate);
            }
        });

        bottomSheetAffiliatePurchaseListBinding.tvBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate = bottomSheetAffiliatePurchaseListBinding.tvFromDate.getText().toString().trim();
                toDate = bottomSheetAffiliatePurchaseListBinding.tvToDate.getText().toString().trim();
                //purchaseListFragment.getAffiliatePurchaseList(fromDate, toDate);

                fragments.clear();
                fragments.add(new PurchaseListFragment(fromDate, toDate));
                fragments.add(new TransactionHistoryFragment(list));
                viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
                binding.vpLaunchId.setAdapter(viewPagerFragmentAdapter);
                // new PurchaseListFragment().getAffiliatePurchaseList(fromDate, toDate,activity);
                bottomSheetDialog.dismiss();


            }
        });

    }

    private void pickDate(TextView tvDate) {
        Date date = null;
        String myFormat = "yyyy-MM-dd";
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat inputFormat = new SimpleDateFormat(myFormat);
        try {
            date = inputFormat.parse(tvDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tvDate.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide previous date,month and year
        //calendar.add(Calendar.YEAR, 0);
        // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.show();
    }

    private void getAffiliatePurchaseSummary() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getAffiliatePurchaseSummary(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        affilliatPurchaseSummaryModel = response.body().affilliatPurchaseSummaryModel;
                        Utils.E("affilliatPurchaseSummaryModel::::" + affilliatPurchaseSummaryModel);
                        binding.tvTotalRupees.setText(new DecimalFormat("##.##").format(Double.parseDouble(String.valueOf(affilliatPurchaseSummaryModel.grandTotalAmt))));
                        binding.tvTotalPaidRupees.setText("₹" + new DecimalFormat("##.##").format(Double.parseDouble(String.valueOf(affilliatPurchaseSummaryModel.receivedAmt))));

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