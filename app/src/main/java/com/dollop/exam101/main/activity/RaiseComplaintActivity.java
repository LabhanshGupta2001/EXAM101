package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityRaiseComplaintBinding;
import com.dollop.exam101.databinding.BottomsheetRaiseComplaintsBinding;
import com.dollop.exam101.main.adapter.RaiseComplaintAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.ComplaintModel;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseComplaintActivity extends BaseActivity implements View.OnClickListener {
    ApiService apiService;
    Activity activity = RaiseComplaintActivity.this;
    ActivityRaiseComplaintBinding binding;
    ArrayList<ComplaintModel> complaintModelArrayList = new ArrayList<>();
    RaiseComplaintAdapter raiseComplaintAdapter;
    BottomSheetDialog bottomSheetDialog;
    BottomsheetRaiseComplaintsBinding bottomsheetRaiseComplaintsBinding;

    @Override
    protected void onResume() {
        super.onResume();
        getComplaintList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.mcvAdd.setOnClickListener(this);
        raiseComplaintAdapter = new RaiseComplaintAdapter(activity, complaintModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rvRaiseComplaint.setLayoutManager(linearLayoutManager);
        binding.rvRaiseComplaint.setAdapter(raiseComplaintAdapter);

       /* list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvRaiseComplaint.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvRaiseComplaint.setAdapter(new RaiseComplaintAdapter(activity, list));*/
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.mcvAdd) {
            Utils.I(activity, RaiseComplaintFormActivity.class, null);
        }
    }


 /*   public void complaintDetails() {
        BottomSheetTask();
        Dialog progressDialog = Utils.initProgressDialog(activity);
        HashMap<String, String> hm = new  HashMap<>();
        hm.put(Constants.Key.complaintUuid  ,Uuid);
        apiService.getComplaintDetail(Utils.GetSession().token,hm).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                       // complaintListModels.add(response.body().complaintListModel);
                        Utils.T(activity, getString(R.string.Complaint_Raised_Successfully));
                        Utils.I(activity, RaiseComplaintActivity.class,null);
                        bottomsheetRaiseComplaintsBinding.tvComplainId.setText(Constants.Key.ComplaintID+response.body().complaintListModel.complaintId);
                        bottomsheetRaiseComplaintsBinding.tvDetails.setText(Constants.Key.ComplaintID+response.body().complaintListModel.complaintDescription);

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
    }*/

    void getComplaintList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getComplaintList(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        complaintModelArrayList.clear();
                        assert response.body() != null;
                        complaintModelArrayList.addAll(response.body().complaintList);
                        if (!complaintModelArrayList.isEmpty()) {
                            binding.rvRaiseComplaint.setVisibility(View.VISIBLE);
                            binding.layoutDataNotFound.llParent.setVisibility(View.GONE);
                            raiseComplaintAdapter.notifyDataSetChanged();
                        } else {
                            binding.layoutDataNotFound.llParent.setVisibility(View.VISIBLE);
                            binding.rvRaiseComplaint.setVisibility(View.GONE);
                        }

                    } else {
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            assert response.errorBody() != null;
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            assert response.errorBody() != null;
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
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

    @SuppressLint("SetTextI18n")
    public void BottomSheetTask(ComplaintModel model) {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomsheetRaiseComplaintsBinding = BottomsheetRaiseComplaintsBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomsheetRaiseComplaintsBinding.getRoot());
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetRaiseComplaintsBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomsheetRaiseComplaintsBinding.tvDetails.setText(model.complaintDescription);
        bottomsheetRaiseComplaintsBinding.tvComplainId.setText(Constants.Key.ComplaintID + model.complaintId);
        if (!model.attachment.equals(Constants.Key.blank)) {
            //  bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.VISIBLE);
           /* uri = Uri.parse(model.attachment);
            Utils.E("AttachmentUri::"+uri);*/
            filesSet(model);
        } else {
            bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.GONE);
            Utils.T(activity, "Not found pdf and Image");
        }
        bottomSheetDialog.show();
        bottomsheetRaiseComplaintsBinding.llSave.setOnClickListener(view1 ->
        {
            bottomSheetDialog.cancel();
        });
    }

    private void filesSet(ComplaintModel model) {
        String s = model.attachment;
        if (s.contains(Constants.Key.pdf) || s.contains(Constants.Key.doc)
                || s.contains(Constants.Key.docx)) {
            Utils.E("*&^AttachmentIMAGE");
            bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.GONE);
            bottomsheetRaiseComplaintsBinding.pvImagePdf.setVisibility(View.VISIBLE);
            displayFromUri(model);
        } else if (s.contains(Constants.Key.jpg) || s.contains(Constants.Key.png)
                || s.contains(Constants.Key.jpeg)) {
            Utils.E("*&^AttachmentIMAGE");
            bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.VISIBLE);
            bottomsheetRaiseComplaintsBinding.pvImagePdf.setVisibility(View.GONE);
            Picasso.get().load(Const.Url.HOST_URL + model.attachment).into(bottomsheetRaiseComplaintsBinding.imageView);
        } else {
            Utils.T(activity, "Show only pdf and Image");
        }

        /*if (getMimeType(activity, uri).equals(Constants.Key.PDF) || getMimeType(activity, uri).equals(Constants.Key.doc)
                || getMimeType(activity, uri).equals(Constants.Key.docx))
        {
            Utils.E("AttachmentPDF&**&*");
            bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.GONE);
            bottomsheetRaiseComplaintsBinding.pvImagePdf.setVisibility(View.VISIBLE);
            displayFromUri(uri,bottomsheetRaiseComplaintsBinding.pvImagePdf);
            // Picasso.get().load(Const.Url.HOST_URL + model.attachment).into(bottomsheetRaiseComplaintsBinding.pvImagePdf);
        } else if (getMimeType(activity, uri).equals(Constants.Key.jpg) || getMimeType(activity, uri).equals(Constants.Key.png) || getMimeType(activity, uri).equals(Constants.Key.jpeg))
        {
            Utils.E("*&^AttachmentIMAGE");
            bottomsheetRaiseComplaintsBinding.imageView.setVisibility(View.VISIBLE);
            bottomsheetRaiseComplaintsBinding.pvImagePdf.setVisibility(View.GONE);
            Picasso.get().load(Const.Url.HOST_URL +model.attachment).into(bottomsheetRaiseComplaintsBinding.imageView);
        }else {
            Utils.T(activity, "Show only pdf and Image");
        }*/
    }

    private void displayFromUri(ComplaintModel model) {
      AsyncTask.execute(() -> {
        try {
            final InputStream input = new URL(Const.Url.HOST_URL + model.attachment).openStream();
            activity.runOnUiThread(() -> bottomsheetRaiseComplaintsBinding.pvImagePdf.fromStream(input)
                    .enableSwipe(true)
                    .fitEachPage(true)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(activity))
                    .load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }

}