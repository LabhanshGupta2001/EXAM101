package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCoursesMaterialBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.main.adapter.CourseMaterialSubjectAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesMaterial extends BaseActivity implements View.OnClickListener {
    Activity activity = CoursesMaterial.this;
    ActivityCoursesMaterialBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    private final Boolean dropdown = true;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.ivBack.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {

        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {

        } else {
           // Utils.InternetDialog(activity);
            InternetDialog();
        }
        setProgress();
        getCourseList();
    }

    void setProgress() {
        binding.progressBar.setProgress(50);
        binding.progressBar.setMax(100);
    }

    void getCourseList() {
        list.clear();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        binding.rvCourseList.setHasFixedSize(true);
        binding.rvCourseList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvCourseList.setAdapter(new CourseMaterialSubjectAdapter(activity, list));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            Utils.I(activity, CourseListActivity.class, null);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getCourseMaterialProgressBar() {
        apiService.getCourseMaterialProgressBar("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void getCourseMaterialList() {
        apiService.getCourseMaterialList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity);
        AlertdialogBinding alertDialogBinding = AlertdialogBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        alertDialogBinding.tvPermittManually.setText(R.string.retry);
        alertDialogBinding.tvDesc.setText(R.string.please_check_your_connection);
        alertDialogBinding.tvPermittManually.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}