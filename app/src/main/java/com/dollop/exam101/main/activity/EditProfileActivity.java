package com.dollop.exam101.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.UserData;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.AppHelper;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityEditProfileBinding;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private final ArrayList<CountryModel> contryItemArrayList = new ArrayList<>();
    private final ArrayList<StateModel> stateItemArrayList = new ArrayList<>();
    String selectedCountryUuId = "", selectedCountryCode, selectedCountryName, selectedState, selectedCountryFlag;
    Activity activity = EditProfileActivity.this;
    ActivityEditProfileBinding binding;
    ApiService apiService;
    BottomSheetDialog bottomSheetDialog, bottomSheetStateDialog;
    BottomSheetCountryBinding bottomSheetCountryBinding;
    BottomSheetStateBinding bottomSheetStateBinding;
    String Token;
    List<ValidationModel> allResponseModels = new ArrayList<>();
    boolean profile = false;
    Uri profileUri = null;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;

    ActivityResultLauncher<String> openGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            profile = true;
            profileUri = uri;
            binding.ivProfile.setImageURI(uri);
        }

    });
    ActivityResultLauncher<Intent> openCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            Bitmap bitmap = (Bitmap) result.getData().getExtras().get(Constants.Key.Data);
            binding.ivProfile.setImageBitmap(bitmap);
            profile = true;
            profileUri = Utils.getImageUri(activity, bitmap);
        }
    });


    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        Token = Utils.GetSession().token;
        if (AppController.getInstance().isOnline()) {
            SetDataWithDataBase();
            getCountryList();
        } else {
            InternetDialog();
            //Utils.InternetDialog(activity);
        }

        binding.mcvProfileSelector.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llCountryCode.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.etEnterMobile.setOnClickListener(this);
        binding.tvSelectCountry.setOnClickListener(this);
        binding.tvSelectState.setOnClickListener(this);


        binding.etEnterMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    binding.mcvMobile.setStrokeColor(ContextCompat.getColor(activity, R.color.theme));
                }else binding.mcvMobile.setStrokeColor(ContextCompat.getColor(activity, R.color.HorizontallineColor));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.mcvProfileSelector) {
            if (checkAndRequestPermissions(activity)) {
                chooseImage();
            }
        } else if (view == binding.llSave) {
            CheckValidationTask();
        } else if (view == binding.ivBack) {
            finish();
        } else {
            if (view != binding.etEnterMobile) {
               if (view == binding.tvSelectState) {
                    if (binding.tvSelectCountry.equals(Constants.Key.blank))
                        Utils.T(activity, Constants.Key.Pleas_Select_Country);
                    else bottomSheetStateTask();
                }/*else if (view == binding.llCountryCode) {
                    bottomSheetCountryTask(Constants.Key.CountryId_Show);
                }  if (view == binding.tvSelectCountry) {
                    bottomSheetCountryTask(Constants.Key.Country_Code_Nan);
                } */
            }
        }


    }

    private void chooseImage() {
        final CharSequence[] optionsMenu = {Constants.Key.Take_Photo, Constants.Key.Choose_From_Gallery, Constants.Key.Exit}; // create a menuOption Array
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(optionsMenu, (dialogInterface, i) -> {
            if (optionsMenu[i].equals(Constants.Key.Take_Photo)) {
                openCamera.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                //  Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(takePicture, 0);
            } else if (optionsMenu[i].equals(Constants.Key.Choose_From_Gallery)) {
              /*  Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);*/
                openGallery.launch(Constants.Key.CONTENT_ALL_IMAGE);
            } else if (optionsMenu[i].equals(Constants.Key.Exit)) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        Constants.Key.FlagUp_Requires_Access_To_Camara, Toast.LENGTH_SHORT).show();
                showSettingsDialog();
            } else if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                showSettingsDialog();
                Toast.makeText(getApplicationContext(), Constants.Key.FlagUp_Requires_Access_To_Your_Storage, Toast.LENGTH_SHORT).show();
            } else {
                chooseImage();
            }
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(Constants.Key.Need_Permissions);

        builder.setMessage(R.string.needs_permission);
        builder.setPositiveButton(Constants.Key.GOTO_SETTINGS, (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(Constants.Key.Package, getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton(Constants.Key.Cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get(Constants.Key.Data);
                        binding.ivProfile.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        binding.ivProfile.setImageURI(selectedImage);
                    }
                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bottomSheetCountryTask(String countryKey) {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetCountryBinding = BottomSheetCountryBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetCountryBinding.getRoot());

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetCountryBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setMaxHeight(binding.rlChildParent.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);

        countryAdapter = new CountryAdapter(activity, contryItemArrayList, countryKey, Constants.Key.EditProfile);
        bottomSheetCountryBinding.rvCountryListId.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        bottomSheetCountryBinding.rvCountryListId.setAdapter(countryAdapter);
        bottomSheetCountryBinding.searchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.getFilter().filter(newText);
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

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetStateBinding.getRoot().getParent()));
        bottomSheetStateDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setMaxHeight(binding.rlChildParent.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);
        if (AppController.getInstance().isOnline()) {
            getState();
        } else {
            Utils.InternetDialog(activity);
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

    private void getCountryList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getCountryList().enqueue(new Callback<AllResponseModel>() {
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

    private void getState() {
        Utils.E("getCountryUuId::" + SavedData.getCountryUuId());
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getStateList(SavedData.getCountryUuId()).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        stateItemArrayList.clear();
                        assert response.body() != null;
                        stateItemArrayList.addAll(response.body().state);
                        stateAdapter = new StateAdapter(activity, stateItemArrayList, Constants.Key.ClickProfile);
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
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });

    }


    //UpdateProfileApi
    private void UpdateProfile() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        MultipartBody.Part profilePic;

        hashMap.put(Constants.Key.studentName, RequestBody.create(binding.etUserName.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.studentEmail, RequestBody.create(binding.etUserEmail.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.studentMobileNo, RequestBody.create(binding.etEnterMobile.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.countryCode, RequestBody.create(binding.tvCountryCodeId.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.countryName, RequestBody.create(binding.tvSelectCountry.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.stateName, RequestBody.create(binding.tvSelectState.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));

        if (profile && profileUri != null) {
            profilePic = AppHelper.prepareFilePart(Constants.Key.profilePic, binding.ivProfile.getDrawable());
        } else {
            profilePic = AppHelper.prepareFilePart(Constants.Key.profilePic);
        }
        apiService.EditProfileImage(Token, hashMap, profilePic).enqueue(new Callback<AllResponseModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, "Updated Successfully");
                        if (AppController.getInstance().isOnline()) {
                          getEditProfileDetails();
                        } else {
                            InternetDialog();
                            //Utils.InternetDialog(activity);
                        }

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

    private void CheckValidationTask() {
        allResponseModels.clear();
        allResponseModels.add(new ValidationModel(Validation.Type.Empty, binding.etUserName, binding.etUserEmail, binding.etEnterMobile));
        allResponseModels.add(new ValidationModel(Validation.Type.Email, binding.etUserEmail));
        allResponseModels.add(new ValidationModel(Validation.Type.Phone, binding.etEnterMobile));
        allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCountry.getText().toString(), getString(R.string.please_select_the_country)));
        allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectState.getText().toString(), getString(R.string.please_select_the_state)));
        // allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCity.getText().toString(), getString(R.string.please_select_the_city)));

        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, allResponseModels);
        if (resultReturn.aBoolean) {
            UpdateProfile();
            // Toast.makeText(this, Constants.Key.All_Validation_Pass, Toast.LENGTH_SHORT).show();

        } else {
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

    private  void SetDataWithDataBase(){
        binding.etEnterMobile.setText(Utils.GetSession().studentMobileNo);
        binding.etUserEmail.setText(Utils.GetSession().studentEmail);
        binding.etUserName.setText(Utils.GetSession().studentName);
        binding.tvSelectState.setText(Utils.GetSession().stateName);
        Picasso.get().load(Const.HOST_URL + Utils.GetSession().profilePic).error(R.drawable.user_profile).into(binding.ivProfile);
        if (Utils.GetSession().countryName.equals(Constants.Key.blank)) {
          //  binding.tvSelectCountry.setText(Utils.getDefaultCountryCode(activity).countryName);
            binding.tvSelectCountry.setText(Constants.Key.India);
        } else {
            binding.tvSelectCountry.setText(Utils.GetSession().countryName);
        }
        if (Utils.GetSession().countryCode.equals(Constants.Key.blank)) {
               binding.tvCountryCodeId.setText(Constants.Key.Default_Country_Code);
            //binding.tvCountryCodeId.setText(Utils.getDefaultCountryCode(activity).CountryCode);
        } else {
            binding.tvCountryCodeId.setText(Utils.GetSession().countryCode);
        }
    }
    //GetApiProfileData
    private void getEditProfileDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getEditProfileDetails(Token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        UserData userData = response.body().studentData;
                        UserData DatabaseData = Utils.GetSession();
                        userData.studentId = DatabaseData.studentId;
                        userData.token = DatabaseData.token;
                        UserDataHelper.getInstance().insertData(userData);
                        SetDataWithDataBase();

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

    public void onCountrySelectedE(String countryUuId, String CountryName /*String countryCode, String flag*/) {
        this.selectedCountryUuId = countryUuId;
        this.selectedCountryName = CountryName;
        binding.tvSelectCountry.setText(Constants.Key.India);
        binding.tvSelectState.setText(Constants.Key.blank);
        bottomSheetDialog.dismiss();
    }

    public void onStateSelectedE(String State) {
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
               /* final boolean globalVisibleRect;
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity,android.R.style.Theme_DeviceDefault_Dialog_Alert);
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

