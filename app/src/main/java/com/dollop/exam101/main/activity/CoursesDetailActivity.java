package com.dollop.exam101.main.activity;

import static com.dollop.exam101.main.activity.EditProfileActivity.REQUEST_ID_MULTIPLE_PERMISSIONS;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCoursesDetailBinding;
import com.dollop.exam101.databinding.BottomSheetPracticeTestBinding;
import com.dollop.exam101.main.adapter.TopicPdfadapter;
import com.dollop.exam101.main.adapter.TopicVideosAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesDetailActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CoursesDetailActivity.this;
    ActivityCoursesDetailBinding binding;
    BottomSheetPracticeTestBinding bottomSheetPracticeTestBinding;
    BottomSheetDialog bottomSheetDialog;
    ApiService apiService;
    //ArrayList<TopicDetailModel> topicList = new ArrayList<>();
    Bundle bundle;
    String orderExamUuid;
    String topicUuid;
    private String fileName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

        if (AppController.getInstance().isOnline()) {
            getCourseDetails();
        } else {
            Utils.InternetDialog(activity);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if ((view == binding.btnNext)) {
            showBottomSheetDialog();
        }

    }

    private void getCourseDetails() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new HashMap<>();
        hm.put(Constants.Key.topicUuid, bundle.getString(Constants.Key.topicUuid, bundle.getString(Constants.Key.topicUuid)));
        hm.put(Constants.Key.orderExamUuid, bundle.getString(Constants.Key.orderExamUuid, bundle.getString(Constants.Key.orderExamUuid)));
        hm.put(Constants.Key.device_type, Constants.Key.android);
        apiService.getTopicDetails(Utils.GetSession().token, hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        binding.tvChapterName.setText(response.body().topicDetailModel.topicName);
                        binding.tvTopicDetail.setText(HtmlCompat.fromHtml(response.body().topicDetailModel.topicDetail, 0));
                        orderExamUuid = response.body().topicDetailModel.orderExamUuid;
                        topicUuid = response.body().topicDetailModel.topicUuid;


                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.Key.orderExamUuid, response.body().topicDetailModel.orderExamUuid);
                        bundle.putString(Constants.Key.topicUuid, topicUuid);
                        bundle.putString(Constants.Key.topicName, response.body().topicDetailModel.topicName);
                        bundle.putString(Constants.Key.topicDetail, response.body().topicDetailModel.topicDetail);


                        if (response.body().topicDetailModel.video.isEmpty()) {
                            binding.tvCourseVideo.setVisibility(View.GONE);
                            binding.rvTopicVideo.setVisibility(View.GONE);

                        } else {
                            binding.tvCourseVideo.setVisibility(View.VISIBLE);
                            binding.rvTopicVideo.setVisibility(View.VISIBLE);
                            Utils.E("videoSize:::" + response.body().topicDetailModel.video.size());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                            binding.rvTopicVideo.setLayoutManager(linearLayoutManager);
                            binding.rvTopicVideo.setAdapter(new TopicVideosAdapter(response.body().topicDetailModel.video, activity, bundle));


                        }


                        if (response.body().topicDetailModel.pdfFile == null || response.body().topicDetailModel.pdfFile.isEmpty()) {
                            binding.tvCousrePdf.setVisibility(View.GONE);
                            binding.rvTopicPdf.setVisibility(View.GONE);
                        } else {
                            binding.rvTopicPdf.setVisibility(View.VISIBLE);
                            binding.tvCousrePdf.setVisibility(View.VISIBLE);
                            binding.rvTopicPdf.setLayoutManager(new LinearLayoutManager(activity));
                            binding.rvTopicPdf.setAdapter(new TopicPdfadapter(response.body().topicDetailModel, activity, "pdf", bundle));

                        }
                    } else {
                        assert response.errorBody() == null;
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

    private void showBottomSheetDialog() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetPracticeTestBinding = BottomSheetPracticeTestBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetPracticeTestBinding.getRoot());
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetPracticeTestBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

        bottomSheetPracticeTestBinding.tvBtnPracticeTest.setOnClickListener(view ->
        {
            bottomSheetDialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.topicUuid, topicUuid);
            bundle.putString(Constants.Key.orderExamUuid, orderExamUuid);
            Utils.I(activity, CourseTestActivity.class, bundle);
        });

    }

    private void getFileName(String uri) {
        Cursor returnCursor =
                getContentResolver().query(Uri.parse(uri), null, null, null, null);
        try {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            fileName = returnCursor.getString(nameIndex);

            Utils.E("file name : " + fileName);
        } catch (Exception e) {
            Utils.E("error: " + e);
            //handle the failure cases here
        } finally {
            returnCursor.close();
        }
    }

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
                //   chooseImage();
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
}