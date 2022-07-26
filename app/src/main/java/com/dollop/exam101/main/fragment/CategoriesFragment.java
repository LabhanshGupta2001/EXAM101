package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentCategoriesBinding;
import com.dollop.exam101.main.adapter.CategoriesFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment implements View.OnClickListener {
    public CategoriesFragmentAdapter categoriesFragmentAdapter;
    ApiService apiService;
    Activity activity;
    String Token;
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    OnItemClicked onItemClicked;
    private FragmentCategoriesBinding binding;
    private String From;

    public CategoriesFragment(String key, OnItemClicked onItemClicked) {
        From = key;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);

        activity = requireActivity();
        apiService = RetrofitClient.getClient();
        init();

        return binding.getRoot();
    }

    private void init() {
        Token = Utils.GetSession().token;
        getExamList();
    }

    @Override
    public void onClick(View v) {

    }

    private void getExamList() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.Examlist(Token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        courseModelArrayList.clear();
                        courseModelArrayList.addAll(response.body().examListModels);
                        binding.rvCategoriesItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        categoriesFragmentAdapter = new CategoriesFragmentAdapter(courseModelArrayList, requireActivity(), From);
                        binding.rvCategoriesItem.setAdapter(categoriesFragmentAdapter);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(getContext(), message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(getContext(), message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

}