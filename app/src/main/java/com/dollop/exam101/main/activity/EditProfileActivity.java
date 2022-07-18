package com.dollop.exam101.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.dollop.exam101.Basics.UtilityTools.AppHelper;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityEditProfileBinding;
import com.dollop.exam101.databinding.BottomSheetCountryBinding;
import com.dollop.exam101.databinding.BottomSheetStateBinding;
import com.dollop.exam101.main.adapter.CountryAdapter;
import com.dollop.exam101.main.adapter.EditProfileAdapter;
import com.dollop.exam101.main.adapter.EditProfileCountryAdapter;
import com.dollop.exam101.main.adapter.StateAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CountryItem;

import com.dollop.exam101.main.model.CountryModel;
import com.dollop.exam101.main.model.StateModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    String selectedCountryId, selectedCountryCode, selectedCountryName, selectedState, selectedCountryFlag;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    Activity activity = EditProfileActivity.this;
    ActivityEditProfileBinding binding;
    ApiService apiService;
    ImageView imageView;
    EditProfileCountryAdapter contryAdapter;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private ArrayList<CountryModel> contryItemArrayList = new ArrayList<>();
    private ArrayList<StateModel> stateItemArrayList = new ArrayList<>();
    BottomSheetDialog bottomSheetDialog, bottomSheetStateDialog;
    BottomSheetCountryBinding bottomSheetCountryBinding;
    BottomSheetStateBinding bottomSheetStateBinding;
    String Token;
    List<ValidationModel> allResponseModels = new ArrayList<>();
    boolean profile = false;
    Uri profileUri = null;

  /*  ActivityResultLauncher<String> openGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            profile = true;
            profileUri = uri;
            binding.ivProfile.setImageURI(uri);
        }

    });
    ActivityResultLauncher<Intent> openCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
            Utils.E("bitmap:::" + bitmap);
            binding.ivProfile.setImageBitmap(bitmap);
            profile = true;
            profileUri = Utils.getImageUri(activity, bitmap);
        }
    });
*/

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        Token=Utils.GetSession().token;
        Utils.E("Token:::"+Token);
        getEditProfileDetails();
        getCountryList();

        binding.mcvProfileSelector.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llCountryCode.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.etEnterMobile.setOnClickListener(this);
        binding.tvSelectCountry.setOnClickListener(this);
        binding.tvSelectState.setOnClickListener(this);
        binding.tvSelectCity.setOnClickListener(this);
