package com.dollop.exam101.main.activity;

import static com.dollop.exam101.Basics.UtilityTools.AppController.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCategoryDetailsBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.main.adapter.CategoryDetailAdapter;
import com.dollop.exam101.main.adapter.CategoryDetailSecondaryAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.ExamFilterFragment;
import com.dollop.exam101.main.fragment.LanguageFragment;
import com.dollop.exam101.main.fragment.PriceFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseModel;
import com.dollop.exam101.main.model.PackageModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends BaseActivity implements View.OnClickListener {

    public String examId = null;
    Activity activity = CategoryDetailsActivity.this;
    ActivityCategoryDetailsBinding binding;
    ArrayList<PackageModel> arrayList = new ArrayList<>();
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    ApiService apiService;
    BottomSheetDialog bottomSheetFilter;
    BottomsheetFilterBinding bottomsheetFilterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            examId = bundle.getString(Constants.Key.examId);
        }
        apiService = RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);
        getCategoryDetails(examId);
        getCategoriesItemHeading();

      /*  list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvPackagesHeading.setAdapter(new CategoryDetailAdapter(activity, list));
        binding.rvPackagesHeading.setHasFixedSize(true);
        binding.rvPackagesHeading.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));*/
/*
        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");

        binding.rvPackagesSecondary.setAdapter(new CategoryDetailSecondaryAdapter(activity, arrayList));
        binding.rvPackagesSecondary.setHasFixedSize(true);
        binding.rvPackagesSecondary.setLayoutManager(new LinearLayoutManager(activity));*/
    }

    @Override
    public void onClick(View view) {

        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.tvFilter) {
            bottomSheetFilterTask();
        }
    }

    private void bottomSheetFilterTask() {
        bottomSheetFilter = new BottomSheetDialog(activity);
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetFilter.setContentView(bottomsheetFilterBinding.getRoot());

       /* BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomsheetFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
*/
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetFilterBinding.getRoot().getParent()));
        bottomSheetFilter.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetFilter.show();

        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add(Constants.Key.Exams);
        title.add(Constants.Key.Price);
        title.add(Constants.Key.Language);

        fragments.add(new ExamFilterFragment());
        fragments.add(new PriceFragment());
        fragments.add(new LanguageFragment());

        bottomsheetFilterBinding.tvllSave.setOnClickListener(view ->
                bottomSheetFilter.cancel());

        /*TabLayout tabLayout = bottomSheetFilter.findViewById(R.id.tlFilter);
        ViewPager2 viewPager2 = bottomSheetFilter.findViewById(R.id.vpLaunchId);*/
        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(bottomsheetFilterBinding.tlTabLayoutId, bottomsheetFilterBinding.ViewPagerId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();

        View tab1 = ((ViewGroup) bottomsheetFilterBinding.tlTabLayoutId.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();
    }

    private void getCategoriesItemHeading() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.Examlist(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        courseModelArrayList.clear();
                        courseModelArrayList.addAll(response.body().examListModels);
                        binding.rvPackagesHeading.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                        binding.rvPackagesHeading.setAdapter(new CategoryDetailAdapter(activity, courseModelArrayList));
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

    public void getCategoryDetails(String ExamId) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.examPackageListItem(Utils.GetSession().token, ExamId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        arrayList.clear();
                        assert response.body() != null;
                        arrayList.addAll(response.body().packageModels);
                        binding.rvPackagesSecondary.setAdapter(new CategoryDetailSecondaryAdapter(activity, arrayList));
                        binding.rvPackagesSecondary.setHasFixedSize(true);
                        binding.rvPackagesSecondary.setLayoutManager(new LinearLayoutManager(activity));

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.T(activity, message.message);
                                Utils.UnAuthorizationToken(activity);
                            }
                        } else {
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


    private void getCategoryFilter() {
        apiService.getCategoryFilter("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}