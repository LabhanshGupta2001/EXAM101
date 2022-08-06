package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCoursesDetailBinding;
import com.dollop.exam101.databinding.BottomSheetPracticeTestBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.TopicDetailModel;
import com.dollop.exam101.main.model.WishListModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesDetailActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CoursesDetailActivity.this;
    ActivityCoursesDetailBinding binding;
    BottomSheetPracticeTestBinding bottomSheetPracticeTestBinding;
    BottomSheetDialog bottomSheetDialog;
    ApiService apiService;
    //ArrayList<TopicDetailModel> topicList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

        if (AppController.getInstance().isOnline()) {
            getCourseDetails();
        } else {
            Utils.InternetDialog(activity);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if ((view == binding.btnNext)) {
            showBottomSheetDialog();
        }

    }

    private void getCourseDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoiZ2VldEBnbWFpbC5jb20iLCJ1c2VyX2lkIjoiYWQ5YzhhNzQtMGNmMi0xMWVkLTk3NTQtMDAwYzI5MTE1MWViIiwicm9sZSI6IlN0dWRlbnQiLCJBUElfVElNRSI6MTY1OTY3MjgwMX0.BBr3FZ9vob8SC5Q5cj20h-vRHFiX4dDeej2eZoF_grk";
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.orderExamUuid, "fe2d148e-0f0c-11ed-9754-000c291151eb");
        hm.put(Constants.Key.topicUuid, "d4c9f70d-1257-11ed-967e-000c291151eb");
        apiService.getTopicDetails(token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                      // Utils.E("TopicDetail"+response.body());
                        binding.tvChapterName.setText(response.body().topicDetailModel.topicName);
                        binding.tvTopicSummary.setText(response.body().topicDetailModel.topicSummary);
                        binding.tvTopicDetail.setText(HtmlCompat.fromHtml(response.body().topicDetailModel.topicDetail,0));
                       /* if (response.body().topicDetailModel.video != null || response.body().topicDetailModel.video != Constants.Key.blank ){
                            binding.vvVideoView.setVideoPath(response.body().topicDetailModel.video);
                        }*/
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

    private void showBottomSheetDialog() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetPracticeTestBinding = BottomSheetPracticeTestBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetPracticeTestBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetPracticeTestBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

        bottomSheetPracticeTestBinding.tvBtnPracticeTest.setOnClickListener(view ->
        {
            bottomSheetDialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.orderExamUuid, "fe2d148e-0f0c-11ed-9754-000c291151eb");
            bundle.putString(Constants.Key.topicUuid, "d4c9f70d-1257-11ed-967e-000c291151eb");
            Utils.I(activity, CourseTestActivity.class, bundle);
        });

    }

}