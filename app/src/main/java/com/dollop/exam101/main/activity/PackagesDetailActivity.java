package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.KeyboardUtils;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityPackagesDetailBinding;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.dollop.exam101.main.fragment.CourseMaterialFragment;
import com.dollop.exam101.main.fragment.MockTestFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.ExamModel;
import com.dollop.exam101.main.model.LanguageModel;
import com.dollop.exam101.main.model.MockTestModel;
import com.dollop.exam101.main.model.PackageDetailModel;
import com.dollop.exam101.main.model.ReviewRating;
import com.dollop.exam101.main.model.SubjectModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = PackagesDetailActivity.this;
    ActivityPackagesDetailBinding binding;
    ApiService apiService;
    String packageUuid;
    String languageUuId;
    MockTestViewPagerAdapter mockTestViewPagerAdapter;
    ArrayList<String> Tittle = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    PakageDetailPrimaryAdapter pakageDetailPrimaryAdapter;
    List<MockTestModel> mockTestModels = new ArrayList<>();
    ArrayList<ReviewRating> reviewRatingModels = new ArrayList<>();
    List<ExamModel> examModelArrayList = new ArrayList<>();
    List<SubjectModel> subjectModelArrayList = new ArrayList<>();
    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;
    CourseMaterialFragment courseMaterialFragment = new CourseMaterialFragment();
    MockTestFragment mockTestFragment = new MockTestFragment();
    PakageDetailMockTestFragmentAdapter pakageDetailMockTestFragmentAdapter;

    String packageName, packageDetail, imgPath, shortDesc;
    private boolean isDone = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackagesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Utils.IS_LOGIN()){
            init();
        }else {
            Utils.I_clear(activity,LoginActivity.class,null);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        setViewPager();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageUuid = bundle.getString(Constants.Key.packageUuId);
            Utils.E("packageUuid;;;;;;" + packageUuid);
            Utils.E("Bundle;;;;;;" + bundle);
        }

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Utils.E("22appLinkAction::"+appLinkData);
        if (appLinkData != null) {
            packageUuid = new String(Base64.decode(appLinkData.getLastPathSegment(), Base64.NO_CLOSE));
            Utils.E("packageUuiduuid::"+packageUuid);
        }

        if (AppController.getInstance().isOnline()) {
            getPackageDetails();
        } else {
            InternetDialog();
        }
        binding.ivBack.setOnClickListener(this);
        binding.mcvAddtoWishlist.setOnClickListener(this);
        binding.tvRateId.setOnClickListener(this);
        binding.llMockTest.setOnClickListener(this);
        binding.llcoursematerial.setOnClickListener(this);
        binding.AddtoCart.setOnClickListener(this);
        binding.mcvShare.setOnClickListener(this);
        if (AppController.getInstance().isOnline()) {
            GetPackageDetailsMockTestListRatingNow();
        } else {
            InternetDialog();
        }


        pakageDetailMockTestFragmentAdapter = new PakageDetailMockTestFragmentAdapter(activity, mockTestModels);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(pakageDetailMockTestFragmentAdapter);


        pakageDetailPrimaryAdapter = new PakageDetailPrimaryAdapter(activity, subjectModelArrayList);
        binding.rvCourseList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvCourseList.setAdapter(pakageDetailPrimaryAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.llcoursematerial) {
            binding.tvCourseMaterial.setTextColor(ContextCompat.getColor(activity, R.color.theme));
            binding.viewCourseMaterial.setVisibility(View.VISIBLE);
            binding.tvMockTest.setTextColor(ContextCompat.getColor(activity, R.color.black));
            binding.viewMockTest.setVisibility(View.GONE);
            binding.rvCourseList.setVisibility(View.VISIBLE);
            binding.rvMockTestList.setVisibility(View.GONE);
        }
        if (view == binding.llMockTest) {
            binding.tvCourseMaterial.setTextColor(ContextCompat.getColor(activity, R.color.black));
            binding.viewCourseMaterial.setVisibility(View.GONE);
            binding.tvMockTest.setTextColor(ContextCompat.getColor(activity, R.color.theme));
            binding.viewMockTest.setVisibility(View.VISIBLE);

            binding.rvCourseList.setVisibility(View.GONE);
            binding.rvMockTestList.setVisibility(View.VISIBLE);
        }
        if (view == binding.tvRateId) {
            if (AppController.getInstance().isOnline()) {
                rateNowBottomSheet();
            } else {
                InternetDialog();
            }
        } else if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.mcvAddtoWishlist) {
            if (AppController.getInstance().isOnline()) {
                addToWishList();
            } else {
                InternetDialog();
            }
        } else if (view == binding.AddtoCart) {
            if (AppController.getInstance().isOnline()) {
                addCart();
            } else {
                InternetDialog();
            }

        }else if (view == binding.mcvShare){
            shearIntent();
        }
    }

    private void shearIntent() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(Constants.Key.TEXT_PLAIN_TYPE);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.Key.Exam101);
            String shareMessage= "\nLet me recommend you this package\n\n";
            shareMessage = shareMessage + Const.Url.HOST_URL+Constants.Key.exam+ Base64.encodeToString(packageUuid.getBytes(), Base64.NO_CLOSE);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, Constants.Key.choose_one));
        } catch(Exception e) {
            //e.toString();
        }
    }
    private void addToWishList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.packageUuid, packageUuid);
        apiService.addWishlist(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, getString(R.string.added_to_wishlist));
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

    private void addCart() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.packageUuId, packageUuid);
        hm.put(Constants.Key.languageUuId, languageUuId);
        Utils.E("packageUuid:::::::" + packageUuid);
        Utils.E("languageUuId:::::::" + languageUuId);
        apiService.addCart(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, "Added to Cart Successfully");
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

    private void getPackageDetails() {
        binding.rvmain.setVisibility(View.GONE);

        Dialog progressDialog = Utils.initProgressDialog(activity);
        Utils.E("packageUid:::::" + packageUuid);
        apiService.getPackageDetailApi(Utils.GetSession().token, packageUuid).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                if (response.code() == StatusCodeConstant.OK) {
                    assert response.body() != null;
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                    binding.rvmain.setAnimation(animation);
                    binding.rvmain.setVisibility(View.VISIBLE);

                    PackageDetailModel packageDetailModels = response.body().packageDetail;
                    List<LanguageModel> languageModels = response.body().packageDetail.languageModels;
                    binding.scrollView.setVisibility(View.VISIBLE);
                    binding.llBtn.setVisibility(View.VISIBLE);
                    packageName = packageDetailModels.packageName;
                    packageDetail = String.valueOf(HtmlCompat.fromHtml(response.body().packageDetail.packageDetail, 0));
                    shortDesc = String.valueOf(HtmlCompat.fromHtml(response.body().packageDetail.shortDesc, 0));
                    imgPath = packageDetailModels.featureImg;
                    binding.tvHeadings.setText(packageName);
                    binding.tvLanguage.setText(languageModels.get(0).languageName);
//                    binding.tvDates.setText("Purchased on: " + TimeFormatter.getDateTime(packageDetailModels.d, activity, "yyyy-MM-dd HH:mm:ss", "Date"));
                    binding.tvPriceGreat.setText(new DecimalFormat("##.##").format(Double.parseDouble(packageDetailModels.discountedPrice)));
                    binding.tvPriceSmall.setText(new DecimalFormat("##.##").format(Double.parseDouble(packageDetailModels.actualPrice)));
                    binding.tvDescription.setText(HtmlCompat.fromHtml(response.body().packageDetail.shortDesc, 0));
                    languageUuId = packageDetailModels.languageModels.get(0).languageUuid;
                    if (packageDetail.isEmpty()) {
                        binding.tvDetail.setVisibility(View.GONE);
                        binding.tvOverView.setVisibility(View.GONE);
                    } else {
                        binding.tvDetail.setVisibility(View.VISIBLE);
                        binding.tvDetail.setText(HtmlCompat.fromHtml(response.body().packageDetail.packageDetail, 0));
                    }

                    Utils.E("languageUuId::" + languageUuId);
                    mockTestModels.addAll(packageDetailModels.mockTests);
                    Utils.E("mockTestModels::" + mockTestModels);
                    examModelArrayList.addAll(packageDetailModels.examModels);
                    Utils.E("examModelArrayList::" + examModelArrayList);
                    subjectModelArrayList.clear();
                    for (int i = 0; i < examModelArrayList.size(); i++) {
                        subjectModelArrayList.addAll(examModelArrayList.get(i).subjects);
                    }

                    if (subjectModelArrayList.isEmpty() && mockTestModels.isEmpty()) {
                        binding.viewtwo.setVisibility(View.GONE);
                        binding.llTabView.setVisibility(View.GONE);
                        binding.llTab.setVisibility(View.GONE);
                    } else {
                        binding.viewtwo.setVisibility(View.VISIBLE);
                        binding.llTab.setVisibility(View.VISIBLE);
                        binding.llTabView.setVisibility(View.VISIBLE);
                    }
                    Utils.E("subjectModelArrayList::" + subjectModelArrayList);
                    if (!subjectModelArrayList.isEmpty()) {
                        courseMaterialFragment.subjectModelArrayList.clear();
                        courseMaterialFragment.subjectModelArrayList.addAll(subjectModelArrayList);
                        courseMaterialFragment.UpdateData();
                        pakageDetailPrimaryAdapter.notifyDataSetChanged();

                    } else {
                        binding.llcoursematerial.setVisibility(View.GONE);
                        binding.llMockTest.performClick();
                        fragments.remove(courseMaterialFragment);
                        binding.tlPackageDetailTabLayoutId.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }

                    if (!mockTestModels.isEmpty()) {
                        mockTestFragment.mockTestModels.clear();
                        mockTestFragment.mockTestModels.addAll(mockTestModels);
                        mockTestFragment.UpdateData();
                    } else {
                        binding.llMockTest.setVisibility(View.GONE);
                        binding.llcoursematerial.performClick();
                        fragments.remove(mockTestFragment);
                        binding.tlPackageDetailTabLayoutId.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                    mockTestViewPagerAdapter.notifyDataSetChanged();

                } else {
                    if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Utils.T(activity, message.message);
                    } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Utils.T(activity, message.message);
                        Utils.UnAuthorizationToken(activity);
                    }
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

    private void setViewPager() {
        Tittle.clear();
        fragments.clear();
        fragments.add(courseMaterialFragment);
        fragments.add(mockTestFragment);
        Tittle.add(Constants.Key.Course_Material);
        Tittle.add(Constants.Key.Mock_Test);
        mockTestViewPagerAdapter = new MockTestViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.ViewPagerPackageDetailId.setAdapter(mockTestViewPagerAdapter);
        new TabLayoutMediator(binding.tlPackageDetailTabLayoutId, binding.ViewPagerPackageDetailId, (tab, position) -> {
            tab.setText(Tittle.get(position));
        }).attach();
    }

    private void GetPackageDetailsMockTestListRatingNow() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getPackageDetailsMockTestListRatingNow(Utils.GetSession().token, packageUuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        reviewRatingModels.clear();
                        assert response.body() != null;
                        reviewRatingModels.addAll(response.body().reviewRating);
                        if (reviewRatingModels.isEmpty() || reviewRatingModels.equals(Constants.Key.blank)) {
                        } else {
                            binding.rvRatingId.setLayoutManager(new LinearLayoutManager(activity));
                            binding.rvRatingId.setAdapter(new PakageDetailRatingAdapter(activity, reviewRatingModels));
                            Utils.E("^@%reviewRatingELSE::");
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
            }
        });
    }

    private void addRatingReview(Float rating, String review) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.packageUuId, packageUuid);
        hm.put(Constants.Key.rating, String.valueOf(rating));
        hm.put(Constants.Key.review, review);
        apiService.addRatingReview(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        GetPackageDetailsMockTestListRatingNow();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.scrollView.setVisibility(View.GONE);
        binding.llBtn.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                getPackageDetails();
                binding.scrollView.setVisibility(View.VISIBLE);
                binding.llBtn.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);
            }
        });

    }

    private void rateNowBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetRatenowBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetDialog.show();
        bottomSheetRatenowBinding.llParent.setOnClickListener(KeyboardUtils::hideKeyboard);

        bottomSheetRatenowBinding.etShareThoughts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bottomSheetRatenowBinding.tvErrorReview.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bottomSheetRatenowBinding.tvHeading.setText(packageName);
        bottomSheetRatenowBinding.tvSubHeading.setText(shortDesc);
        Picasso.get().load(Const.Url.HOST_URL + imgPath).error(R.drawable.dummy).
                into(bottomSheetRatenowBinding.ivPhotoId);
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
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Utils.E("SimpleDateFormat::::" + currentDate + currentTime);
                addRatingReview(bottomSheetRatenowBinding.rating.getRating(), bottomSheetRatenowBinding.etShareThoughts.getText().toString().trim());
                bottomSheetDialog.cancel();
            }
        });
    }
}