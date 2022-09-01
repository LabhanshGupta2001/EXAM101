package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentPurchaseListBinding;
import com.dollop.exam101.main.activity.AffiliatePurchaseListActivity;
import com.dollop.exam101.main.adapter.PurchaseListAdapter;
import com.dollop.exam101.main.model.AffiliatePurchaseModel;
import com.dollop.exam101.main.model.AffilliatPurchaseListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseListFragment extends Fragment implements View.OnClickListener {
    String startDate = Constants.Key.blank;
    String endDate = Constants.Key.blank;
    FragmentPurchaseListBinding binding;
    ApiService apiService;
    Activity activity;
    PurchaseListAdapter purchaseListAdapter;
    ArrayList<AffiliatePurchaseModel> affiliatePurchaseModelList = new ArrayList<>();


    public PurchaseListFragment(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        Utils.E("startDate" + startDate + " :endDate::" + endDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        purchaseListAdapter = new PurchaseListAdapter(activity, affiliatePurchaseModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rvPurchaseList.setLayoutManager(linearLayoutManager);
        binding.rvPurchaseList.setAdapter(purchaseListAdapter);
        if (AppController.getInstance().isOnline()) {
            getAffiliatePurchaseList(startDate, endDate, activity);
        } else {
            InternetDialog();
        }

    }

    @Override
    public void onClick(View v) {

    }


    public void getAffiliatePurchaseList(String startDate, String endDate, Activity activity) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.startDate, startDate);
        hm.put(Constants.Key.endDate, endDate);
        apiService = RetrofitClient.getClient();
        apiService.getAffiliatePurchaseList(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    affiliatePurchaseModelList.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        if (response.body().affilliatPurchaseListModel.affiliatePurchaseModelList.isEmpty() ||
                                response.body().affilliatPurchaseListModel.affiliatePurchaseModelList.equals(Constants.Key.blank) ||
                                response.body().affilliatPurchaseListModel.equals(Constants.Key.blank)) {
                            binding.rvPurchaseList.setVisibility(View.GONE);
                            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);

                        } else {
                            affiliatePurchaseModelList.addAll(response.body().affilliatPurchaseListModel.affiliatePurchaseModelList);
                            Utils.E("affilliatPurchaseListModel::::" + affiliatePurchaseModelList);
                            purchaseListAdapter.notifyDataSetChanged();
                            binding.rvPurchaseList.setVisibility(View.VISIBLE);
                            binding.noResultFoundId.llParent.setVisibility(View.GONE);
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.rvPurchaseList.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.rvPurchaseList.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });
    }
}