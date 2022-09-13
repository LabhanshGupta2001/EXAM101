package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.BuildConfig;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBlogDetailBinding;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.main.adapter.AllBlogListAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BlogDetailActivity.this;
    ActivityBlogDetailBinding binding;
    ApiService apiService;
    String uuid = Constants.Key.blank;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;
    AllBlogListAdapter allBlogListAdapter;
    AllBlogListModel allBlogListModel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Utils.IS_LOGIN()){
            init();
        }else {
            Utils.I_clear(activity,LoginActivity.class,null);
            finish();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            uuid = bundle.getString(Constants.Key.uuid);
        }

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Utils.E("22appLinkAction::"+appLinkData);
        if (appLinkData != null) {
            uuid = new String(Base64.decode(appLinkData.getLastPathSegment(), Base64.NO_CLOSE));
            Utils.E("uuid::"+uuid);
        }

        binding.ivBack.setOnClickListener(this);
        if (AppController.getInstance().isOnline()) {
            getBlogDetails();
        } else {

            InternetDialog();
        }
        // binding.flotingBtn.setOnClickListener(this);
        binding.mcvFacebook.setOnClickListener(this);
        binding.mcvLinkIn.setOnClickListener(this);
        binding.mcvInstagram.setOnClickListener(this);
        binding.mcvTwitter.setOnClickListener(this);
        binding.mcvShare.setOnClickListener(this);


        /*allBlogListAdapter = new AllBlogListAdapter(activity, Blogarraylist);
        binding.rvBlogs.setAdapter(allBlogListAdapter);
        binding.rvBlogs.setLayoutManager(new LinearLayoutManager(activity));*/
    }

    private void getBlogDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        Utils.E("APIuuid::"+uuid);
        apiService.getBlogDetails(uuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        allBlogListModel = response.body().blog;
                        binding.tvMainHeading.setText(allBlogListModel.blogSortDesc);
                        binding.tvBlogLine.setText(allBlogListModel.blogDetail);
                        binding.tvDate.setText(allBlogListModel.blogDate);
                        binding.tvAuthorName.setText(allBlogListModel.authorName);
                        binding.tvBlogHeading.setText(allBlogListModel.blogCatName);
                        binding.tvAuthorName2.setText(allBlogListModel.blogAuthorName);
                        binding.tvAuthorDescription.setText(allBlogListModel.authorbio);
                        Picasso.get().load(Const.Url.HOST_URL + allBlogListModel.authorImage).error(R.drawable.dummy).
                                into(binding.ivAuthorProfile2);
                        binding.tvTitle.setText(allBlogListModel.blogTitle);
                        //  Utils.Picasso(allBlogListModel.mainImg, binding.imBlogDetail, R.drawable.dummy);
                        Picasso.get().load(Const.Url.HOST_URL + allBlogListModel.mainImg).error(R.drawable.dummy).
                                into(binding.imBlogDetail);
                        Picasso.get().load(Const.Url.HOST_URL + allBlogListModel.featureImg).error(R.drawable.dummy).
                                into(binding.ivAuthorProfile);
                        //  Utils.Picasso(allBlogListModel.featureImg, binding.ivAuthorProfile, R.drawable.dummy);
                        binding.tvBlogLine.setText(HtmlCompat.fromHtml(response.body().blog.blogDetail, 0));
                       if (!allBlogListModel.authorFBLink.isEmpty() || !allBlogListModel.authorInstaLink.isEmpty() ||
                               !allBlogListModel.authorLinkdedLink.isEmpty() || !allBlogListModel.authorTwitLink.isEmpty()) {

                           if (!allBlogListModel.authorFBLink.isEmpty()) {
                               binding.mcvFacebook.setVisibility(View.VISIBLE);
                           }
                           if (!allBlogListModel.authorInstaLink.isEmpty()) {
                               binding.mcvInstagram.setVisibility(View.VISIBLE);
                           }
                           if (!allBlogListModel.authorLinkdedLink.isEmpty()) {
                               binding.mcvLinkIn.setVisibility(View.VISIBLE);
                           }
                           if (!allBlogListModel.authorTwitLink.isEmpty()) {
                               binding.mcvTwitter.setVisibility(View.VISIBLE);
                           }
                       }else {
                            binding.tvSocialMedia.setVisibility(View.GONE);
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

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }else if (view == binding.mcvFacebook){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(allBlogListModel.authorFBLink));
                intent.setPackage("com.facebook.orca");
                startActivity(intent);
            }
            catch (ActivityNotFoundException anfe)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(allBlogListModel.authorInstaLink)));
            }
        }else if (view == binding.mcvInstagram){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(allBlogListModel.authorInstaLink));
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            }
            catch (ActivityNotFoundException anfe)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(allBlogListModel.authorInstaLink)));
            }
        }else if (view == binding.mcvTwitter){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(allBlogListModel.authorTwitLink));
                intent.setPackage("com.twitter.android");
                startActivity(intent);
            }
            catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(allBlogListModel.authorTwitLink)));
            }
        }else if (view == binding.mcvLinkIn){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(allBlogListModel.authorLinkdedLink));
                intent.setPackage("com.linkedin.android");
                startActivity(intent);
            }
            catch (ActivityNotFoundException anfe)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(allBlogListModel.authorLinkdedLink)));
            }
        } else if (view == binding.mcvShare){
            shearIntent();
        }

        /*else if (view == binding.flotingBtn) {
            bottomSheetTask();
        }*/
    }

    private void shearIntent() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(Constants.Key.TEXT_PLAIN_TYPE);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.Key.Exam101);
            String shareMessage= "\nLet me recommend you this blog\n\n";
            shareMessage = shareMessage + Const.Url.HOST_URL+Constants.Key.Blog+Base64.encodeToString(uuid.getBytes(), Base64.NO_CLOSE);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, Constants.Key.choose_one));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.svMain.setVisibility(View.GONE);
        //binding.flotingBtn.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.svMain.setVisibility(View.VISIBLE);
                // binding.flotingBtn.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });
    }

    private void bottomSheetTask() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetRatenowBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetRatenowBinding.tvMainName.setText(R.string.rate_blog);
        bottomSheetDialog.show();

        bottomSheetRatenowBinding.tvRateNow.setOnClickListener(view -> {
            if (bottomSheetRatenowBinding.etShareThoughts.getText().toString().trim().equals(Constants.Key.blank)) {
                bottomSheetRatenowBinding.tvErrorReview.setVisibility(View.VISIBLE);
                bottomSheetRatenowBinding.tvErrorReview.setText(R.string.empty_error);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_to_bottom);
                bottomSheetRatenowBinding.tvErrorReview.startAnimation(animation);
                bottomSheetRatenowBinding.etShareThoughts.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(bottomSheetRatenowBinding.etShareThoughts, InputMethodManager.SHOW_IMPLICIT);
            } else {
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