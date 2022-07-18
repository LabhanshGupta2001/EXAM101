package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.UserData;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivitySignUpBinding;
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
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 100;
    String selectedCountryId, selectedCountryCode, selectedCountryName, selectedState, selectedCountryFlag;
    String personName,personEmail;
    Activity activity = SignUpActivity.this;
    ActivitySignUpBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    BottomSheetDialog bottomSheetDialog, bottomSheetStateDialog;
    BottomSheetCountryBinding bottomSheetCountryBinding;
    BottomSheetStateBinding bottomSheetStateBinding;
    List<ValidationModel> errorValidationModels = new ArrayList<>();
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private ArrayList<CountryModel> contryItemArrayList = new ArrayList<>();
    private ArrayList<StateModel> stateItemArrayList = new ArrayList<>();
    private ApiService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiservice = RetrofitClient.getClient();
        init();
    }

    private void init() {

        getCountryList();

        binding.SignUPId.setOnClickListener(this);
        binding.tvRegisterId.setOnClickListener(this);
        binding.llLoginWithGoogle.setOnClickListener(this);
        binding.llSignUp.setOnClickListener(this);
        binding.tvSelectCountry.setOnClickListener(this);
        binding.tvSelectState.setOnClickListener(this);
        binding.llCountryCode.setOnClickListener(this);
        binding.tvRegisterId.setOnClickListener(this);
        // countryAdapter = new CountryAdapter(activity, contryItemArrayList);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        binding.llLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
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
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                 personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Log.e(String.valueOf(activity), "handleSignInResult: " + personName);
            }
            SocialLogin();
           // startActivity(new Intent(activity, DashboardScreenActivity.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("massage", e.toString());
        }
    }


    @Override
    public void onClick(View view) {
        if (view == binding.SignUPId) {
            Utils.I(SignUpActivity.this, LoginActivity.class, null);
        } else if (view == binding.tvSelectCountry) {
            bottomSheetCountryTask(Constants.Key.No,Constants.Key.Login);
        } else if (view == binding.tvSelectState) {
            bottomSheetStateTask();
        } else if (view == binding.llCountryCode) {
            bottomSheetCountryTask(Constants.Key.Yes,Constants.Key.Login);
        } else if (view == binding.tvRegisterId) {
            CheckValidationTask();
        }/*else if (view == binding.llLoginWithGoogle) {

        }*/
    }

    private void getState(String countryId) {
        apiservice.getStateList(countryId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
               try {

                if (response.code() == StatusCodeConstant.OK) {
                    stateItemArrayList.clear();
                    assert response.body() != null;
                    stateItemArrayList.addAll(response.body().state);
                    stateAdapter = new StateAdapter(activity, stateItemArrayList,Constants.Key.ClickSign);
                    bottomSheetStateBinding.rvStateListId.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
                    bottomSheetStateBinding.rvStateListId.setAdapter(stateAdapter);
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });

    }

    private void getCountryList() {
        apiservice.getCountryList().enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });

    }

    private void CheckValidationTask() {
        errorValidationModels.clear();
        errorValidationModels.add(new ValidationModel(Validation.Type.Empty, binding.etUserName));
        errorValidationModels.add(new ValidationModel(Validation.Type.Email, binding.etUserEmail));
        errorValidationModels.add(new ValidationModel(Validation.Type.Phone, binding.etEnterMobile));
        errorValidationModels.add(new ValidationModel(Validation.Type.PasswordMatch, binding.etPassword, binding.etConformPassword));
        errorValidationModels.add(new ValidationModel(Validation.Type.PasswordStrong, binding.etPassword, binding.etConformPassword));
        errorValidationModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCountry.getText().toString(), getString(R.string.please_select_the_country)));
        errorValidationModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectState.getText().toString(), getString(R.string.please_select_the_state)));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, errorValidationModels);
        if (resultReturn.aBoolean) {
            userSignup();
            Toast.makeText(this, "All Validation Pass", Toast.LENGTH_SHORT).show();

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

    //   CountryAdapter countryAdapter;
    private void bottomSheetCountryTask(String from,String FROM) {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetCountryBinding = BottomSheetCountryBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetCountryBinding.getRoot());
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetCountryBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        countryAdapter = new CountryAdapter(activity, contryItemArrayList, from,FROM);
        bottomSheetCountryBinding.rvCountryListId.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        bottomSheetCountryBinding.rvCountryListId.setAdapter(countryAdapter);
        bottomSheetDialog.show();

    }

    private void bottomSheetStateTask() {
        bottomSheetStateDialog = new BottomSheetDialog(activity);
        bottomSheetStateBinding = BottomSheetStateBinding.inflate(getLayoutInflater());
        bottomSheetStateDialog.setContentView(bottomSheetStateBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetStateBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getState(selectedCountryId);
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
        hm.put(Constants.Key.countryCode, selectedCountryCode);
        hm.put(Constants.Key.countryName, selectedCountryName);
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
                        Utils.I(activity, LoginActivity.class, bundle);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.T(activity, message.message);
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

    public void onCountrySelected(String countryId, String countryCode, String CountryName, String flag) {
        this.selectedCountryId = countryId;
        this.selectedCountryName = CountryName;
        this.selectedCountryCode = countryCode;
        this.selectedCountryFlag = flag;
        binding.tvCountryCodeId.setText(selectedCountryCode);
        binding.tvSelectCountry.setText(selectedCountryName);
        binding.tvSelectState.setText(Constants.Key.blank);
        Picasso.get().load(Const.FLAG_URL + flag).error(R.drawable.ic_india).into(binding.ivFlagIndiaId);
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
                        Utils.E("Google:::"+response.body().userData.studentId);
                        assert response.body() != null;
                        UserDataHelper.getInstance().insertData(response.body().userData);
                        Utils.I_clear(activity, DashboardScreenActivity.class,null);

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.T(activity, message.message);
                            }
                        } else {
                            Utils.alert(activity, message.message);
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