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
import com.dollop.exam101.databinding.FragmentAuthorBinding;
import com.dollop.exam101.main.adapter.AuthorHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorFragment extends Fragment implements View.OnClickListener{
    Activity activity;
    FragmentAuthorBinding binding;
    ApiService apiService;
    ArrayList<String> list = new ArrayList<>();
    public AuthorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthorBinding.inflate(inflater,container,false);
        activity = requireActivity();
        apiService= RetrofitClient.getClient();
        init();
        return binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvAuthorSearch.setHasFixedSize(true);
        binding.rvAuthorSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAuthorSearch.setAdapter(new AuthorHistoryAdapter(getContext(), list));
    }

    @Override
    public void onClick(View view) {
    }
    private void AuthorList(){
        apiService.AuthorList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}