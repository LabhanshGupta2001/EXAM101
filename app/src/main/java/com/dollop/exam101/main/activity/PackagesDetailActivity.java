package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityPackagesDetailBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;
import com.dollop.exam101.main.adapter.OverviewCourseDetailsAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.dollop.exam101.main.fragment.CourseMaterialFragment;
import com.dollop.exam101.main.fragment.MockTestFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.ExamModel;
import com.dollop.exam101.main.model.MockTestModel;
import com.dollop.exam101.main.model.PackageDetailModel;
import com.dollop.exam101.main.model.ReviewRating;
import com.dollop.exam101.main.model.SubjectModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
    List<MockTestModel> mockTestModels = new ArrayList<>();
    ArrayList<ReviewRating> reviewRatingModels = new ArrayList<>();
    List<ExamModel> examModelArrayList = new ArrayList<>();
    List<SubjectModel> subjectModelArrayList = new ArrayList<>();
    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;
    String packageName, packageDetail, imgPath,shortDesc;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackagesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        Utils.E("MY Data -> "+Utils.GetSession().token+" Package UUID"+packageUuid);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getInstance().isOnline()) {
            getPackageDetails();
        } else {
            InternetDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageUuid = bundle.getString(Constants.Key.packageUuId);
            Utils.E("packageUuid;;;;;;" + packageUuid);
            Utils.E("Bundle;;;;;;" + bundle);
        }

        binding.ivBack.setOnClickListener(this);
        binding.mcvAddtoWishlist.setOnClickListener(this);
        binding.tvRateId.setOnClickListener(this);
        binding.AddtoCart.setOnClickListener(this);

        if (AppController.getInstance().isOnline()) {
            GetPackageDetailsMockTestListRatingNow();
        } else {
            InternetDialog();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
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

    private void getPackageDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        Utils.E("packageUid:::::" + packageUuid);
        apiService.getPackageDetailApi(Utils.GetSession().token, packageUuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                if (response.code() == StatusCodeConstant.OK) {
                    assert response.body() != null;
                    PackageDetailModel packageDetailModels = response.body().packageDetail;
                    binding.scrollView.setVisibility(View.VISIBLE);
                    binding.llBtn.setVisibility(View.VISIBLE);
                    packageName = packageDetailModels.packageName;
                    packageDetail = String.valueOf(HtmlCompat.fromHtml(response.body().packageDetail.packageDetail, 0));
                    shortDesc=String.valueOf(HtmlCompat.fromHtml(response.body().packageDetail.shortDesc, 0));
                    imgPath = packageDetailModels.featureImg;
                    binding.tvHeadings.setText(packageName);
                    binding.tvPriceGreat.setText(packageDetailModels.discountedPrice);
                    binding.tvPriceSmall.setText(packageDetailModels.actualPrice);
                    binding.tvDescription.setText(HtmlCompat.fromHtml(response.body().packageDetail.shortDesc, 0));
                    binding.tvDetail.setText(HtmlCompat.fromHtml(response.body().packageDetail.packageDetail, 0));
                    languageUuId = packageDetailModels.languageModels.get(0).languageUuid;

                    Utils.E("languageUuId::" + languageUuId);
                    mockTestModels = packageDetailModels.mockTests;
                    Utils.E("mockTestModels::"+mockTestModels);
                    examModelArrayList.addAll(packageDetailModels.examModels);
                    Utils.E("examModelArrayList::"+examModelArrayList);
                    for (int i = 0; i < examModelArrayList.size(); i++) {
                        subjectModelArrayList.addAll(examModelArrayList.get(i).subjects);
                    }
                    fragments.add(new CourseMaterialFragment(subjectModelArrayList));
                    Tittle.clear();
                    Tittle.add(Constants.Key.Course_Material);

                    if (mockTestModels.isEmpty() || mockTestModels.equals(Constants.Key.blank)){
                    }else {
                        Tittle.add(Constants.Key.Mock_Test);
                        fragments.add(new MockTestFragment(mockTestModels));
                    }
                    mockTestViewPagerAdapter = new MockTestViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
                    binding.ViewPagerPackageDetailId.setAdapter(mockTestViewPagerAdapter);
                    new TabLayoutMediator(binding.tlPackageDetailTabLayoutId, binding.ViewPagerPackageDetailId, (tab, position) -> {
                        tab.setText(Tittle.get(position));
                    }).attach();

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
                        binding.rvRatingId.setLayoutManager(new LinearLayoutManager(activity));
                        binding.rvRatingId.setAdapter(new PakageDetailRatingAdapter(activity, reviewRatingModels));

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

    private void rateNowBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetRatenowBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetDialog.show();

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
            } else {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Utils.E("SimpleDateFormat::::" + currentDate + currentTime);
                addRatingReview(bottomSheetRatenowBinding.rating.getRating(), bottomSheetRatenowBinding.etShareThoughts.getText().toString().trim());
                GetPackageDetailsMockTestListRatingNow();
                bottomSheetDialog.cancel();
            }
        });
    }
}