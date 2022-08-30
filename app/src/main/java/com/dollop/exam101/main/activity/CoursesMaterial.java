package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.TimeFormatter;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCoursesMaterialBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.main.adapter.CourseMaterialSubjectAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.Subject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesMaterial extends BaseActivity implements View.OnClickListener {
    private final Boolean dropdown = true;
    Activity activity = CoursesMaterial.this;
    ActivityCoursesMaterialBinding binding;
    ArrayList<Subject> subjectlist = new ArrayList<>();
    ApiService apiService;
    Bundle bundle;
    String orderExamUuid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(this);
        bundle = getIntent().getExtras();
        orderExamUuid = bundle.getString(Constants.Key.orderExamUuid);
        Utils.E("orderExamUuid:::" + orderExamUuid);
        apiService = RetrofitClient.getClient();
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        if (AppController.getInstance().isOnline()) {
            CategoriesHomeAllExamList();

        } else {
            // Utils.InternetDialog(activity);
            InternetDialog();
        }
        setProgress();
        // getCourseList();
    }

    void setProgress() {

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

    private void CategoriesHomeAllExamList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put(Constants.Key.orderExamUuid, orderExamUuid);
        Utils.E("done:::");

        apiService.getStudentExamDetailApi(Utils.GetSession().token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        binding.tvCourseName.setText(response.body().orderexams.examName);
                        binding.tvPercentComplete.setText(response.body().orderexams.completedPercentage + "% Complete");
                        binding.tvLastActivityTime.setText(" "+(TimeFormatter.getDateTime(response.body().orderexams.lastActivityDate, activity, "yyyy-MM-dd HH:mm:ss", "Date")));
                        binding.progressBar.setProgress(Integer.parseInt(response.body().orderexams.completedPercentage));
                        binding.progressBar.setMax(100);
                        subjectlist.addAll(response.body().orderexams.subjects);
                        if (subjectlist.isEmpty()){
                            binding.rvCourseList.setVisibility(View.GONE);
                        }
                        else {
                        binding.rvCourseList.setHasFixedSize(true);
                        binding.rvCourseList.setLayoutManager(new LinearLayoutManager(activity));
                        binding.rvCourseList.setAdapter(new CourseMaterialSubjectAdapter(activity, subjectlist));}
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(activity, message.message);
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