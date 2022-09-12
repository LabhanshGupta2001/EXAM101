package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentCategoryHomeBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.adapter.CategoryHomeAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.Studentexam;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryHomeFragment extends Fragment implements View.OnClickListener {
     FragmentCategoryHomeBinding binding;
    ApiService apiService;
    Activity activity;
    CategoryHomeAdapter categoryHomeAdapter;
    ArrayList<Studentexam> examList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryHomeBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {
            CategoriesHomeAllExamList();
        } else {
            InternetDialog();
        }
        binding.ivBack.setOnClickListener(this);
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), examList);
        binding.rvCategories.setAdapter(categoryHomeAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            binding.llToolbar.setVisibility(View.GONE);
            ((DashboardScreenActivity) activity).navController.popBackStack();
        }

    }

    private void CategoriesHomeAllExamList() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.getStudentExamListApi(Utils.GetSession().token, Constants.Key.android).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body() != null) {
                        if (response.body().studentexam.isEmpty()) {
                            Utils.E("Empty:::::" + response.body().studentexam.size());
                            binding.rvCategories.setVisibility(View.GONE);
                            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                        } else {
                            Utils.E("Empty:::::" + response.body().studentexam.size());
                            binding.rvCategories.setVisibility(View.VISIBLE);
                            binding.noResultFoundId.llParent.setVisibility(View.GONE);
                        }
                    }
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        examList.clear();
                        examList.addAll(response.body().studentexam);
                        categoryHomeAdapter.notifyDataSetChanged();
                    } else {
                        assert response.errorBody() != null;
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(getContext(), message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(getContext(), message.message);
                            Utils.UnAuthorizationToken(activity);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.rvCategories.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.rvCategories.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });
    }
}