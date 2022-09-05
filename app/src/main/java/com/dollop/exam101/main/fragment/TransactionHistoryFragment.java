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
    ArrayList<AllResponseModel> allResponseModels = new ArrayList<>();

    public TransactionHistoryFragment(ArrayList<AllResponseModel> list) {
        this.allResponseModels.addAll(list);
        Utils.E("allResponseModels::"+allResponseModels);
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