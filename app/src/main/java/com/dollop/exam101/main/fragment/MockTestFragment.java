package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;

import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MockTestFragment extends Fragment  implements View.OnClickListener{
    Activity activity;
    FragmentMockTestBinding binding;
    ApiService apiService;
    ArrayList<String> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMockTestBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();

    }

    private void init() {

        apiService= RetrofitClient.getClient();
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvMockTestList.setHasFixedSize(true);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMockTestList.setAdapter(new PakageDetailMockTestFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }
    private void getPackageDetailsMockTestList(){
        apiService.getPackageDetailsMockTestList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}