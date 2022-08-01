package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBlogDetailBinding;

import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    BlogDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BlogDetailActivity.this;
    ActivityBlogDetailBinding binding;
    ApiService apiService;
    String uuid;
    String urlSlug;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        getBlogDetails();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            uuid = bundle.getString(Constants.Key.uuid, Constants.Key.blank);
        }
        binding.ivBack.setOnClickListener(this);

        if (AppController.getInstance().isOnline()) {
            getBlogDetails();
        } else {
            //Utils.InternetDialog(activity);
            InternetDialog();
        }
        binding.flotingBtn.setOnClickListener(this);
    }

    private void getBlogDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogDetails(uuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                  /*  if(response.body().blogs.isEmpty()){
                        binding.llLaunch.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    }else {
                        binding.llLaunch.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }*/
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        AllBlogListModel allBlogListModel=response.body().blog;
                        binding.tvMainHeading.setText(allBlogListModel.blogSortDesc);
                        binding.tvBlogLine.setText(allBlogListModel.blogDetail);
                        binding.tvDate.setText(allBlogListModel.blogDate);
                        binding.tvAuthorName.setText(allBlogListModel.authorName);
                        binding.tvBlogHeading.setText(allBlogListModel.blogCatName);
                        binding.tvTitle.setText(allBlogListModel.blogTitle);
                        Utils.Picasso(allBlogListModel.mainImg, binding.imBlogDetail, R.drawable.dummy);
                        Utils.Picasso(allBlogListModel.featureImg, binding.ivAuthorProfile, R.drawable.dummy);


                        binding.tvBlogLine.setText(HtmlCompat.fromHtml(response.body().blog.blogDetail,0));
                       // Utils.T(activity, "" + response.body().message);
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

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.flotingBtn) {
            bottomSheetTask();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity,android.R.style.Theme_DeviceDefault_Dialog_Alert);
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

    private void bottomSheetTask() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());
        bottomSheetDialog.show();

        bottomSheetRatenowBinding.tvRateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
               /* final boolean globalVisibleRect;
                globalVisibleRect = v.getGlobalVisibleRect(outRect);*/
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}