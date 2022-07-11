package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.databinding.ActivityMyWishlistBinding;
import com.dollop.exam101.main.adapter.MyWishListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWishlistActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = MyWishlistActivity.this;
    ActivityMyWishlistBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        getWishlist();
        apiService = RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    void getWishlist() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        MyWishListAdapter myWishListAdapter = new MyWishListAdapter(activity, list);
        binding.rvWishList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvWishList.setAdapter(myWishListAdapter);
    }

    void getWishList() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getWishList(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}