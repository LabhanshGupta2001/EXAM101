package com.dollop.exam101.main.fragment;

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
import com.dollop.exam101.main.adapter.PurchaseListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseListFragment extends Fragment implements View.OnClickListener {
    FragmentPurchaseListBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    Activity activity;

    public PurchaseListFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        list.clear();
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {
            //getAffiliatePurchaseList();
            //getAffiliatePurchaseSummary();
        } else {
            InternetDialog();
        }

        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvPurchaseList.setHasFixedSize(true);
        binding.rvPurchaseList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPurchaseList.setAdapter(new PurchaseListAdapter(getContext(), list));
    }

    @Override
    public void onClick(View v) {

    }
/*

    private void getAffiliatePurchaseList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String,String> hm = new HashMap<>();
        hm.put(Constants.Key.startDate,"");
        hm.put(Constants.Key.endDate,"");
        apiService.getAffiliatePurchaseList(Utils.GetSession().token,hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                       */
/* affilliatDetailModel.clear();
                        assert response.body() != null;
                        AffilliatDetailModel affilliatDetailModel = response.body().affilliatDetailModel;
                        //Utils.E("affilliatDetailModel::::"+affilliatDetailModel);
                        if (affilliatDetailModel.reqStatus.equals(Constants.Key.Pending)) {
                            binding.llRequestPendingCode.setVisibility(View.VISIBLE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                        } else if (affilliatDetailModel.reqStatus.equals(Constants.Key.Success)) {
                            binding.llRequestPendingCode.setVisibility(View.GONE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                            binding.llBankDetailsAndAffiliation.setVisibility(View.VISIBLE);
                        } else {
                            binding.llRequestAffilation.setVisibility(View.VISIBLE);
                        }*//*


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

    private void getAffiliatePurchaseSummary() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getAffiliatePurchaseSummary(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                       */
/* affilliatDetailModel.clear();
                        assert response.body() != null;
                        AffilliatDetailModel affilliatDetailModel = response.body().affilliatDetailModel;
                        //Utils.E("affilliatDetailModel::::"+affilliatDetailModel);
                        if (affilliatDetailModel.reqStatus.equals(Constants.Key.Pending)) {
                            binding.llRequestPendingCode.setVisibility(View.VISIBLE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                        } else if (affilliatDetailModel.reqStatus.equals(Constants.Key.Success)) {
                            binding.llRequestPendingCode.setVisibility(View.GONE);
                            binding.llRequestAffilation.setVisibility(View.GONE);
                            binding.llBankDetailsAndAffiliation.setVisibility(View.VISIBLE);
                        } else {
                            binding.llRequestAffilation.setVisibility(View.VISIBLE);
                        }*//*


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
*/


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