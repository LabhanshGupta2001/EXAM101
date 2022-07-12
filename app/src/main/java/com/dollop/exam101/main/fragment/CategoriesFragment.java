package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCategoriesBinding;
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.main.adapter.CategoriesFragmentAdapter;
import com.dollop.exam101.main.adapter.ExamFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment implements View.OnClickListener{
    ApiService apiService;
    Activity activity;
    Fragment fragment = CategoriesFragment.this;
    FragmentCategoriesBinding binding;
    ArrayList<String> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);

        activity = requireActivity();
        apiService= RetrofitClient.getClient(); init();
        return binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvCategoriesItem.setHasFixedSize(true);
        binding.rvCategoriesItem.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategoriesItem.setAdapter(new CategoriesFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }
    private void CategoriesList(){
        apiService.CategoriesList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}