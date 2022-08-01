package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityMyCartBinding;
import com.dollop.exam101.databinding.BottomsheetApplycouponBinding;
import com.dollop.exam101.databinding.BottomsheetReferralcodeBinding;
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

public class MyCartActivity extends BaseActivity implements View.OnClickListener {
    Activity activity;
    ActivityMyCartBinding binding;
    BottomSheetDialog bottomSheetApplyCoupon, bottomSheetDialogReferralCode;
    List<CartDatumModel> myCartModelArrayList = new ArrayList<>();
    ApiService apiService;
    BottomsheetApplycouponBinding bottomsheetApplycouponBinding;
    BottomsheetReferralcodeBinding bottomsheetReferralcodeBinding;
    String subTotal,SGST;
    Integer GrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getCartItem();
    }


    private void init() {
        activity = MyCartActivity.this;
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
            onBackPressed();
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


     private  void getCartItem() {
         Dialog progressDialog=Utils.initProgressDialog(activity);
        apiService.getCartList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoiZ2VldEBnbWFpbC5jb20iLCJ1c2VyX2lkIjoiYWQ5YzhhNzQtMGNmMi0xMWVkLTk3NTQtMDAwYzI5MTE1MWViIiwicm9sZSI6IlN0dWRlbnQiLCJBUElfVElNRSI6MTY1OTA5MDE4Nn0.QCvlCA6xQdHknPfcTHC5tmXZo6j60bhtJb2uvg5R6pU").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        SGST=response.body().subTotalAmt;
                        subTotal=response.body().gstPercentage;
                        GrandTotal=response.body().grandTotalAmt;

                        Utils.E("subTotal;;;;;;;;;"+subTotal);
                        Utils.E("SGST;;;;;;;"+SGST);
                        Utils.E("GrandTotal;;;;;"+GrandTotal);
                        binding.tvSubTotalId.setText(subTotal);
                        binding.tvSgst.setText(SGST);
                        binding.tvGrandtotal.setText(GrandTotal);
                        binding.tvGrandTotalBottom.setText(GrandTotal);

                        Utils.E("Cartdata;;;;;"+response.body().cartData);
                        myCartModelArrayList.addAll(response.body().cartData);
                        Utils.E("Cartdata;;;;;"+response.body().cartData);
                        binding.rvPackageListId.setHasFixedSize(true);
                        binding.rvPackageListId.setLayoutManager(new LinearLayoutManager(activity));
                        binding.rvPackageListId.setAdapter(new MyCartAdapter(activity, myCartModelArrayList));
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
