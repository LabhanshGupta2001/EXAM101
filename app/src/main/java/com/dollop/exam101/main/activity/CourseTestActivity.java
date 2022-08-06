package com.dollop.exam101.main.activity;

import static com.dollop.exam101.Basics.UtilityTools.AppController.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCourseTestBinding;
import com.dollop.exam101.main.adapter.CourseTestQuestionAdapter;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.QuestionListModel;
import com.dollop.exam101.main.model.QuestionModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTestActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CourseTestActivity.this;
    ActivityCourseTestBinding binding;
    ApiService apiService;
    String orderExamUuids = "", topicUuids = "";
    ArrayList<QuestionModel> questionListModelArrayList = new ArrayList<>();
    CourseTestQuestionAdapter courseTestQuestionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        apiService = RetrofitClient.getClient();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            orderExamUuids = bundle.getString(Constants.Key.orderExamUuid);
            topicUuids =  bundle.getString(Constants.Key.topicUuid);
        }
        Utils.E("orderExamUuid::"+orderExamUuids+"topicUuids::"+topicUuids);

        if (AppController.getInstance().isOnline()) {
            getPracticeQuestion();
        } else {
            Utils.InternetDialog(activity);
        }
        binding.ivBack.setOnClickListener(this);
        binding.ivAbout.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

      //  getQuestin();

    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.ivAbout) {
            Toast.makeText(activity, "About This App", Toast.LENGTH_SHORT).show();
        } else if (view == binding.btnSubmit) {
            submitPracticeTest();
        }
    }

    private void submitPracticeTest() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoiZ2VldEBnbWFpbC5jb20iLCJ1c2VyX2lkIjoiYWQ5YzhhNzQtMGNmMi0xMWVkLTk3NTQtMDAwYzI5MTE1MWViIiwicm9sZSI6IlN0dWRlbnQiLCJBUElfVElNRSI6MTY1OTY3MjgwMX0.BBr3FZ9vob8SC5Q5cj20h-vRHFiX4dDeej2eZoF_grk";
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.orderExamUuid,orderExamUuids);
        hm.put(Constants.Key.topicUuid, topicUuids);
        hm.put(Constants.Key.questionIds, "87||97");
        hm.put(Constants.Key.options, "options_1||options_2");
        apiService.practiceTestSubmit(token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity,"Test Submitted Successfully");
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.Key.testAttemptUuid,response.body().testAttemptId);
                        Utils.E("testAttemptUuid::"+response.body().testAttemptId);
                        Utils.I(activity, TestResultActivity.class, bundle);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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

    private void getPracticeQuestion() {
            Dialog progressDialog = Utils.initProgressDialog(activity);
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoiZ2VldEBnbWFpbC5jb20iLCJ1c2VyX2lkIjoiYWQ5YzhhNzQtMGNmMi0xMWVkLTk3NTQtMDAwYzI5MTE1MWViIiwicm9sZSI6IlN0dWRlbnQiLCJBUElfVElNRSI6MTY1OTY3MjgwMX0.BBr3FZ9vob8SC5Q5cj20h-vRHFiX4dDeej2eZoF_grk";
            HashMap<String, String> hm = new HashMap<>();
            hm.put(Constants.Key.orderExamUuid,orderExamUuids);
            hm.put(Constants.Key.topicUuid, topicUuids);
            apiService.getMyQuestionList(token, hm).enqueue(new Callback<AllResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                    progressDialog.dismiss();
                    try {
                        if (response.code() == StatusCodeConstant.OK) {
                            assert response.body() != null;
                            questionListModelArrayList.clear();
                            //questionListModelArrayList.addAll(Collections.singleton(response.body().questionListModel));
                            questionListModelArrayList.addAll(response.body().questionListModel.questions);

                            courseTestQuestionAdapter = new CourseTestQuestionAdapter(activity, questionListModelArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            binding.rvQuestion.setLayoutManager(linearLayoutManager);
                            binding.rvQuestion.setAdapter(courseTestQuestionAdapter);

                        } else {
                            assert response.errorBody() != null;
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                                Utils.T(activity, message.message);
                            } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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