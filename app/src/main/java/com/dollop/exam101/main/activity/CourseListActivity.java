package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        apiService = RetrofitClient.getClient();

        if (AppController.getInstance().isOnline()) {

        } else {
            Utils.InternetDialog(activity);
        }
        binding.ivBack.setOnClickListener(this);
        courseListModels.clear();
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "0", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "20", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "20", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "0", "30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software", "Digital Design Thinking", "04 Jul,2022", "24", "30 DAYS"));

        CourseListAdapter adapter = new CourseListAdapter(activity, courseListModels);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.rvCourseList.setLayoutManager(linearLayoutManager3);
        binding.rvCourseList.setAdapter(adapter);

    }

    private void getCourseList() {
        apiService.getCourseList("").enqueue(new Callback<AllResponseModel>() {
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
        if (view == binding.ivBack) {
            onBackPressed();

        }
    }


}