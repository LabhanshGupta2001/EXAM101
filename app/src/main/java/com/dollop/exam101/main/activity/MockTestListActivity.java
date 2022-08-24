package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityMockTestListBinding;
import com.dollop.exam101.main.adapter.MockTestListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestListActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = MockTestListActivity.this;
    ActivityMockTestListBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMockTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        getTestList();
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == binding.ivBack) {
            activity.finish();
            Utils.I(activity, ProfileActivity.class, null);
        }
    }

    void getTestList() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(new MockTestListAdapter(activity, list));
    }

    void getMocktestList() {
        HashMap<String, String> hashMap = new HashMap<>();
        apiService.getorderHistory(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

}