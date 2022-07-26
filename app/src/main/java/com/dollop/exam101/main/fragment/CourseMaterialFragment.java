package com.dollop.exam101.main.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.main.adapter.OverviewCourseDetailsAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.ReviewRatingModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentCourseMaterialBinding binding;
    String packageId;
    ArrayList<ReviewRatingModel> reviewRatingModels = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    BottomSheetDialog bottomSheetDialog;

    public CourseMaterialFragment(String packageId) {
        Utils.E("PackageId:::" + packageId);
        this.packageId = packageId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseMaterialBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.tvRateId.setOnClickListener(this);
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvFirst.setNestedScrollingEnabled(false);
        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFirst.setAdapter(new PakageDetailPrimaryAdapter(getContext(), list));

        binding.rvOverView.setNestedScrollingEnabled(false);
        binding.rvOverView.setHasFixedSize(true);
        binding.rvOverView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvOverView.setAdapter(new OverviewCourseDetailsAdapter(getContext(), list));

        GetPackageDetailsMockTestListRatingNow();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvRateId) {
            rateNowBottomSheet();
        }
    }

    private void rateNowBottomSheet() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        BottomSheetRatenowBinding bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetRatenowBinding.tvRateNow.setOnClickListener(view ->
                addRatingReview(bottomSheetRatenowBinding.rating.getRating(), bottomSheetRatenowBinding.etShareThoughts.getText().toString()));
    }


    private void GetPackageDetailsMockTestListRatingNow() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getPackageDetailsMockTestListRatingNow(Utils.GetSession().token, packageId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        reviewRatingModels.clear();
                        assert response.body() != null;
                        reviewRatingModels.addAll(response.body().reviewRatingModel);
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
        hm.put(Constants.Key.packageId, packageId);
        hm.put(Constants.Key.rating, String.valueOf(rating));
        hm.put(Constants.Key.review, review);
        apiService.addRatingReview(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        bottomSheetDialog.dismiss();
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

}