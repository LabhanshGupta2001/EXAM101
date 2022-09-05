package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.FragmentAllResultsBinding;
import com.dollop.exam101.main.adapter.MockHistoryAllResultsAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.MockTestHistory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllResultsFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentAllResultsBinding binding;
    ApiService apiService;
    List<MockTestHistory> mockTestHistorylist = new ArrayList<>();
    MockHistoryAllResultsAdapter mockHistoryAllResultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllResultsBinding.inflate(inflater, container, false);
        activity = requireActivity();
        apiService = RetrofitClient.getClient();
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.rvFragmentAllResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFragmentAllResults.setHasFixedSize(true);
        mockHistoryAllResultsAdapter = new MockHistoryAllResultsAdapter(requireContext(), mockTestHistorylist);
        binding.rvFragmentAllResults.setAdapter(mockHistoryAllResultsAdapter);
    }

    @Override
    public void onClick(View view) {

    }
    //Guru
    private void getMockTestHistory() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getMockTestHistoryListApi(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        mockTestHistorylist.clear();
                        if (response.body().mockTestHistory != null && !response.body().mockTestHistory.isEmpty()) {
                            binding.dataNoFoundId.llParent.setVisibility(View.GONE);
                            binding.rvFragmentAllResults.setVisibility(View.VISIBLE);
                            mockTestHistorylist.addAll(response.body().mockTestHistory);
                            mockHistoryAllResultsAdapter.notifyDataSetChanged();
                        } else {
                            binding.dataNoFoundId.llParent.setVisibility(View.VISIBLE);
                            binding.rvFragmentAllResults.setVisibility(View.GONE);
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
                getMockTestHistory();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (AppController.getInstance().isOnline()) {
            getMockTestHistory();}
        else{showInternetDialog();}
    }
}