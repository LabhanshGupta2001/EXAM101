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
import com.dollop.exam101.databinding.ActivityRaiseComplaintBinding;
import com.dollop.exam101.main.adapter.RaiseComplaintAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseComplaintActivity extends BaseActivity implements View.OnClickListener {
    ApiService apiService;
    Activity activity = RaiseComplaintActivity.this;
    ActivityRaiseComplaintBinding binding;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
        binding.mcvAdd.setOnClickListener(this);


        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvRaiseComplaint.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvRaiseComplaint.setAdapter(new RaiseComplaintAdapter(activity, list));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.mcvAdd) {
            Utils.I(activity, RaiseComplaintFormActivity.class, null);
        }
    }

    void getComplaintList() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getComplaintList(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

}