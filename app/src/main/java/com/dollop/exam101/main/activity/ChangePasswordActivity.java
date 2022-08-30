package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityChangePasswordBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = ChangePasswordActivity.this;
    ActivityChangePasswordBinding binding;
    ApiService apiService;
    List<ValidationModel> allResponseModels = new ArrayList<>();
    String Token;
    Boolean isClicked = false;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        Token = Utils.GetSession().token;
        apiService = RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
        binding.ivShowHidePasswordConfirm.setOnClickListener(this);
        binding.ivShowHidePasswordNew.setOnClickListener(this);
        binding.ivShowHidePasswordCurrent.setOnClickListener(this);
        binding.llSavePassword.setOnClickListener(this);
        binding.etConfirmNewPassword.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
        binding.etCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorCurrentPass.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorNewPass.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorConfirmPass.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void ChangePassword() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.newPassword, binding.etNewPassword.getText().toString().trim());
        hm.put(Constants.Key.confirmPassword, binding.etConfirmNewPassword.getText().toString().trim());
        hm.put(Constants.Key.oldPassword, binding.etCurrentPassword.getText().toString().trim());
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.ChangePassword(Token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.I(activity, SettingActivity.class, null);
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
    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.llSavePassword) {
            if (AppController.getInstance().isOnline()) {
                CheckValidationTask();
            } else {
                InternetDialog();
            }
        } else if (view == binding.ivShowHidePasswordCurrent) {
            isClicked = !isClicked;
            if (isClicked) {
                binding.ivShowHidePasswordCurrent.setImageResource(R.drawable.ic_hide);

                binding.etCurrentPassword.setTransformationMethod
                        (HideReturnsTransformationMethod.getInstance());
                binding.etCurrentPassword.setSelection(binding.etCurrentPassword.length());
                binding.etCurrentPassword.requestFocus();


            } else {
                binding.ivShowHidePasswordCurrent.setImageResource(R.drawable.ic_visibility);

                binding.etCurrentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etCurrentPassword.setSelection(binding.etCurrentPassword.length());
                binding.etCurrentPassword.requestFocus();


            }
        } else if (view == binding.ivShowHidePasswordNew) {
            isClicked = !isClicked;
            if (isClicked) {
                binding.ivShowHidePasswordNew.setImageResource(R.drawable.ic_hide);

                binding.etNewPassword.setTransformationMethod
                        (HideReturnsTransformationMethod.getInstance());
                binding.etNewPassword.setSelection(binding.etNewPassword.length());
                binding.etNewPassword.requestFocus();


            } else {
                binding.ivShowHidePasswordNew.setImageResource(R.drawable.ic_visibility);

                binding.etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etNewPassword.setSelection(binding.etNewPassword.length());
                binding.etNewPassword.requestFocus();

            }
        } else if (view == binding.ivShowHidePasswordConfirm) {
            isClicked = !isClicked;
            if (isClicked) {
                binding.ivShowHidePasswordConfirm.setImageResource(R.drawable.ic_hide);

                binding.etConfirmNewPassword.setTransformationMethod
                        (HideReturnsTransformationMethod.getInstance());
                binding.etConfirmNewPassword.setSelection(binding.etConfirmNewPassword.length());
                binding.etConfirmNewPassword.requestFocus();


            } else {
                binding.ivShowHidePasswordConfirm.setImageResource(R.drawable.ic_visibility);

                binding.etConfirmNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etConfirmNewPassword.setSelection(binding.etConfirmNewPassword.length());
                binding.etConfirmNewPassword.requestFocus();

            }
        }

    }

    private void CheckValidationTask() {
        allResponseModels.clear();
        allResponseModels.add(new ValidationModel(Validation.Type.Empty, binding.etCurrentPassword, binding.tvErrorCurrentPass));
        allResponseModels.add(new ValidationModel(Validation.Type.PasswordStrong, binding.etNewPassword, binding.tvErrorNewPass));
        allResponseModels.add(new ValidationModel(Validation.Type.PasswordMatch, binding.etNewPassword, binding.etConfirmNewPassword, binding.tvErrorConfirmPass));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, allResponseModels);
        if (resultReturn.aBoolean) {
            ChangePassword();
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

    @SuppressLint("NewApi")
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
}