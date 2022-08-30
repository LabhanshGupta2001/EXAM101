package com.dollop.exam101.main.fragment;

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
import com.dollop.exam101.main.model.Exam;
import com.dollop.exam101.main.model.StudentExamList;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryHomeFragment extends Fragment implements View.OnClickListener {
    ApiService apiService;
    Activity activity;
    FragmentCategoryHomeBinding binding;
    CategoryHomeAdapter categoryHomeAdapter;
    ArrayList<Exam> examList = new ArrayList<com.dollop.exam101.main.model.Exam>();

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
        apiService.getStudentExamListApi(Utils.GetSession().token).enqueue(new Callback<StudentExamList>() {
            @Override
            public void onResponse(@NonNull Call<StudentExamList> call, @NonNull Response<StudentExamList> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().exams.equals(Constants.Key.blank) || response.body().exams.isEmpty()) {
                        binding.rvCategories.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvCategories.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        examList.clear();
                        examList.addAll(response.body().exams);
                        categoryHomeAdapter.notifyDataSetChanged();
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
            public void onFailure(@NonNull Call<StudentExamList> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.llParent.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.llParent.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });
    }
}