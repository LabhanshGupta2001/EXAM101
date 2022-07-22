package com.dollop.exam101.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.databinding.FragmentTransactionHistoryBinding;
import com.dollop.exam101.main.adapter.TransactionHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;

    public TransactionHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

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

    void getTransactionHistory() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getTransactionHistory(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}