/*

        initList();
        spinner();
        imageView = binding.ivProfile;
        String[] countryNames = {"India", "China", "Australia", "Portugle", "America", "New Zealand"};
        int[] flags = {R.drawable.ic_india, R.drawable.ic_china, R.drawable.ic_flag_of_australia, R.drawable.ic_portugal, R.drawable.ic_portugal, R.drawable.ic_new_zealand};
        binding.Spinner.setOnItemSelectedListener(this);
        EditProfileAdapter editProfileAdapter = new EditProfileAdapter(getApplicationContext(), flags, countryNames);
        binding.Spinner.setAdapter(editProfileAdapter);
        contryAdapter = new EditProfileCountryAdapter(activity, contryItemArrayList);
        binding.SpinnerCountry.setAdapter(contryAdapter);
        binding.SpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CountryItem contryItem = (CountryItem) adapterView.getItemAtPosition(i);
                String ClickContryCode = contryItem.getCode();
                Utils.E(ClickContryCode);
                Log.e("ClickContryCode", ClickContryCode);
                binding.tvContryCodeId.setText(ClickContryCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvProfileSelector) {
            if (checkAndRequestPermissions(activity)) {
                chooseImage(activity);
            }
        } else if (view == binding.llSave) {
            CheckValidationTask();
        }else if (view == binding.ivBack) {
            finish();
        } else if (view == binding.etEnterMobile) {

        }  else if (view == binding.tvSelectCountry) {
            bottomSheetCountryTask(Constants.Key.No,Constants.Key.EditProfile);
        } else if (view == binding.tvSelectState) {
            bottomSheetStateTask();
        } else if (view == binding.llCountryCode) {
            bottomSheetCountryTask(Constants.Key.Yes,Constants.Key.EditProfile);
        }

    }

    private void chooseImage(Activity activity) {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    showSettingsDialog();
                    Toast.makeText(getApplicationContext(), "FlagUp Requires Access to Your Storage.", Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(activity);
                }
                break;
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
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

    private void bottomSheetCountryTask(String from, String FROM) {
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

    private void bottomSheetStateTask()
    {
        bottomSheetStateDialog = new BottomSheetDialog(activity);
        bottomSheetStateBinding = BottomSheetStateBinding.inflate(getLayoutInflater());
        bottomSheetStateDialog.setContentView(bottomSheetStateBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetStateBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getState(selectedCountryId);
        bottomSheetStateDialog.show();

    }
    private void getCountryList() {
        apiService.getCountryList().enqueue(new Callback<AllResponseModel>() {
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

    private void getState(String countryId) {
        apiService.getStateList(countryId).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                try {

                    if (response.code() == StatusCodeConstant.OK) {
                        stateItemArrayList.clear();
                        assert response.body() != null;
                        stateItemArrayList.addAll(response.body().state);
                        stateAdapter = new StateAdapter(activity, stateItemArrayList,Constants.Key.ClickProfile);
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


    //UpdateProfileApi
    private void UpdateProfile() {
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        //MultipartBody.Part profilePic;

        hashMap.put(Constants.Key.studentName, RequestBody.create(binding.etUserName.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.studentEmail, RequestBody.create(binding.etUserEmail.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.studentMobileNo, RequestBody.create(binding.etEnterMobile.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.countryCode, RequestBody.create(binding.tvCountryCodeId.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.countryName, RequestBody.create(binding.tvSelectCountry.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.stateName, RequestBody.create(binding.tvSelectState.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));

        File f = new File(this.getCacheDir(), "image.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.ivProfile.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        RequestBody profilePicRequest = RequestBody.create(MediaType.parse("image/*"), f);

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(imageBytes);
            fos.close();
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }


        MultipartBody.Part part = MultipartBody.Part.createFormData("profile_pic", f.getName(), profilePicRequest);

       /* if (profile && profileUri != null) {
            profilePic = AppHelper.prepareFilePart(Constants.Key.profilePic, binding.ivProfile.getDrawable());
        } else {
            profilePic = AppHelper.prepareFilePart(Constants.Key.profilePic);
        }*/
        apiService.EditProfileImage(Token,hashMap, part).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, "Updated Successfully");
                        getEditProfileDetails();

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.alert(activity, message.message);
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }
    private void CheckValidationTask() {
        allResponseModels.clear();
        allResponseModels.add(new ValidationModel(Validation.Type.Empty, binding.etUserName,binding.etUserEmail,binding.etEnterMobile));
        allResponseModels.add(new ValidationModel(Validation.Type.Email,binding.etUserEmail));
        allResponseModels.add(new ValidationModel(Validation.Type.Phone,binding.etEnterMobile));
        allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCountry.getText().toString(), getString(R.string.please_select_the_country)));
        allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectState.getText().toString(), getString(R.string.please_select_the_state)));
       // allResponseModels.add(new ValidationModel(Validation.Type.EmptyString, binding.tvSelectCity.getText().toString(), getString(R.string.please_select_the_city)));

        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, allResponseModels);
        if (resultReturn.aBoolean) {
            UpdateProfile();
            Toast.makeText(this, "All Validation Pass", Toast.LENGTH_SHORT).show();

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

    //GetApiProfileData
    private void getEditProfileDetails() {
        apiService.getEditProfileDetails(Token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        UserData userData =response.body().studentData;
                        binding.etEnterMobile.setText(userData.studentMobileNo);
                        binding.etUserEmail.setText(userData.studentEmail);
                        binding.etUserName.setText(userData.studentName);
                        binding.tvCountryCodeId.setText(userData.countryCode);
                        binding.tvSelectCountry.setText(userData.countryName);
                        binding.etUserName.setText(userData.studentName);
                        binding.tvSelectState.setText(userData.stateName);
                      //  Picasso.get().load(Const.FLAG_URL + userData.flag).error(R.drawable.ic_india).into(binding.ivFlagIndiaId);
                      // Picasso.get().load(Const.FLAG_URL + userData.).error(R.drawable.ic_india).into(binding.ivProfile);
                        UserData DatabaseData = Utils.GetSession();
                        userData.studentId =DatabaseData.studentId;
                        userData.token =DatabaseData.token;
                        UserDataHelper.getInstance().insertData(userData);



                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.alert(activity, message.message);
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }
    public void onCountrySelectedE(String countryId, String countryCode, String CountryName, String flag) {
        this.selectedCountryId = countryId;
        this.selectedCountryName = CountryName;
        this.selectedCountryCode = countryCode;
        this.selectedCountryFlag = flag;
        binding.tvCountryCodeId.setText(selectedCountryCode);
        binding.tvSelectCountry.setText(selectedCountryName);
        Picasso.get().load(Const.FLAG_URL + flag).error(R.drawable.ic_india).into(binding.ivFlagIndiaId);
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

