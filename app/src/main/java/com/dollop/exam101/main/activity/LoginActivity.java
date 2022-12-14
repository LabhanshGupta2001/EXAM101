package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.Database.UserData;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityLoginBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 100;
    Activity activity;
    ActivityLoginBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    ApiService apiservice;
    String personName, personEmail;
    List<ValidationModel> errorValidationModels = new ArrayList<>();
    Boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // anil sir 15-07
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = LoginActivity.this;
        init();
    }

    private void init() {
        edittextValidation();
        apiservice = RetrofitClient.getClient();
        binding.tvForgetPasswordId.setOnClickListener(this);
        binding.SignInId.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.ivShowHidePassword.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        binding.llLoginWithGoogle.setOnClickListener(v -> signIn());
    }

    private void edittextValidation() {
        binding.etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorPass.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // startActivity(new Intent(activity, DashboardScreenActivity.class));
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                personName = acct.getDisplayName();
                personEmail = acct.getEmail();
            }
            if (AppController.getInstance().isOnline()) {
                SocialLogin();
            } else {
                InternetDialog();
            }
        } catch (ApiException e) {
            Log.e("massage", e.toString());
        }

    }


    @Override
    public void onClick(View view) {
        if (view == binding.tvForgetPasswordId) {
            Utils.I(activity, ForgotPasswordActivity.class, null);
        } else if (view == binding.SignInId) {
            if (AppController.getInstance().isOnline()) {
                CheckValidationTask();
            } else {
                InternetDialog();
            }
        } else if (view == binding.tvSignUp) {
            Utils.I(activity, SignUpActivity.class, null);
        } else if (view == binding.ivShowHidePassword) {
            isClicked = !isClicked;
            if (isClicked) {
                binding.ivShowHidePassword.setImageResource(R.drawable.ic_hide);
                binding.etUserPassword.setTransformationMethod
                        (HideReturnsTransformationMethod.getInstance());
                binding.etUserPassword.setSelection(binding.etUserPassword.length());
            } else {
                binding.ivShowHidePassword.setImageResource(R.drawable.ic_visibility);
                binding.etUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etUserPassword.setSelection(binding.etUserPassword.length());
            }
        }
    }



    private void CheckValidationTask() {
        errorValidationModels.clear();
        errorValidationModels.add(new ValidationModel(Validation.Type.Email, binding.etUserEmail, binding.tvErrorEmail));
        errorValidationModels.add(new ValidationModel(Validation.Type.Empty, binding.etUserPassword, binding.tvErrorPass));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, errorValidationModels);
        if (resultReturn.aBoolean) {
            userLogin();
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

    void userLogin() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        String fcmid = "";
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.studentEmail, binding.etUserEmail.getText().toString().trim());
        hm.put(Constants.Key.password, binding.etUserPassword.getText().toString().trim());
        hm.put(Constants.Key.fcmId, fcmid);
        apiservice.userLogin(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        Bundle bundle = new Bundle();
                        assert response.body() != null;
                        UserData userModel = response.body().userData;
                        UserDataHelper.getInstance().insertData(userModel);
                        Utils.I_clear(activity, DashboardScreenActivity.class, bundle);
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

    void SocialLogin() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        mGoogleSignInClient.signOut();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.Key.studentName, personName);
        hashMap.put(Constants.Key.studentEmail, personEmail);
        hashMap.put(Constants.Key.loginType, Constants.Key.Google);
        hashMap.put(Constants.Key.fcmId, "asdasdasdsad");
        apiservice.SocialLogin(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        UserDataHelper.getInstance().insertData(response.body().User);
                        Utils.I_clear(activity, DashboardScreenActivity.class, null);

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.alert(activity, message.message);
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
