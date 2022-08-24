package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityAllPackageBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.CategoriesFragment;
import com.dollop.exam101.main.fragment.LanguageFragment;
import com.dollop.exam101.main.fragment.PriceFragment;
import com.dollop.exam101.main.model.AllResponseModel;
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

public class AllPackageActivity extends AppCompatActivity implements View.OnClickListener, OnItemClicked {
    private final String[] labels = new String[]{Constants.Key.Exams, Constants.Key.Price, Constants.Key.Language};
    public String LanguageId = "";
    public String ExamId = "";
    public int Positions = -1, LanguagePos = -1;
    Activity activity = AllPackageActivity.this;
    ActivityAllPackageBinding binding;
    ApiService apiService;
    String token, MinValue = "", MaxValue = "";
    BottomsheetFilterBinding bottomsheetFilterBinding;
    BottomSheetDialog bottomSheetDialog;
    ArrayList<PackageModel> packageModelList = new ArrayList<>();
    PackageAdapter packageAdapter;
    CategoriesFragment categoriesFragment;
    PriceFragment priceFragment;
    LanguageFragment languageFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        if (AppController.getInstance().isOnline()) {
            packageList();
        } else {
            InternetDialog();
        }

        binding.llBtnFilter.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llBtnFilter) {
            bottomSheetFilterTask();
        }
    }

    private void packageList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.Key.examId, ExamId);
        hashMap.put(Constants.Key.languageId, LanguageId);
        hashMap.put(Constants.Key.discountedPriceStart, MinValue);
        hashMap.put(Constants.Key.discountedPriceEnd, MaxValue);
        apiService.packageListItem(token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().packageModels.isEmpty()) {
                        binding.rvPackagesone.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvPackagesone.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    if (response.code() == StatusCodeConstant.OK) {
                        packageModelList.clear();
                        assert response.body() != null;
                        packageModelList.addAll(response.body().packageModels);

                        packageAdapter = new PackageAdapter(activity, packageModelList);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                        binding.rvPackagesone.setLayoutManager(linearLayoutManager2);
                        binding.rvPackagesone.setAdapter(packageAdapter);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {

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

    private void bottomSheetFilterTask() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomsheetFilterBinding.getRoot());

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetFilterBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setMaxHeight(binding.rvPackagesone.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);

        bottomSheetDialog.show();
        ArrayList<Fragment> Fragment = new ArrayList<>();
        categoriesFragment = new CategoriesFragment(Constants.Key.PackageFragment, AllPackageActivity.this, Positions);
        priceFragment = new PriceFragment(Constants.Key.PackageFragment, AllPackageActivity.this);
        languageFragment = new LanguageFragment(Constants.Key.PackageFragment, AllPackageActivity.this, LanguagePos);
        Fragment.add(categoriesFragment);
        Fragment.add(priceFragment);
        Fragment.add(languageFragment);

        bottomsheetFilterBinding.tvllSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                if (categoriesFragment.categoriesFragmentAdapter != null && categoriesFragment.categoriesFragmentAdapter.newPos != -1) {
                    ExamId = categoriesFragment.categoriesFragmentAdapter.examId;
                    Positions = categoriesFragment.categoriesFragmentAdapter.newPos;

                }
                if (languageFragment.languageAdapter != null && languageFragment.languageAdapter.index != -1) {
                    LanguageId = languageFragment.languageAdapter.languageId;
                    LanguagePos = languageFragment.languageAdapter.index;
                }
                if (priceFragment != null) {
                    MinValue = priceFragment.minValue;
                    MaxValue = priceFragment.maxValue;
                }

                if (AppController.getInstance().isOnline()) {
                    packageList();
                } else {
                    InternetDialog();
                }
                Utils.E("ID:::::" + ExamId + "LId::" + LanguageId + "MValue::" + priceFragment.minValue + "MaxValue::" + priceFragment.maxValue +
                        "Positions:" + Positions);

                bottomSheetDialog.cancel();
            }
        });
        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), Fragment));

        new TabLayoutMediator(bottomsheetFilterBinding.tlTabLayoutId, bottomsheetFilterBinding.ViewPagerId, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();

        View tab1 = ((ViewGroup) bottomsheetFilterBinding.tlTabLayoutId.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.rvPackagesone.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.rvPackagesone.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
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
}