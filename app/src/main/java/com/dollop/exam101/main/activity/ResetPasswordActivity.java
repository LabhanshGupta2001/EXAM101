package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityResetPasswordBinding;
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

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    Activity activity;
    ActivityResetPasswordBinding binding;
    List<ValidationModel> validationModels = new ArrayList<>();
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ResetPasswordActivity.this;
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ChangePasswordId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==binding.ChangePasswordId){
            CheckValidationTask();
        }

    }


    private void CheckValidationTask() {
        validationModels.clear();
        validationModels.add(new ValidationModel(Validation.Type.PasswordStrong, binding.etNewPassword,binding.tvErrorNewPass));
        validationModels.add(new ValidationModel(Validation.Type.PasswordMatch, binding.etNewPassword, binding.etConfirmPassword,binding.tvErrorConfirmPass));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, validationModels);
        if (resultReturn.aBoolean) {
            Utils.I(ResetPasswordActivity.this, LoginActivity.class, null);
            //resetPassword();
        } else {
            resultReturn.errorTextView.setVisibility(View.VISIBLE);
            if (resultReturn.type == Validation.Type.EmptyString) {
                resultReturn.errorTextView.setText(resultReturn.errorMessage);
            } else {
                resultReturn.errorTextView.setText(validation.errorMessage);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_to_bottom);
                resultReturn.errorTextView.startAnimation(animation);
                validation.EditTextPointer.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(validation.EditTextPointer, InputMethodManager.SHOW_IMPLICIT);
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