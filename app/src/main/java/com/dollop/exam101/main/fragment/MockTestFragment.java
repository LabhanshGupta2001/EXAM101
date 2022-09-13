package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;
import com.dollop.exam101.main.model.MockTestModel;

import java.util.ArrayList;
import java.util.List;


public class MockTestFragment extends Fragment implements View.OnClickListener {
    public List<MockTestModel> mockTestModels = new ArrayList<>();
    Activity activity;
    FragmentMockTestBinding binding;
    ApiService apiService;
    PakageDetailMockTestFragmentAdapter pakageDetailMockTestFragmentAdapter;
    private int PageHeight = 0;


    public MockTestFragment() {
        //     this.mockTestModels = mockTestModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMockTestBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();

    }

    private void init() {
        PageHeight = 0;
        apiService = RetrofitClient.getClient();
        Utils.E("mockTestModels::::::::" + mockTestModels);
        pakageDetailMockTestFragmentAdapter = new PakageDetailMockTestFragmentAdapter(activity, mockTestModels);

        binding.rvMockTestList.setHasFixedSize(true);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(pakageDetailMockTestFragmentAdapter);

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (PageHeight == 0)
                    PageHeight = binding.rvMockTestList.getHeight();
                binding.rvMockTestList.setMinimumHeight(PageHeight);
                Utils.E("CountDownTimer:rvMockTestList:" + PageHeight);

            }
        }.start();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void UpdateData() {
        if (pakageDetailMockTestFragmentAdapter != null) {
            pakageDetailMockTestFragmentAdapter.notifyDataSetChanged();
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    PageHeight = 0;
                    PageHeight = binding.rvMockTestList.getHeight();
                    binding.rvMockTestList.setMinimumHeight(PageHeight);
                    Utils.E("PageHeight :" + this + " " + PageHeight);
                }
            }.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PageHeight != 0) {
            binding.rvMockTestList.setMinimumHeight(PageHeight);
            Utils.E("CountDownTimer:rvMockTestList:" + PageHeight);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
