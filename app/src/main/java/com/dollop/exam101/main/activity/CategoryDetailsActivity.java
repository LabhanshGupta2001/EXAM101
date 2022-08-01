package com.dollop.exam101.main.activity;

import static com.dollop.exam101.Basics.UtilityTools.AppController.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCategoryDetailsBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.main.adapter.CategoryDetailAdapter;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.CategoriesFragment;
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
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends BaseActivity implements View.OnClickListener, OnItemClicked {

    public String examId = "";
    public String Price = "";
    public String languageId = "";
    String ExamName = "", MinValue = "", MaxValue = "";
    int Positions;
    Activity activity = CategoryDetailsActivity.this;
    ActivityCategoryDetailsBinding binding;
    ArrayList<PackageModel> arrayList = new ArrayList<>();
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    ApiService apiService;
    BottomSheetDialog bottomSheetFilter;
    BottomsheetFilterBinding bottomsheetFilterBinding;
    PackageAdapter packageAdapter;
    CategoriesFragment categoriesFragment;
    PriceFragment priceFragment;
    LanguageFragment languageFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            examId = bundle.getString(Constants.Key.examId);
            Positions = bundle.getInt(Constants.Key.Position, 0);
            ExamName = bundle.getString(Constants.Key.examName);
        }
        apiService = RetrofitClient.getClient();
        binding.tvToolbarName.setText(ExamName);
        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);

        if (AppController.getInstance().isOnline()) {
            getCategoryDetails(examId);
            getCategoriesItemHeading();
        } else {
            InternetDialog();
        }

        packageAdapter = new PackageAdapter(activity, arrayList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvPackagesSecondary.setLayoutManager(linearLayoutManager2);
        binding.rvPackagesSecondary.setAdapter(packageAdapter);
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

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetFilterBinding.getRoot().getParent()));
        bottomSheetFilter.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetFilter.show();

        ArrayList<String> title = new ArrayList<>();
        title.add(Constants.Key.Exams);
        title.add(Constants.Key.Price);
        title.add(Constants.Key.Language);

        ArrayList<Fragment> Fragment = new ArrayList<>();
        categoriesFragment = new CategoriesFragment(Constants.Key.PackageFragment, CategoryDetailsActivity.this);
        priceFragment = new PriceFragment(Constants.Key.PackageFragment, CategoryDetailsActivity.this);
        languageFragment = new LanguageFragment(Constants.Key.PackageFragment, CategoryDetailsActivity.this);
        Fragment.add(categoriesFragment);
        Fragment.add(priceFragment);
        Fragment.add(languageFragment);


        bottomsheetFilterBinding.tvllSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoriesFragment.categoriesFragmentAdapter != null && categoriesFragment.categoriesFragmentAdapter.index != -1) {
                    examId = categoriesFragment.categoriesFragmentAdapter.examId;
                }
                if (languageFragment.languageAdapter != null && languageFragment.languageAdapter.index != -1) {
                    languageId = languageFragment.languageAdapter.languageId;
                }
                if (priceFragment != null) {
                    MinValue = priceFragment.minValue;
                    MaxValue = priceFragment.maxValue;
                }
                getCategoryDetails(examId);
                Utils.E("ID:::::" + examId + "LId::" + languageId + "MValue::" + priceFragment.minValue + "MaxValue::" + priceFragment.maxValue);

                bottomSheetFilter.cancel();
            }
        });

        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), Fragment));

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
                        binding.rvPackagesHeading.setAdapter(new CategoryDetailAdapter(activity, courseModelArrayList, Positions));
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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.Key.examId, ExamId);
        hashMap.put(Constants.Key.languageId, languageId);
        hashMap.put(Constants.Key.discountedPriceStart, MinValue);
        hashMap.put(Constants.Key.discountedPriceEnd, MaxValue);
        apiService.packageListItem(Utils.GetSession().token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().packageModels.isEmpty()) {
                        binding.rvPackagesSecondary.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvPackagesSecondary.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    if (response.code() == StatusCodeConstant.OK) {
                        arrayList.clear();
                        assert response.body() != null;
                        arrayList.addAll(response.body().packageModels);
                        packageAdapter.notifyDataSetChanged();
                     /*   binding.rvPackagesSecondary.setAdapter(new CategoryDetailSecondaryAdapter(activity, arrayList));
                        binding.rvPackagesSecondary.setHasFixedSize(true);
                        binding.rvPackagesSecondary.setLayoutManager(new LinearLayoutManager(activity));*/

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

    @Override
    public void onClickedExamID(String s) {

    }

    @Override
    public void onClickedLanguageID(String s) {

    }

    @Override
    public void onClickedPrice(String s) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Dialog_Alert);
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
}