package com.dollop.exam101.main.activity;

import android.app.Activity;
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
import com.dollop.exam101.main.model.MyCartModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends BaseActivity implements View.OnClickListener {
    Activity activity;
    ActivityMyCartBinding binding;
    BottomSheetDialog bottomSheetApplyCoupon, bottomSheetDialogReferralCode;
    ArrayList<MyCartModel> myCartModelArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ApiService apiService;
    BottomsheetApplycouponBinding bottomsheetApplycouponBinding;
    BottomsheetReferralcodeBinding bottomsheetReferralcodeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        MyCartModel myCartModel = new MyCartModel();
        MyCartModel myCartModel1 = new MyCartModel();
        MyCartModel myCartModel2 = new MyCartModel();


        myCartModel.Point = "Microsoft power Point";
        myCartModel.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel.Rupees = "₹500";
        myCartModel.Wishlist = "Add to Wishlist";
        myCartModel.Remove = "Remove";


        myCartModel1.Point = "Microsoft power Point";
        myCartModel1.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel1.Rupees = "₹500";
        myCartModel1.Wishlist = "Add to Wishlist";
        myCartModel1.Remove = "Remove";


        myCartModel2.Point = "Microsoft power Point";
        myCartModel2.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel2.Rupees = "₹500";
        myCartModel2.Wishlist = "Add to Wishlist";
        myCartModel2.Remove = "Remove";

        myCartModelArrayList.clear();
        myCartModelArrayList.add(myCartModel);
        myCartModelArrayList.add(myCartModel1);
        myCartModelArrayList.add(myCartModel2);

        binding.rvPackageListId.setHasFixedSize(true);
        binding.rvPackageListId.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvPackageListId.setAdapter(new MyCartAdapter(activity, myCartModelArrayList));
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


    void getCartItem() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getCartList(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}
