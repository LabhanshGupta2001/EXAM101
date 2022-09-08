package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityTestResultBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.main.adapter.TestResultAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.TestResultQuestion;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResultActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = TestResultActivity.this;
    ActivityTestResultBinding binding;
    List<TestResultQuestion> testResultQuestionList = new ArrayList<>();
    ApiService apiService;
    String testAttemptUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundel = getIntent().getExtras();
        testAttemptUuid = bundel.getString(Constants.Key.testAttemptUuid, "0");
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getTestResult() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.testAttemptUuid, testAttemptUuid);
        apiService.getTestResult(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;

                        if (response.body().testResultQuestions != null && !response.body().testResultQuestions.isEmpty()) {
                            AllResponseModel allResponseModel = response.body();
                            testResultQuestionList.clear();
                            testResultQuestionList.addAll(response.body().testResultQuestions);

                            binding.tvTotalQuestion.setText(allResponseModel.totalQuestionCnt.toString());
                            binding.tvTotalQ.setText(allResponseModel.totalQuestionCnt.toString());
                            binding.tvYourCorrectQ.setText(allResponseModel.correntQuestionCnt.toString());
                            int correctAns = Integer.parseInt(allResponseModel.correntQuestionCnt.toString());
                            int totalQuestoin = Integer.parseInt(allResponseModel.totalQuestionCnt.toString());
                            int wrongAns = totalQuestoin-correctAns;
                            binding.tvYourWrongQ.setText(String.valueOf(totalQuestoin-correctAns));

                            binding.rvListOfQuestion.setLayoutManager(new LinearLayoutManager(activity));
                            binding.rvListOfQuestion.setHasFixedSize(true);
                            binding.rvListOfQuestion.setAdapter(new TestResultAdapter(activity, testResultQuestionList));
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
                getTestResult();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        if (AppController.getInstance().isOnline()) {
            getTestResult();
        } else {
            showInternetDialog();
        }
        super.onResume();
    }
}