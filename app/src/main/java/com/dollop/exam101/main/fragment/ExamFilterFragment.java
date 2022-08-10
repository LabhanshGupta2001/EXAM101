package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.main.adapter.CategoriesFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamFilterFragment extends Fragment implements View.OnClickListener {
    FragmentExamFilterBinding binding;
    ArrayList<CourseModel> examArrayList = new ArrayList<>();
    Activity activity;
    ApiService apiService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamFilterBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {
            getFilter();
        } else {
           // Utils.InternetDialog(activity);
            InternetDialog();

        }

    }

    @Override
    public void onClick(View v) {

    }

    void getFilter() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.Examlist(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().examListModels.isEmpty()) {
                        binding.rvExam.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvExam.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    examArrayList.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        examArrayList.clear();
                        examArrayList.addAll(response.body().examListModels);
                        binding.rvExam.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        //  binding.rvExam.setAdapter(new CategoriesFragmentAdapter(examArrayList, getContext(), Constants.Key.ExamFilterFragment,onItemClicked));
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
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
                init();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}