package com.dollop.exam101.main.activity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityAffilationBankDetailsBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AffilationBankDetailsActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = AffilationBankDetailsActivity.this;
    ActivityAffilationBankDetailsBinding binding;
    ApiService apiService;
    List<ValidationModel> validationModels = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffilationBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        edittextValidation();
        apiService = RetrofitClient.getClient();
        binding.llSubmit.setOnClickListener(this);
        binding.etHolderName.setOnClickListener(this);
        binding.etAccountNumber.setOnClickListener(this);
        binding.etIfscCode.setOnClickListener(this);
        binding.etBranch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        if (AppController.getInstance().isOnline()) {

        } else {
            // Utils.InternetDialog(activity);
        }
    }

    private void GetUserBankProfile() {
        apiService.getBankProfile("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void edittextValidation() {
        binding.etHolderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorName.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorAccNumber.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etIfscCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorIFSC.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etSelectType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorType.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorBranch.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void BankUserDetails() {
        HashMap<String, String> hashMap = new HashMap<>();
        apiService.UserBankDetails(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.llSubmit) {
            CheckValidationTask();
        } else if (view == binding.ivBack) {
            onBackPressed();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void CheckValidationTask() {
        validationModels.clear();
        validationModels.add(new ValidationModel(Validation.Type.Empty, binding.etHolderName, binding.tvErrorName));
        validationModels.add(new ValidationModel(Validation.Type.AccountNumber, binding.etAccountNumber, binding.tvErrorAccNumber));
        validationModels.add(new ValidationModel(Validation.Type.IFSC, binding.etIfscCode, binding.tvErrorIFSC));
        validationModels.add(new ValidationModel(Validation.Type.Empty, binding.etSelectType, binding.tvErrorType));
        validationModels.add(new ValidationModel(Validation.Type.Empty, binding.etBranch, binding.tvErrorBranch));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, validationModels);
        if (resultReturn.aBoolean) {
            if (AppController.getInstance().isOnline()) {
                SavedData.SaveBankStatus(Constants.Key.pending);
                finish();
            } else {

            }
        } else {
            resultReturn.errorTextView.setVisibility(View.VISIBLE);
            if (resultReturn.type == Validation.Type.EmptyString) {
                resultReturn.errorTextView.setText(resultReturn.errorMessage);
            } else {
                resultReturn.errorTextView.setText(validation.errorMessage);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_to_bottom);
                resultReturn.errorTextView.startAnimation(animation);
                validation.EditTextPointer.requestFocus();
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                final boolean globalVisibleRect;
                globalVisibleRect = v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}