package com.dollop.exam101.main.activity;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;


import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityMyWishlistBinding;


public class MyWishlistActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = MyWishlistActivity.this;
    ActivityMyWishlistBinding binding;
    ApiService apiService;
    String Token;


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
        Token = Utils.GetSession().token;
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    void getWishlist() {
     /*   list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        MyWishListAdapter myWishListAdapter = new MyWishListAdapter(activity, list);
        binding.rvWishList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvWishList.setAdapter(myWishListAdapter);*/
    }

}