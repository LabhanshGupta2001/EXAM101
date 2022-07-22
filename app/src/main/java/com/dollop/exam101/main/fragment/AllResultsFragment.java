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
import com.dollop.exam101.databinding.FragmentAllResultsBinding;
import com.dollop.exam101.main.adapter.MockHistoryAllResultsAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllResultsFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentAllResultsBinding binding;
    ApiService apiService;
    ArrayList<String>list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllResultsBinding.inflate(inflater, container, false);
        activity = requireActivity();
        apiService= RetrofitClient.getClient();
        init();

        return  binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");


        binding.rvFragmentAllResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFragmentAllResults.setHasFixedSize(true);
        binding.rvFragmentAllResults.setAdapter(new MockHistoryAllResultsAdapter(getContext(),list));
    }

    @Override
    public void onClick(View view) {

    }
    private void AllResults(){
        apiService.AllResults("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}