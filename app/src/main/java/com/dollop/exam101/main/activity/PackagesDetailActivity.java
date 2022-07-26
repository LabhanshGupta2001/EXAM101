package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityPackagesDetailBinding;
import com.dollop.exam101.main.adapter.MockTestViewPagerAdapter;

import com.dollop.exam101.main.fragment.CourseMaterialFragment;
import com.dollop.exam101.main.fragment.MockTestFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesDetailActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = PackagesDetailActivity.this;
    ActivityPackagesDetailBinding binding;
    ApiService apiService;
    String PackageId;
    MockTestViewPagerAdapter mockTestViewPagerAdapter;
    ArrayList<String> Tittle = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackagesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            PackageId = bundle.getString(Constants.Key.packageId);
        }
        binding.ivBack.setOnClickListener(this);
        binding.mcvAddtoWishlist.setOnClickListener(this);
        fragments.add(new CourseMaterialFragment(PackageId));
        fragments.add(new MockTestFragment());
        Tittle.add(Constants.Key.Course_Material);
        Tittle.add(Constants.Key.Course_Material);
        mockTestViewPagerAdapter = new MockTestViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        binding.ViewPagerPackageDetailId.setAdapter(mockTestViewPagerAdapter);

        new TabLayoutMediator(binding.tlPackageDetailTabLayoutId, binding.ViewPagerPackageDetailId, (tab, position) -> {
            tab.setText(Tittle.get(position));
        }).attach();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        }
        else if(view==binding.mcvAddtoWishlist){
            getWishList();
            Utils.T(activity,Constants.Key.added_to_Wishlist);
        }
    }

       private void getWishList() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
          HashMap<String, String> hm = new HashMap<>();
          hm.put(Constants.Key.packageId,PackageId);
        apiService.addWishlist(Utils.GetSession().token,hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;

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

}