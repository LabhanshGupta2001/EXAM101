package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBlogDetailBinding;
import com.dollop.exam101.main.adapter.AllBlogListAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.BlogListHeadingModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BlogDetailActivity.this;
    ActivityBlogDetailBinding binding;
    ApiService apiService;
    String urlSlug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getBlogDetails();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            urlSlug = bundle.getString(Constants.Key.urlSlug, Constants.Key.blank);
        }
        binding.ivBack.setOnClickListener(this);
    }

    private void getBlogDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogDetails(urlSlug).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
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
        }
    }
}