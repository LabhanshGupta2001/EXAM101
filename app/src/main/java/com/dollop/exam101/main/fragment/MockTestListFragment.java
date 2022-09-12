package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.databinding.FragmentMockTestListBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.activity.ProfileActivity;
import com.dollop.exam101.main.adapter.MockTestListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.StudentMockTest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestListFragment extends Fragment implements View.OnClickListener {
    FragmentMockTestListBinding binding;
    Activity activity;
    ApiService apiService;
    MockTestListAdapter mockTestListAdapter;
    List<StudentMockTest> studentMockTestList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMockTestListBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    private void init() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fadein);
        binding.llMain.startAnimation(animation);
        apiService = RetrofitClient.getClient();
      //  binding.ivBack.setOnClickListener(this);
        mockTestListAdapter = new MockTestListAdapter(activity, studentMockTestList);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(mockTestListAdapter);
    }

    private void getMockTestList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getStudentMockTestListApi(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        studentMockTestList.clear();
                        if (response.body().studentMockTestsList != null && !response.body().studentMockTestsList.isEmpty()) {
                            binding.dataNoFoundId.llParent.setVisibility(View.GONE);
                            binding.rvMockTestList.setVisibility(View.VISIBLE);
                            studentMockTestList.addAll(response.body().studentMockTestsList);
                            mockTestListAdapter.notifyDataSetChanged();
                        } else {
                            binding.dataNoFoundId.llParent.setVisibility(View.VISIBLE);
                            binding.rvMockTestList.setVisibility(View.GONE);
                        }

                    } else {
                        assert response.errorBody() != null;
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(activity, message.message);
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

    @Override
    public void onClick(View view) {
/*
        if (view == binding.ivBack) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fadeout);
            binding.llMain.startAnimation(animation);
            binding.llToolbar.setVisibility(View.GONE);
            new CountDownTimer(800, 800) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    binding.llToolbar.setVisibility(View.GONE);
                    binding.rvMockTestList.setVisibility(View.GONE);
                    ((DashboardScreenActivity) activity).navController.popBackStack();
                }

            }.start();
          */
/*  binding.llToolbar.setVisibility(View.GONE);
            ((DashboardScreenActivity) activity).navController.popBackStack();*//*

        }
*/
    }

    @Override
    public void onResume() {
        if (AppController.getInstance().isOnline()) {
            getMockTestList();
            Utils.E("onResume ::: Called");}
        else{showInternetDialog();}
        super.onResume();
    }
    private void showInternetDialog() {
        Dialog dialog = new Dialog(activity);
        AlertdialogBinding alertDialogBinding = AlertdialogBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        alertDialogBinding.tvPermittManually.setText(R.string.retry);
        alertDialogBinding.tvDesc.setText(R.string.please_check_your_connection);
        alertDialogBinding.tvPermittManually.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                getMockTestList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}