package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.KeyboardUtils;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomsheetApplycouponBinding;
import com.dollop.exam101.databinding.BottomsheetReferralcodeBinding;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.activity.PaymentFailedActivity;
import com.dollop.exam101.main.activity.ThankYouPgActivity;
import com.dollop.exam101.main.adapter.MyCartAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.AppliedCouponModel;
import com.dollop.exam101.main.model.CartDatumModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements View.OnClickListener {
    FragmentCartBinding binding;
    Activity activity;
    String couponAndReferralCode = "";
    BottomSheetDialog bottomSheetApplyCoupon, bottomSheetDialogReferralCode;
    List<CartDatumModel> myCartModelArrayList = new ArrayList<>();
    ApiService apiService;
    BottomsheetApplycouponBinding bottomsheetApplycouponBinding;
    BottomsheetReferralcodeBinding bottomsheetReferralcodeBinding;
    AppliedCouponModel appliedCouponModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onResume() {
        super.onResume();
        if (AppController.getInstance().isOnline()) {
            Utils.E("couponAndReferralCode:::" + couponAndReferralCode);
            getCartItem();
        } else {
            InternetDialog();
        }
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.cvApplyCoupon.setOnClickListener(this);
        binding.cvReferralCode.setOnClickListener(this);
        binding.cvProceedToCheckOut.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvRemoveCoupon.setOnClickListener(this);
    }

    private void couponCodeBottomSheet() {
        bottomSheetApplyCoupon = new BottomSheetDialog(activity);
        bottomsheetApplycouponBinding = BottomsheetApplycouponBinding.inflate(getLayoutInflater());
        bottomSheetApplyCoupon.setContentView(bottomsheetApplycouponBinding.getRoot());
        bottomSheetApplyCoupon.show();
        bottomsheetApplycouponBinding.llParent.setOnClickListener(KeyboardUtils::hideKeyboard);
        bottomsheetApplycouponBinding.etApplyCouponId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bottomsheetApplycouponBinding.tvErrorCoupon.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bottomsheetApplycouponBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {

                if (bottomsheetApplycouponBinding.etApplyCouponId.getText().toString().isEmpty()) {
                    bottomsheetApplycouponBinding.tvErrorCoupon.setVisibility(View.VISIBLE);
                    bottomsheetApplycouponBinding.tvErrorCoupon.setText(R.string.empty_error);
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_bottom);
                    bottomsheetApplycouponBinding.tvErrorCoupon.startAnimation(animation);
                    KeyboardUtils.showKeyboard(activity);
                    bottomsheetApplycouponBinding.etApplyCouponId.requestFocus();

                } else {
                    if (AppController.getInstance().isOnline()) {
                        couponAndReferralCode = bottomsheetApplycouponBinding.etApplyCouponId.getText().toString().trim();
                        applyCouponCode();
                        bottomSheetApplyCoupon.dismiss();
                    } else {
                        InternetDialog();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.cvApplyCoupon) {
            couponCodeBottomSheet();
        } else if (view == binding.cvReferralCode) {
            referralCodeBottomSheet();
        } else if (view == binding.cvProceedToCheckOut) {
            if (AppController.getInstance().isOnline()) {
                purchasePackage();
            } else {
                InternetDialog();
            }
        } else if (view == binding.ivBack) {
            binding.llToolbar.setVisibility(View.GONE);
            ((DashboardScreenActivity) activity).navController.popBackStack();
        } else if (view == binding.tvRemoveCoupon){
            binding.view.setVisibility(View.VISIBLE);
            binding.llCouponReferral.setVisibility(View.VISIBLE);
            binding.tvCouponHeading.setVisibility(View.GONE);
            binding.CardViewCoupon.setVisibility(View.GONE);
            binding.tvCouponDiscount.setText("00");
            getCartItem();
        }
    }

    private void referralCodeBottomSheet() {
        bottomSheetDialogReferralCode = new BottomSheetDialog(activity);
        bottomsheetReferralcodeBinding = BottomsheetReferralcodeBinding.inflate(getLayoutInflater());
        bottomSheetDialogReferralCode.setContentView(bottomsheetReferralcodeBinding.getRoot());
        bottomSheetDialogReferralCode.show();
        bottomsheetReferralcodeBinding.llParent.setOnClickListener(KeyboardUtils::hideKeyboard);


        bottomsheetReferralcodeBinding.etReferralCodeId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bottomsheetReferralcodeBinding.tvErrorReferral.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bottomsheetReferralcodeBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (bottomsheetReferralcodeBinding.etReferralCodeId.getText().toString().isEmpty()) {
                    bottomsheetReferralcodeBinding.tvErrorReferral.setVisibility(View.VISIBLE);
                    bottomsheetReferralcodeBinding.tvErrorReferral.setText(R.string.empty_error);
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_bottom);
                    bottomsheetReferralcodeBinding.tvErrorReferral.startAnimation(animation);
                    KeyboardUtils.showKeyboard(activity);
                    bottomsheetReferralcodeBinding.etReferralCodeId.requestFocus();
                } else {
                    if (AppController.getInstance().isOnline()) {
                        couponAndReferralCode = bottomsheetReferralcodeBinding.etReferralCodeId.getText().toString().trim();
                        applyCouponCode();
                        bottomSheetDialogReferralCode.dismiss();
                    } else {
                        InternetDialog();
                    }
                }
            }

        });
    }

    private void applyCouponCode() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.ApplyCouponCode(Utils.GetSession().token, couponAndReferralCode).
                enqueue(new Callback<AllResponseModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                        progressDialog.dismiss();
                        if (response.code() == StatusCodeConstant.OK) {
                            assert response.body() != null;
                            appliedCouponModel = response.body().appliedCouponModel;
                            if (response.body().message.equals(Constants.Key.Success)) {
                                Utils.T(activity, response.body().message);
                                binding.view.setVisibility(View.GONE);
                                binding.llCouponReferral.setVisibility(View.GONE);
                                binding.tvCouponHeading.setVisibility(View.VISIBLE);
                                binding.CardViewCoupon.setVisibility(View.VISIBLE);
                                binding.rlCouponDiscount.setVisibility(View.VISIBLE);
                                binding.tvCoupon.setText(couponAndReferralCode);
                                binding.tvSgst.setText(new DecimalFormat("##.##").format(Double.parseDouble(appliedCouponModel.gstPercentage)) + "%");
                                binding.tvCouponDiscount.setText(new DecimalFormat("##.##").format(Double.parseDouble(appliedCouponModel.discountedAmt)));
                                binding.tvSubTotalId.setText(String.valueOf(new DecimalFormat("##.##").format(appliedCouponModel.subTotalAmt)));
                                binding.tvGrandtotal.setText(String.valueOf(new DecimalFormat("##.##").format(appliedCouponModel.grandTotalAmt)));
                                binding.tvGrandTotalBottom.setText(String.valueOf(new DecimalFormat("##.##").format(appliedCouponModel.grandTotalAmt)));
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
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                        call.cancel();
                        t.printStackTrace();
                        progressDialog.dismiss();
                    }
                });

    }

    public void getCartItem() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getCartList(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                if (response.code() == StatusCodeConstant.OK) {
                    assert response.body() != null;
                    if (!response.body().cartData.isEmpty()) {
                        binding.rlBottom.setVisibility(View.VISIBLE);
                        binding.scrollCartItem.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParentEmpty.setVisibility(View.GONE);
                        binding.tvSubTotalId.setText(new DecimalFormat("##.##").format(Double.parseDouble(response.body().subTotalAmt)));
                        binding.tvSgst.setText(new DecimalFormat("##.##").format(Double.parseDouble(response.body().gstPercentage)) + "%");
                        binding.tvGrandtotal.setText(String.valueOf(new DecimalFormat("##.##").format(response.body().grandTotalAmt)));
                        binding.tvGrandTotalBottom.setText(String.valueOf(new DecimalFormat("##.##").format(response.body().grandTotalAmt)));
                        myCartModelArrayList.clear();
                        myCartModelArrayList.addAll(response.body().cartData);
                        binding.rvPackageListId.setLayoutManager(new LinearLayoutManager(activity));
                        binding.rvPackageListId.setAdapter(new MyCartAdapter(activity, myCartModelArrayList, CartFragment.this));
                    } else {
                        binding.rlBottom.setVisibility(View.GONE);
                        binding.scrollCartItem.setVisibility(View.GONE);
                        binding.noResultFoundId.llParentEmpty.setVisibility(View.VISIBLE);
                    }
                } else {
                    assert response.errorBody() != null;
                    if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Utils.T(activity, message.message);
                    } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        binding.rlBottom.setVisibility(View.GONE);
        binding.scrollCartItem.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                getCartItem();
                binding.rlBottom.setVisibility(View.VISIBLE);
                binding.scrollCartItem.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);

            }
        });
    }

    public void addToWishList(String packageUuid) {
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
                        Utils.T(activity, "Added to WishList Successfully");
                        getCartItem();
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

    private void purchasePackage() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.couponCode,couponAndReferralCode);
        hm.put(Constants.Key.transactionId, "xyz");
        hm.put(Constants.Key.paymentMode, Constants.Key.Online);
        apiService.purchasePackage(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, response.body().message);
                        Utils.I(activity, ThankYouPgActivity.class, null);
                    } else {
                        assert response.errorBody() != null;
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(activity, message.message);
                            Utils.I(activity, PaymentFailedActivity.class, null);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
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

    public void removeFromWishList(String wishListUuid) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.removeFromWishList(Utils.GetSession().token, wishListUuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        Utils.T(activity, getString(R.string.removed_from_wishlist_successfully));
                        getCartItem();
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
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
}