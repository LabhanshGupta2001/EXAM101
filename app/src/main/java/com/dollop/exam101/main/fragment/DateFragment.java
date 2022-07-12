package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentDateBinding;

public class DateFragment extends Fragment implements View.OnClickListener{
    ApiService apiService;
    Activity activity;
    FragmentDateBinding binding;

    public DateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDateBinding.inflate(inflater, container, false);
        activity = requireActivity();
        apiService= RetrofitClient.getClient();
        init();
        return binding.getRoot();
    }

    private void init() {
    }

    @Override
    public void onClick(View view) {

    }

}