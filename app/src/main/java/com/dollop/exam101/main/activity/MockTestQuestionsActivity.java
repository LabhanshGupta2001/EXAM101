package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityMockTestQuestionsBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomSheetQuitExamBinding;
import com.dollop.exam101.main.adapter.MockTestQuestionAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.MockTestQuestion;
import com.dollop.exam101.main.model.Question;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestQuestionsActivity extends BaseActivity implements View.OnClickListener {
    public static int attemptedCount = 0;
    Activity activity = MockTestQuestionsActivity.this;
    ActivityMockTestQuestionsBinding binding;
    BottomSheetDialog quitTestDialog;
    BottomSheetQuitExamBinding bottomSheetQuitExamBinding;
    List<Question> questionList = new ArrayList<>();
    MockTestQuestionAdapter mockTestQuestionAdapter;
    AlertdialogBinding alertDialogBinding;
    Dialog dialog;
    int visitedQuestion = 0;
    int nowPagePosition = 0;
    int totalPages = 0;
    int layoutWidth = 1;
    String questionIds = "";
    String selectedOption = "";
    ApiService apiService;
    private String orderMockTestId, orderMockTestUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMockTestQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundel = getIntent().getExtras();
        orderMockTestId = bundel.getString(Constants.Key.orderMockTestId, "0");
        orderMockTestUuid = bundel.getString(Constants.Key.orderMockTestUuid, "0");

        init();

        mockTestQuestionAdapter = new MockTestQuestionAdapter(questionList, getApplicationContext());
        binding.vpQuestion.setAdapter(mockTestQuestionAdapter);

        layoutWidth = binding.vpQuestion.getWidth();
        binding.tvAllPages.setText(String.valueOf(layoutWidth));
    }

    //Guru
    private void getMockTestQuestionList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.orderMockTestId, orderMockTestId);
        hm.put(Constants.Key.orderMockTestUuid, orderMockTestUuid);
        hm.put(Constants.Key.device_type, Constants.Key.android);

        apiService.getMockTestQuestionList(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        MockTestQuestion mockTestQuestion = response.body().mockTestQuestion;
                        if (response.body().mockTestQuestion != null && !response.body().mockTestQuestion.questions.isEmpty()) {
                            questionList.clear();
                            questionList.addAll(response.body().mockTestQuestion.questions);
                            mockTestQuestionAdapter.notifyDataSetChanged();

                            binding.tvAllPages.setText(mockTestQuestion.questionCnt.toString());
                            binding.tvCountMin.setText(mockTestQuestion.duration + " Min");
                            binding.tvTotalQuestion.setText(mockTestQuestion.questionCnt.toString());
                            totalPages = Integer.parseInt(mockTestQuestion.questionCnt + "");
                            if (!mockTestQuestion.duration.equalsIgnoreCase("0"))
                                setTimer(mockTestQuestion.duration);
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

    private void setTimer(String duration2) {
        int duration = Integer.parseInt(duration2);
        long durationSec = (duration * 60L);
        final long durationInMilliSec = TimeUnit.SECONDS.toMillis(durationSec);
        new CountDownTimer(durationInMilliSec, 1000) {
            public void onTick(long millisUntilFinished) {
                @SuppressLint("DefaultLocale") String timeLeft = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                binding.tvCountRun.setText(timeLeft);
                bottomSheetQuitExamBinding.tvRemainingTime.setText(timeLeft);

            }

            public void onFinish() {
                //show Timer On finish
                showFinishExamDialog();
                new CountDownTimer(6000, 1000) {
                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onTick(long millisUntilFinished) {
                        int sec = Math.toIntExact(millisUntilFinished / 1000);
                        //String testSubmit= R.string.test_submit_in);
                        alertDialogBinding.tvDesc.setText(getString(R.string.test_will_be_automatically_submitted_within) + " " + sec + " sec");
                    }

                    public void onFinish() {
                        dialog.dismiss();
                        submitMockTest();
                    }
                }.start();
            }
        }.start();
    }

    private void init() {
        setBottomSheet();
        apiService = RetrofitClient.getClient();
        binding.tvQuitExam.setOnClickListener(this);
        binding.tvButtonSubmit.setOnClickListener(this);
        binding.mcvMoveBack.setOnClickListener(this);
        binding.mcvMoveNext.setOnClickListener(this);
        bottomSheetQuitExamBinding.mcvBtnQuitTest.setOnClickListener(this);
        bottomSheetQuitExamBinding.mcvBtnCancel.setOnClickListener(this);

        binding.vpQuestion.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nowPagePosition = position + 1;
                binding.tvCurrentPage.setText(nowPagePosition + Constants.Key.blank);
                if (position >= visitedQuestion)
                    visitedQuestion++;
                int notVisitedQuestion2 = totalPages - visitedQuestion;
                binding.tvAttempted.setText(visitedQuestion + "");
                binding.tvNotYetVisited.setText(notVisitedQuestion2 + "");
                if (nowPagePosition == totalPages)
                    binding.mcvMoveNext.setVisibility(View.GONE);
                else binding.mcvMoveNext.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvQuitExam) {
            quitTestDialog.show();
        } else if (view == binding.tvButtonSubmit) {
            submitMockTest();
        } else if (view == binding.mcvMoveNext) {
            binding.vpQuestion.setCurrentItem(binding.vpQuestion.getCurrentItem() + 1, true);
        } else if (view == binding.mcvMoveBack) {
            binding.vpQuestion.setCurrentItem(binding.vpQuestion.getCurrentItem() - 1, true);
        } else if (view == bottomSheetQuitExamBinding.mcvBtnCancel) {
            quitTestDialog.cancel();
        } else if (view == bottomSheetQuitExamBinding.mcvBtnQuitTest) {
            quitTestDialog.cancel();
            finish();
        }
    }

    void setBottomSheet() {
        quitTestDialog = new BottomSheetDialog(activity);
        bottomSheetQuitExamBinding = BottomSheetQuitExamBinding.inflate(getLayoutInflater());
        quitTestDialog.setContentView(bottomSheetQuitExamBinding.getRoot());
    }

    void submitMockTest() {
        if (getSelectedOption())
        {
            Dialog progressDialog = Utils.initProgressDialog(activity);
            HashMap<String, String> hm = new HashMap<>();
            hm.put(Constants.Key.orderMockTestId, orderMockTestId);
            hm.put(Constants.Key.questionIds, questionIds);
            hm.put(Constants.Key.options, selectedOption);

            apiService.submitMockTest(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
                @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                @Override
                public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                    progressDialog.dismiss();
                    try {
                        if (response.code() == StatusCodeConstant.OK) {
                            assert response.body() != null;
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.Key.testAttemptUuid,response.body().testAttemptUuid);
                            Utils.I(activity, TestResultActivity.class, bundle);
                            finish();
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
    }

    // get selected question and answer
    private boolean getSelectedOption() {
        for (int i = 0; i < MockTestQuestionAdapter.questionList.size(); i++) {
            Question questin = MockTestQuestionAdapter.questionList.get(i);
            if (i == MockTestQuestionAdapter.questionList.size() - 1) {
                questionIds += "" + questin.questionId;
                if (questin.SelectedAnswer == -1)
                    selectedOption += Constants.Key.not_attempted;
                else selectedOption += Constants.Key.options_ + "" + questin.SelectedAnswer;
                return true;
            } else {
                questionIds += questin.questionId + "||";
                if (questin.SelectedAnswer == -1)
                    selectedOption += Constants.Key.not_attempted + "||";
                else selectedOption += Constants.Key.options_ + "" + questin.SelectedAnswer + "||";
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (quitTestDialog != null && !quitTestDialog.isShowing()) {
            quitTestDialog.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getInstance().isOnline()) {
            getMockTestQuestionList();
        } else {
            showInternetDialog();
        }
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
                getMockTestQuestionList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showFinishExamDialog() {
        dialog = new Dialog(activity);
        alertDialogBinding = AlertdialogBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        alertDialogBinding.tvPermittManually.setText(R.string.submit);
        alertDialogBinding.tvDesc.setText(R.string.test_submit_in);
        alertDialogBinding.tvDesc.setTextColor(ContextCompat.getColor(activity, R.color.green));
        alertDialogBinding.tvPermittManually.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                dialog.dismiss();
                submitMockTest();
            }
        });
        dialog.show();
    }
}