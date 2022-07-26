package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    Activity activity;
    ActivityForgotPasswordBinding binding;
    ApiService apiService;
    List<ValidationModel> errorValidationModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ForgotPasswordActivity.this;
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.tvBtnSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == binding.tvBtnSubmit) {
            CheckValidationTask();
        }
    }

    private void CheckValidationTask() {
        errorValidationModels.clear();
        errorValidationModels.add(new ValidationModel(Validation.Type.Empty, binding.etEmail));
        errorValidationModels.add(new ValidationModel(Validation.Type.Email, binding.etEmail));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, errorValidationModels);
        if (resultReturn.aBoolean) {
            ForgetPassword();
        } else {
            // resultReturn.errorTextView.setVisibility(View.VISIBLE);
            if (resultReturn.type == Validation.Type.EmptyString) {

                //  resultReturn.errorTextView.setText(resultReturn.errorMessage);
                Toast.makeText(this, resultReturn.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                //   resultReturn.errorTextView.setText(validation.errorMessage);
                validation.EditTextPointer.setError(validation.errorMessage);
                validation.EditTextPointer.requestFocus();
            }

        }
    }

    private void ForgetPassword() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.studentEmail, binding.etEmail.getText().toString().trim());
        apiService.ForgetPassword(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                    progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        Bundle bundle = new Bundle();
                        assert response.body() != null;
                        Utils.I(activity, CheckEmailActivity.class, bundle);
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

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                /*final boolean globalVisibleRect;
                globalVisibleRect = v.getGlobalVisibleRect(outRect);*/
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