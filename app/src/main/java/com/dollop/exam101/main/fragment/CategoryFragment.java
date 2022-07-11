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
import com.dollop.exam101.databinding.FragmentCategoryBinding;
import com.dollop.exam101.main.adapter.AuthorHistoryAdapter;
import com.dollop.exam101.main.adapter.CategoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment implements View.OnClickListener{
    FragmentCategoryBinding binding;
    ApiService apiService;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();
    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
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
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategory.setAdapter(new CategoryAdapter(getContext(),list));
    }

    @Override
    public void onClick(View v) {

    }
    private void CategoriesAllBlogsList(){
        apiService.CategoriesAllBlogsList("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}