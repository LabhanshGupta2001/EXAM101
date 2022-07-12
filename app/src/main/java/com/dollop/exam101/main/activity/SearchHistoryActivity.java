package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.databinding.ActivitySearchHistoryBinding;
import com.dollop.exam101.main.adapter.FlexBoxAdapter;
import com.dollop.exam101.main.adapter.ResentSearchHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = SearchHistoryActivity.this;
    ActivitySearchHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    FlexBoxAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvSearchHistory.setAdapter(new ResentSearchHistoryAdapter(activity, list));
        binding.rvSearchHistory.setHasFixedSize(true);
        binding.rvSearchHistory.setLayoutManager(new LinearLayoutManager(activity));


        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        binding.rvFlexBox.setHasFixedSize(true);
        binding.rvFlexBox.setLayoutManager(layoutManager);
        binding.rvFlexBox.setAdapter(new FlexBoxAdapter(activity, arrayList));

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        }
    }

    void getSearchHistory() {
        HashMap<String, String> hm = new HashMap();
        apiService.getSearchHistory(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    void getFlexBox() {
        HashMap<String, String> hashMap = new HashMap();
        apiService.getFlexBox(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}