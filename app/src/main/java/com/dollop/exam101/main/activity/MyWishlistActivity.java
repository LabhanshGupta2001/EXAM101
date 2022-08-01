package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityMyWishlistBinding;
import com.dollop.exam101.main.adapter.MyWishListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.WishListModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyWishlistActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = MyWishlistActivity.this;
    ActivityMyWishlistBinding binding;
    ApiService apiService;
    ArrayList<WishListModel> wishList = new ArrayList<>();
    MyWishListAdapter myWishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        apiService = RetrofitClient.getClient();

        myWishListAdapter = new MyWishListAdapter(activity, wishList);
        binding.rvWishList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvWishList.setAdapter(myWishListAdapter);

        getWishlist();

        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    /* void getWishlist() {
        list.clear();
         list.add("1");
         list.add("1");
         list.add("1");
         list.add("1");
         list.add("1");
         list.add("1");
         MyWishListAdapter myWishListAdapter = new MyWishListAdapter(activity,list);
         binding.rvWishList.setLayoutManager(new LinearLayoutManager(activity));
         binding.rvWishList.setAdapter(myWishListAdapter);

     }
 */
    private void getWishlist() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getWishList(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        wishList.clear();
                        wishList.addAll(response.body().wishList);
                        myWishListAdapter.notifyDataSetChanged();
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(activity, message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }
}