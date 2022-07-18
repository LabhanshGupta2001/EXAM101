package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCourseTestBinding;
import com.dollop.exam101.main.adapter.CourseTestQuestionAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTestActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CourseTestActivity.this;
    ActivityCourseTestBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.ivAbout.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        getQuestin();

    }

    void getQuestin() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("1) What is Software ");
        arrayList.add("2) what is Android");
        arrayList.add("3) what is XML");
        arrayList.add("4) what is Pointer");
        arrayList.add("5) what is Intent And How To Use it what is Intent And How To Use it ");
        binding.rvQuestion.setAdapter(new CourseTestQuestionAdapter(activity, arrayList));
        binding.rvQuestion.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.ivAbout) {
            Toast.makeText(activity, "About This App", Toast.LENGTH_SHORT).show();
        } else if (view == binding.btnSubmit) {
            Utils.I(activity, TestResultActivity.class, null);
        }
    }

    private void getMyPakagesList() {
        apiService.getMyPakagesList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}