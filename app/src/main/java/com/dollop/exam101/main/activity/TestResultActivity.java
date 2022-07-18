package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityTestResultBinding;
import com.dollop.exam101.main.adapter.TestResultAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResultActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = TestResultActivity.this;
    ActivityTestResultBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvListOfQuestion.setAdapter(new TestResultAdapter(activity, list));
        binding.rvListOfQuestion.setHasFixedSize(true);
        binding.rvListOfQuestion.setLayoutManager(new LinearLayoutManager(activity));

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            // Utils.T(activity,"back prass");
            Utils.I(activity, MockTestListActivity.class, null);
            activity.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Utils.I_clear(activity, MockTestListActivity.class, null);
        finish();
    }

    void getResult() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getResult(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });

    }

}