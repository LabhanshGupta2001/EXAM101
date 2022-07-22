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
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.main.adapter.ExamFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamFilterFragment extends Fragment implements View.OnClickListener {
    Fragment fragment = ExamFilterFragment.this;
    FragmentExamFilterBinding binding;
    ArrayList<String> list = new ArrayList<>();

    ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamFilterBinding.inflate(inflater, container, false);
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

        binding.rvExam.setHasFixedSize(true);
        binding.rvExam.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExam.setAdapter(new ExamFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }

    void getFilter() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getFilter(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

}