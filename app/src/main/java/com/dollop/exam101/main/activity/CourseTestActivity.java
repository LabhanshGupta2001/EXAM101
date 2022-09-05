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
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.QuestionModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTestActivity extends BaseActivity implements View.OnClickListener {

    public ActivityCourseTestBinding binding;
    Activity activity = CourseTestActivity.this;
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
        if (bundle != null) {
            orderExamUuids = bundle.getString(Constants.Key.orderExamUuid);
            topicUuids = bundle.getString(Constants.Key.topicUuid);
        }
        Utils.E("orderExamUuid::" + orderExamUuids + "topicUuids::" + topicUuids);

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
           calculateAnswersFromList();

        }
    }

    private void calculateAnswersFromList() {
        ArrayList<String> answerList = new ArrayList<>();
        ArrayList<String> questionList = new ArrayList();
        for (int i = 0; i <= questionListModelArrayList.size() - 1; i++) {
            answerList.add("options_" + questionListModelArrayList.get(i).answer);
            questionList.add(questionListModelArrayList.get(i).questionId);
        }
        String questionIds = "";
        String answers = "";
        for (int i = 0; i <= answerList.size() - 1; i++) {
            if (i==0){
                questionIds = questionIds  + questionList.get(i);
                answers = answers +  answerList.get(i);
            }
            else {
            questionIds = questionIds + "||" + questionList.get(i);
            answers = answers + "||" + answerList.get(i);
        }}
        Utils.E("questionIds::::::::::  " + questionIds);
        Utils.E("answers::::::::::  " + answers);
        submitPracticeTest(questionIds, answers);
    }

    private void submitPracticeTest(String questionIds, String answers) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.orderExamUuid, orderExamUuids);
        hm.put(Constants.Key.topicUuid, topicUuids);
        hm.put(Constants.Key.questionIds, questionIds);
        hm.put(Constants.Key.options, answers);
        apiService.practiceTestSubmit(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, "Test Submitted Successfully");
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.Key.testAttemptUuid,response.body().testAttemptUuid);
                        Utils.I(activity, TestResultActivity.class, bundle);
                        Utils.E("testAttemptUuid::" + response.body().testAttemptUuid);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.orderExamUuid, orderExamUuids);
        hm.put(Constants.Key.topicUuid, topicUuids);
        hm.put(Constants.Key.device_type, "android");
        apiService.getMyQuestionList(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        questionListModelArrayList.clear();
                        //questionListModelArrayList.addAll(Collections.singleton(response.body().questionListModel));
                        questionListModelArrayList.addAll(response.body().questionListModel.questions);
                        if (questionListModelArrayList.isEmpty()) {
                            binding.llparent.setVisibility(View.GONE);
                            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                        } else {
                            questionListModelArrayList.clear();
                            binding.llparent.setVisibility(View.VISIBLE);
                            binding.noResultFoundId.llParent.setVisibility(View.GONE);
                            questionListModelArrayList.addAll(response.body().questionListModel.questions);
                            courseTestQuestionAdapter = new CourseTestQuestionAdapter(activity, questionListModelArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            binding.rvQuestion.setLayoutManager(linearLayoutManager);
                            binding.rvQuestion.setAdapter(courseTestQuestionAdapter);
                        }
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {

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