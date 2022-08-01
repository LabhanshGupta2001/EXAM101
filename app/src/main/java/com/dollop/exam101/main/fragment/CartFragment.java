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
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.BottomsheetApplycouponBinding;
import com.dollop.exam101.databinding.BottomsheetReferralcodeBinding;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.activity.PaymentFailedActivity;
import com.dollop.exam101.main.adapter.MyCartAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CartDatumModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements View.OnClickListener {
    FragmentCartBinding binding;
    Activity activity;
    BottomSheetDialog bottomSheetApplyCoupon, bottomSheetDialogReferralCode;
    List<CartDatumModel> myCartModelArrayList = new ArrayList<>();
    ApiService apiService;
    BottomsheetApplycouponBinding bottomsheetApplycouponBinding;
    BottomsheetReferralcodeBinding bottomsheetReferralcodeBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCartItem();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.CardView.setOnClickListener(this);
        binding.CardViewOne.setOnClickListener(this);
        binding.tvButtonCheckoutId.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    private void couponCodeBottomSheet() {
        bottomSheetApplyCoupon = new BottomSheetDialog(activity);
        bottomsheetApplycouponBinding = BottomsheetApplycouponBinding.inflate(getLayoutInflater());
        bottomSheetApplyCoupon.setContentView(bottomsheetApplycouponBinding.getRoot());
        bottomSheetApplyCoupon.show();
        bottomsheetApplycouponBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomsheetApplycouponBinding.etApplyCouponId.getText().toString().isEmpty()) {
                    bottomsheetApplycouponBinding.etApplyCouponId.setError("Enter CouponCode");
                } else {
                    applyCouponCode();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.CardView) {
            couponCodeBottomSheet();
        } else if (view == binding.CardViewOne) {
            bottomSheetDialogReferralCode = new BottomSheetDialog(activity);
            bottomsheetReferralcodeBinding = BottomsheetReferralcodeBinding.inflate(getLayoutInflater());
            bottomSheetDialogReferralCode.setContentView(bottomsheetReferralcodeBinding.getRoot());
            bottomSheetDialogReferralCode.show();
            bottomsheetReferralcodeBinding.tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialogReferralCode.cancel();
                }
            });
        } else if (view == binding.tvButtonCheckoutId) {
            Utils.I(activity, PaymentFailedActivity.class, null);
        } else if (view == binding.ivBack) {
            ((DashboardScreenActivity) activity).navController.popBackStack();
        }
    }

    private void applyCouponCode() {
        apiService.ApplyCouponCode(Utils.GetSession().token, bottomsheetApplycouponBinding.etApplyCouponId.getText().toString()).
                enqueue(new Callback<AllResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                        if (response.code() == StatusCodeConstant.OK) {
                            assert response.body() != null;
                            Utils.T(activity, response.body().message);
                            bottomSheetApplyCoupon.dismiss();
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
                        t.printStackTrace();

                    }
                });

    }

    public void getCartItem() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getCartList(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                if (response.code() == StatusCodeConstant.OK) {
                    assert response.body() != null;
                    if (!response.body().cartData.isEmpty()) {
                        binding.rlBottom.setVisibility(View.VISIBLE);
                        binding.scrollCartItem.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                        binding.tvSubTotalId.setText(response.body().gstPercentage);
                        binding.tvSgst.setText(response.body().subTotalAmt);
                        binding.tvGrandtotal.setText(String.valueOf(response.body().grandTotalAmt));
                        binding.tvGrandTotalBottom.setText(String.valueOf(response.body().grandTotalAmt));
                        myCartModelArrayList.addAll(response.body().cartData);
                        binding.rvPackageListId.setLayoutManager(new LinearLayoutManager(activity));
                        binding.rvPackageListId.setAdapter(new MyCartAdapter(activity, myCartModelArrayList, CartFragment.this));
                    } else {
                        binding.rlBottom.setVisibility(View.GONE);
                        binding.scrollCartItem.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    }


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

    public void removeFromCart(String cartUuId) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.removeFromCart(Utils.GetSession().token, cartUuId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                if (response.code() == StatusCodeConstant.OK) {
                    assert response.body() != null;
                    Utils.T(activity, response.body().message);
                    getCartItem();

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
            public void onFailure(Call<AllResponseModel> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });

    }
}