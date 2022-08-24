package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.databinding.FragmentMockTestListBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.activity.ProfileActivity;
import com.dollop.exam101.main.adapter.MockTestListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestListFragment extends Fragment implements View.OnClickListener {
    FragmentMockTestListBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMockTestListBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        getTestList();
        binding.ivBack.setOnClickListener(this);
    }
    void getTestList() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(new MockTestListAdapter(activity, list));
    }
    void getMockTestList() {
        HashMap<String, String> hashMap = new HashMap<>();
        apiService.getorderHistory(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            ((DashboardScreenActivity) activity).navController.popBackStack();
        }
    }
}