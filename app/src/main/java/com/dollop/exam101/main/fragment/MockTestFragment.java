package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dollop.exam101.Basics.Database.UserData;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;

import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.MockTestModel;
import com.dollop.exam101.main.model.StudentMockTest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MockTestFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentMockTestBinding binding;
    ApiService apiService;
    List<MockTestModel> mockTestModels = new ArrayList<>();


    public MockTestFragment(List<MockTestModel> mockTestModels) {
        this.mockTestModels = mockTestModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMockTestBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        Utils.E("mockTestModels::::::::" + mockTestModels);


        binding.rvMockTestList.setHasFixedSize(true);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(new PakageDetailMockTestFragmentAdapter(activity, mockTestModels));

    }

    @Override
    public void onClick(View v) {

    }
}
