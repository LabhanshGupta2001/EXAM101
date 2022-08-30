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
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentTransactionHistoryBinding;
import com.dollop.exam101.main.adapter.TransactionHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    Activity activity;

    public TransactionHistoryFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {
            //getAffiliatePurchaseSummary();
        } else {
            InternetDialog();
        }


        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");


        binding.rvTransactionHistory.setHasFixedSize(true);
        binding.rvTransactionHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTransactionHistory.setAdapter(new TransactionHistoryAdapter(getContext(), list));
    }

    @Override
    public void onClick(View v) {

    }

    private void getAffiliatePurchaseSummary() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getAffiliatePurchaseSummary(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {

                  /*affilliatDetailModel.clear();
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
                        }
*/

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
        binding.rvTransactionHistory.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.rvTransactionHistory.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });
    }
}