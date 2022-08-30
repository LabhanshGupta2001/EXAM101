package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.databinding.ActivityAffiliatePurchaseListBinding;
import com.dollop.exam101.databinding.BottomSheetAffiliatePurchaseListBinding;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.PurchaseListFragment;
import com.dollop.exam101.main.fragment.TransactionHistoryFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AffiliatePurchaseListActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = AffiliatePurchaseListActivity.this;
    ActivityAffiliatePurchaseListBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetAffiliatePurchaseListBinding bottomSheetAffiliatePurchaseListBinding;
    ApiService apiService;
    public String fromDate = "", toDate = "", strDate = "";

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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        strDate = sdf.format(c.getTime());

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
                bottomSheetDialog.dismiss();
            }
        });

    }

    private void pickDate(TextView tvDate) {
        Date date = null;
        String myFormat = "dd/MM/yyyy";
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
}