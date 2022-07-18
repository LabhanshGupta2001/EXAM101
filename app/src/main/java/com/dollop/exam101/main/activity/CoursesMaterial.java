package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCoursesMaterialBinding;
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
    private Boolean dropdown = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.ivBack.setOnClickListener(this);

    }

    void init() {

        apiService = RetrofitClient.getClient();

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
        Utils.I(activity, CourseListActivity.class, null);
        finish();
    }

    private void getCourseMaterialProgressBar() {
        apiService.getCourseMaterialProgressBar("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    private void getCourseMaterialList() {
        apiService.getCourseMaterialList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

}