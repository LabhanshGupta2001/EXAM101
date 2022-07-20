package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityContactUsBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = ContactUsActivity.this;
    ActivityContactUsBinding binding;
    ApiService apiService;
    List<ValidationModel> allResponseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.llSave.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);

    }

    private void ContactUs() {
        Dialog progressDialog=Utils.initProgressDialog(activity);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(Constants.Key.name, (binding.etHolderName.getText().toString().trim()));
        hashMap.put(Constants.Key.email, (binding.etAccountEmail.getText().toString().trim()));
        hashMap.put(Constants.Key.mobile, (binding.etAccountMobileNo.getText().toString().trim()));
        hashMap.put(Constants.Key.message, (binding.etMessage.getText().toString().trim()));

        apiService.ContactUs(hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity,""+response.body().message);
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

    @Override
    public void onClick(View view) {
        if (view == binding.llSave) {
            CheckValidationTask();

        } else if (view == binding.ivBack) {
            finish();
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

    private void CheckValidationTask() {
        allResponseModels.clear();
        allResponseModels.add(new ValidationModel(Validation.Type.Empty, binding.etHolderName));
        allResponseModels.add(new ValidationModel(Validation.Type.Email, binding.etAccountEmail));
        allResponseModels.add(new ValidationModel(Validation.Type.Phone, binding.etAccountMobileNo));
        allResponseModels.add(new ValidationModel(Validation.Type.Empty, binding.etMessage));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, allResponseModels);
        if (resultReturn.aBoolean) {
            ContactUs();
        } else {
            if (resultReturn.type == Validation.Type.EmptyString) {
                //  resultReturn.errorTextView.setText(resultReturn.errorMessage);
                Toast.makeText(this, resultReturn.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                //resultReturn.errorTextView.setText(validation.errorMessage);
                validation.EditTextPointer.setError(validation.errorMessage);
                validation.EditTextPointer.requestFocus();
            }

        }
    }

}