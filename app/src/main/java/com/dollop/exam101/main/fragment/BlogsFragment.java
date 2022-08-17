package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.FragmentBlogsBinding;
import com.dollop.exam101.main.adapter.BlogsHomeAdapter;
import com.dollop.exam101.main.adapter.NewsAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.NewsModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogsFragment extends Fragment implements View.OnClickListener{
   public FragmentBlogsBinding binding;
    Activity activity;
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();
    BlogsHomeAdapter blogsHomeAdapter;
    ApiService apiService;

    public BlogsFragment(ArrayList<AllBlogListModel> blogListModels) {
        this.Blogarraylist.addAll(blogListModels);
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlogsBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        activity = requireActivity();
        if (AppController.getInstance().isOnline()) {
            //getBlogs();
        } else {
            InternetDialog();
        }

        blogsHomeAdapter = new BlogsHomeAdapter(getActivity(), Blogarraylist);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvBlogs.setLayoutManager(linearLayoutManager3);
        binding.rvBlogs.setAdapter(blogsHomeAdapter);
    }

    @Override
    public void onClick(View v) {

    }
   /* public void getBlogs() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogsData(Constants.Key.blank).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    Blogarraylist.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Blogarraylist.addAll(response.body().blogs);
                        binding.rvBlogs.setHasFixedSize(true);
                        blogsHomeAdapter.notifyDataSetChanged();

                    } else {
                        // assert response.errorBody() != null;
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