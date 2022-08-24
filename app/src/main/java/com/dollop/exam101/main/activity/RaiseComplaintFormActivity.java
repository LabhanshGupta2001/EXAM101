package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppHelper;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityRaiseComplaintFormBinding;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.validation.ResultReturn;
import com.dollop.exam101.main.validation.Validation;
import com.dollop.exam101.main.validation.ValidationModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseComplaintFormActivity extends BaseActivity implements View.OnClickListener {

    public String fileName;
    Activity activity = RaiseComplaintFormActivity.this;
    ActivityRaiseComplaintFormBinding binding;
    Intent intent = new Intent();
    String Priority = "";
    ApiService apiService;
    List<ValidationModel> validationModels = new ArrayList<>();
    Uri uri;


   /* public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llHigh.setOnClickListener(this);
        binding.llNormal.setOnClickListener(this);
        binding.llCritical.setOnClickListener(this);
        binding.mcvUpload.setOnClickListener(this);
        binding.llUploadPdfDone.setOnClickListener(this);
        binding.etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorSubject.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvErrorDescription.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llSave) {
            CheckValidationTask();
        } else if (view == binding.llNormal) {
            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image.setVisibility(View.VISIBLE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));
            Priority = Constants.Key.Normal;

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        } else if (view == binding.llHigh) {
            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image2.setVisibility(View.VISIBLE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));
            Priority = Constants.Key.High;


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


        } else if (view == binding.llCritical) {
            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image3.setVisibility(View.VISIBLE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));
            Priority = Constants.Key.Critical;


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        } else if (view == binding.mcvUpload) {
            intent = getFileChooserIntent();
            //startActivityForResult(intent, 100);
            openGallery.launch(intent);


        } else if (view == binding.llUploadPdfDone) {
            //intent = getFileChooserIntent();
            //startActivityForResult(intent, 100);
            openGallery.launch(intent);
        }
    }



    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return (extension != null) ? extension : Constants.Key.blank;
    }


    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            uri = result.getData().getData();
            Utils.E("uri::" + uri);
            Utils.E("uploadDocFile.toString()::" + getMimeType(activity, uri));
            binding.llUpload.setVisibility(View.GONE);
            binding.llUploadPdfDone.setVisibility(View.VISIBLE);
            getFileName();
            binding.tvUploadFilename.setText(fileName);

        }

    });


    private void CheckValidationTask() {
        validationModels.clear();
        validationModels.add(new ValidationModel(Validation.Type.Empty, binding.etSubject, binding.tvErrorSubject));
        validationModels.add(new ValidationModel(Validation.Type.Empty, binding.etDescription, binding.tvErrorDescription));
        Validation validation = Validation.getInstance();
        ResultReturn resultReturn = validation.CheckValidation(activity, validationModels);
        if (resultReturn.aBoolean) {
            if (!Priority.equals(Constants.Key.blank)){
                submitComplaint();
            }else {
                Utils.T(activity,Constants.Key.select_priority);
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

    private void submitComplaint() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        MultipartBody.Part image;
        hashMap.put(Constants.Key.complaintSubject,RequestBody.create(binding.etSubject.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.complaintDescription,RequestBody.create(binding.etDescription.getText().toString().trim(), MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        hashMap.put(Constants.Key.complaintPriority,RequestBody.create(Priority, MediaType.parse(Constants.Key.TEXT_PLAIN_TYPE)));
        if (uri != null){
            if (getMimeType(activity, uri).equals(Constants.Key.pdf) || getMimeType(activity, uri).equals(Constants.Key.doc) || getMimeType(activity, uri).equals(Constants.Key.docx)) {
                image = AppHelper.prepareFilePartPDF(activity,Constants.Key.attachment,uri);
                Utils.E("PdfImage:::"+image);
            } else if ( getMimeType(activity, uri).equals(Constants.Key.jpg) || getMimeType(activity, uri).equals(Constants.Key.png) || getMimeType(activity, uri).equals(Constants.Key.jpeg)) {
                image = AppHelper.prepareFilePart(activity,Constants.Key.attachment, uri);
                Utils.E("onlyImage:::"+image);
            } else {
                image = AppHelper.prepareFilePart(Constants.Key.attachment);
                Utils.E("NULLLLDAta:::"+image);
            }
        }else {
            image  = AppHelper.prepareFilePart(Constants.Key.attachment);

        }
        Utils.E("Image:::"+image);

        apiService.addRaiseComplaint(Utils.GetSession().token,hashMap,image).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Utils.T(activity, getString(R.string.Complaint_Raised_Successfully));
                        finish();
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
    private void getFileName(){
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        try {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            fileName = returnCursor.getString(nameIndex);
            Utils.E("file name : " + fileName);
        }catch (Exception e){
            Utils.E("error: " + e);
            //handle the failure cases here
        } finally {
            returnCursor.close();
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