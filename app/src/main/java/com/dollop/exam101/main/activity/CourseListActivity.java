package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityCourseListBinding;
import com.dollop.exam101.main.adapter.CourseListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseListActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CourseListActivity.this;
    ActivityCourseListBinding binding;
    ApiService apiService;
    ArrayList<CourseListModel> courseListModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


    }

    void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);

        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "20", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "20", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "20", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "0", "30 DAYS"));

        CourseListAdapter adapter = new CourseListAdapter(activity, courseListModels);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.rvCourseList.setLayoutManager(linearLayoutManager3);
        binding.rvCourseList.setAdapter(adapter);

    }

    private void getCourseList() {
        apiService.getCourseList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();

        }
    }


}