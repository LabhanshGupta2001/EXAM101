package com.dollop.exam101.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.databinding.FragmentPurchaseListBinding;
import com.dollop.exam101.main.adapter.PurchaseListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseListFragment extends Fragment implements View.OnClickListener {
    FragmentPurchaseListBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;

    public PurchaseListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        list.clear();
        apiService = RetrofitClient.getClient();

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

    void getPurchaseList() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getPurchaseList(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}