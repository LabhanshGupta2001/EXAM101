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
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCoursesDetailBinding;
import com.dollop.exam101.databinding.BottomSheetPracticeTestBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesDetailActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CoursesDetailActivity.this;
    ActivityCoursesDetailBinding binding;
    BottomSheetPracticeTestBinding bottomSheetPracticeTestBinding;
    BottomSheetDialog bottomSheetDialog;
    ApiService apiService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        if (AppController.getInstance().isOnline()) {

        } else {
            Utils.InternetDialog(activity);
        }
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if ((view == binding.btnNext)) {
            showBottomSheetDialog();
        }

    }

    private void getCourseDetails() {
        apiService.getCourseDetails("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void showBottomSheetDialog() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetPracticeTestBinding = BottomSheetPracticeTestBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetPracticeTestBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetPracticeTestBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

        bottomSheetPracticeTestBinding.tvBtnPracticeTest.setOnClickListener(view ->
        {
            Utils.I(activity, CourseTestActivity.class, null);
        });

    }

}