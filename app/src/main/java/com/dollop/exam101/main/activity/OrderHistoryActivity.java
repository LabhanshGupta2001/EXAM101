package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityOrderHistoryBinding;
import com.dollop.exam101.databinding.ItemOrderHistoryBinding;
import com.dollop.exam101.main.adapter.OrderHistoryAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.OrderHistoryModel;
import com.dollop.exam101.main.model.StudentOrder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = OrderHistoryActivity.this;
    ActivityOrderHistoryBinding binding;
    List<StudentOrder> orderHistoryModelArrayList = new ArrayList<>();
    OrderHistoryAdapter orderHistoryAdapter;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.rvOderHistory.setLayoutManager(new LinearLayoutManager(activity));
        orderHistoryAdapter = new OrderHistoryAdapter(activity, orderHistoryModelArrayList);
        binding.rvOderHistory.setAdapter(orderHistoryAdapter);
        getOrderHisrory();

    }

    @Override
    public void onClick(View view) {

        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    void getOrderHisrory() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getStudentOrderListApi(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        orderHistoryModelArrayList.clear();
                        orderHistoryModelArrayList.addAll(response.body().studentOrders);
                        if (orderHistoryModelArrayList == null || orderHistoryModelArrayList.isEmpty()){
                            Utils.E("IF Is True::");
                            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                            binding.rvOderHistory.setVisibility(View.GONE);
                            binding.nsScrollView.setVisibility(View.GONE);
                        }else {
                            Utils.E("Else:::");
                            orderHistoryAdapter.notifyDataSetChanged();
                            binding.noResultFoundId.llParent.setVisibility(View.GONE);
                            binding.rvOderHistory.setVisibility(View.VISIBLE);
                            binding.nsScrollView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.UnAuthorizationToken(activity);
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

    public void downloadInvoice() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.downloadInvoice(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.UnAuthorizationToken(activity);
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