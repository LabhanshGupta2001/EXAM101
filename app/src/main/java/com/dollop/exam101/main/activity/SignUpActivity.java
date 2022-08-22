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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.KeyboardUtils;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivitySignUpBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomSheetCountryBinding;
import com.dollop.exam101.databinding.BottomSheetStateBinding;
import com.dollop.exam101.main.adapter.CountryAdapter;
import com.dollop.exam101.main.adapter.StateAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CountryModel;
import com.dollop.exam101.main.model.StateModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 100;
    private final ArrayList<CountryModel> contryItemArrayList = new ArrayList<>();
    private final ArrayList<StateModel> stateItemArrayList = new ArrayList<>();
    String selectedCountryId = "", selectedCountryCode, selectedCountryName, selectedState, selectedCountryFlag;
    String personName, personEmail;
    Activity activity = SignUpActivity.this;
    ActivitySignUpBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    BottomSheetDialog bottomSheetDialog, bottomSheetStateDialog;
    BottomSheetCountryBinding bottomSheetCountryBinding;
    BottomSheetStateBinding bottomSheetStateBinding;
    List<ValidationModel> errorValidationModels = new ArrayList<>();
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private ApiService apiservice;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiservice = RetrofitClient.getClient();
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        if (AppController.getInstance().isOnline()) {
            getCountryList();
        } else {
            //Utils.InternetDialog(activity);
            InternetDialog();
        }
        edittextValidation();
        //binding.tvCountryCodeId.setText(Utils.getDefaultCountryCode(activity).CountryCode);
        binding.tvCountryCodeId.setText(Constants.Key.Default_Country_Code);
        binding.SignUPId.setOnClickListener(this);
        binding.tvRegisterId.setOnClickListener(this);
        binding.llLoginWithGoogle.setOnClickListener(this);
        binding.llSignUp.setOnClickListener(this);
        binding.tvSelectCountry.setOnClickListener(this);
        binding.tvSelectState.setOnClickListener(this);
        binding.llCountryCode.setOnClickListener(this);
        binding.tvRegisterId.setOnClickListener(this);
        binding.tvSelectCountry.setText(Constants.Key.India);

        binding.etEnterMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.mcvMobile.setStrokeColor(ContextCompat.getColor(activity, R.color.theme));
                } else
                    binding.mcvMobile.setStrokeColor(ContextCompat.getColor(activity, R.color.HorizontallineColor));
            }
        });

        // countryAdapter = new CountryAdapter(activity, contryItemArrayList);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.


        binding.llLoginWithGoogle.setOnClickListener(v -> signIn());
    }

    private void edittextValidation() {
        binding.etUserName.addTextChangedListener(new TextWatcher() {
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
        binding.etEnterMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorMobile.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
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
        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                personName = acct.getDisplayName();
                personEmail = acct.getEmail();
                //Uri personPhoto = acct.getPhotoUrl();
            }
            if (AppController.getInstance().isOnline()) {

                SocialLogin();
            } else {
                // Utils.InternetDialog(activity);
                InternetDialog();
            }
            // startActivity(new Intent(activity, DashboardScreenActivity.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(Constants.Key.Massage, e.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.SignUPId) {
            Utils.I_clear(SignUpActivity.this, LoginActivity.class, null);
        } else if (view == binding.tvSelectState) {
            binding.tvErrorState.setVisibility(View.GONE);
            bottomSheetStateTask();
        } else if (view == binding.tvRegisterId) {
            CheckValidationTask();
        }/*else if (view == binding.llCountryCode) {
            bottomSheetCountryTask(Constants.Key.CountryId_Show);
        } else if (view == binding.tvSelectCountry) {
            binding.tvErrorCountry.setVisibility(View.GONE);
            bottomSheetCountryTask(Constants.Key.Country_Code_Nan);
        } */
    }

    private void getState(String countryId) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiservice.getStateList(countryId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        stateItemArrayList.clear();
                        assert response.body() != null;
                        stateItemArrayList.addAll(response.body().state);
                        stateAdapter = new StateAdapter(activity, stateItemArrayList, Constants.Key.ClickSign);
                        bottomSheetStateBinding.rvStateListId.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
                        bottomSheetStateBinding.rvStateListId.setAdapter(stateAdapter);
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

    private void getCountryList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiservice.getCountryList().enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        contryItemArrayList.clear();
                        contryItemArrayList.addAll(response.body().country);

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
    private void CheckValidationTask() {
        errorValidationModels.clear();
        errorValidationModels.add(new ValidationModel(Validation.Type.Empty, binding.etUserName, binding.tvErrorName));
        errorValidationModels.add(new ValidationModel(Validation.Type.Email, binding.etUserEmail, binding.tvErrorEmail));
        errorValidationModels.add(new ValidationModel(Validation.Type.Phone, binding.etEnterMobile, binding.tvErrorMobile));
        errorValidationModels.add(new ValidationModel(Validation.Type.PasswordStrong, binding.etPassword, binding.etConfirmPassword, binding.tvErrorPass));
        errorValidationModels.add(new ValidationModel(Validation.Type.PasswordMatch, binding.etPassword, binding.etConfirmPassword, binding.tvErrorConfirmPass));
        errorValidationModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCountry.getText().toString(), getString(R.string.please_select_the_country), binding.tvErrorCountry));
        errorValidationModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectState.getText().toString(), getString(R.string.please_select_the_state), binding.tvErrorState));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, errorValidationModels);
        if (resultReturn.aBoolean) {
            if (AppController.getInstance().isOnline()) {
                userSignup();
            } else {
                InternetDialog();
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(validation.EditTextPointer, InputMethodManager.SHOW_IMPLICIT);
            }

        }

    }

    private void bottomSheetCountryTask(String countryKey) {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetCountryBinding = BottomSheetCountryBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetCountryBinding.getRoot());

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetCountryBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setSkipCollapsed(true);

        countryAdapter = new CountryAdapter(activity, contryItemArrayList, countryKey, Constants.Key.Login);
        bottomSheetCountryBinding.rvCountryListId.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        bottomSheetCountryBinding.rvCountryListId.setAdapter(countryAdapter);
        bottomSheetCountryBinding.searchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryAdapter.getFilter().filter(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.getFilter().filter(newText.trim());
                return false;
            }
        });
        bottomSheetDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void bottomSheetStateTask() {
        bottomSheetStateDialog = new BottomSheetDialog(activity);
        bottomSheetStateBinding = BottomSheetStateBinding.inflate(getLayoutInflater());
        bottomSheetStateDialog.setContentView(bottomSheetStateBinding.getRoot());
        bottomSheetStateBinding.llParent.setOnClickListener(KeyboardUtils::hideKeyboard);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetStateBinding.getRoot().getParent()));
        bottomSheetStateDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setSkipCollapsed(true);
        if (AppController.getInstance().isOnline()) {
            getState(Constants.Key.DefaultCountryUuId);
        } else {
            // Utils.InternetDialog(activity);
            InternetDialog();
        }
        bottomSheetStateBinding.searchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                stateAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stateAdapter.getFilter().filter(newText);
                return false;
            }
        });

        bottomSheetStateDialog.show();

    }

    void userSignup() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        String fcmid = "";
        hm.put(Constants.Key.studentName, binding.etUserName.getText().toString().trim());
        hm.put(Constants.Key.studentEmail, binding.etUserEmail.getText().toString().trim());
        hm.put(Constants.Key.password, binding.etPassword.getText().toString().trim());
        hm.put(Constants.Key.studentMobileNo, binding.etEnterMobile.getText().toString().trim());
        hm.put(Constants.Key.countryCode, binding.tvCountryCodeId.getText().toString().trim());
        hm.put(Constants.Key.countryName, binding.tvSelectCountry.getText().toString().trim());
        hm.put(Constants.Key.stateName, selectedState);
        hm.put(Constants.Key.fcmId, fcmid);

        apiservice.userSignup(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        Bundle bundle = new Bundle();
                        assert response.body() != null;
                        Utils.T(activity,response.body().message);
                        Utils.I_clear(activity, LoginActivity.class, bundle);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.UnAuthorizationToken(activity);
                            }
                        } else {
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

    public void onCountrySelected(String countryId, String CountryName /* String countryCode, String flag*/) {
        this.selectedCountryId = countryId;
        this.selectedCountryName = CountryName;
       /* this.selectedCountryCode = countryCode;
        this.selectedCountryFlag = flag;
        Picasso.get().load(Const.Url.HOST_URL + flag).error(R.drawable.ic_india).into(binding.ivFlagIndiaId);
        binding.tvCountryCodeId.setText(selectedCountryCode);*/
        binding.tvSelectCountry.setText(selectedCountryName);
        binding.tvSelectState.setText(Constants.Key.blank);
        bottomSheetDialog.dismiss();
    }

    public void onStateSelected(String State) {
        this.selectedState = State;
        binding.tvSelectState.setText(selectedState);
        bottomSheetStateDialog.dismiss();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
            /*    final boolean globalVisibleRect;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}