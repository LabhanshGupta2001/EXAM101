package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;

import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.databinding.FragmentPackageListBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.PackageModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PackageListFragment extends Fragment implements View.OnClickListener {
    FragmentPackageListBinding binding;
    ApiService apiService;
    String token;
    Activity activity;

    BottomsheetFilterBinding bottomsheetFilterBinding;
    private final String[] labels = new String[]{"Exams","Price","Language"};
    BottomSheetDialog bottomSheetDialog;
    ArrayList<PackageModel> packageModelList = new ArrayList<>();
    PackageAdapter packageAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackageListBinding.inflate(inflater, container, false);
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(inflater,container,false);
        activity = requireActivity();
        init();
       return binding.getRoot();
    }


    private void init() {
        apiService = RetrofitClient.getClient();
        token = Utils.GetSession().token;
        binding.mcvFilterId.setOnClickListener(this);
        packageList();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvFilterId) {
            bottomsheetTask();
        }
    }

    private void bottomsheetTask() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomsheetFilterBinding.getRoot());

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetFilterBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setMaxHeight(binding.svParent.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);

        bottomSheetDialog.show();
        ArrayList<Fragment>Fragment=new ArrayList<>();
        Fragment.add(new CategoriesFragment());
        Fragment.add(new PriceFragment());
        Fragment.add(new LanguageFragment());

        bottomsheetFilterBinding.tvllSave.setOnClickListener(view ->
                bottomSheetDialog.cancel());

        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getParentFragmentManager(),getLifecycle(),Fragment));

        new TabLayoutMediator(bottomsheetFilterBinding.tlTabLayoutId, bottomsheetFilterBinding.ViewPagerId, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();

        View tab1 = ((ViewGroup) bottomsheetFilterBinding.tlTabLayoutId.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();

    }
    private void packageList() {
        Dialog progressDialog = Utils.initProgressDialog(requireActivity());
        apiService.packageListItem(token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        packageModelList.clear();
                        assert response.body() != null;
                        packageModelList.addAll(response.body().packageModels);

                        packageAdapter = new PackageAdapter(getActivity(), packageModelList);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        binding.rvPackagesone.setLayoutManager(linearLayoutManager2);
                        binding.rvPackagesone.setAdapter(packageAdapter);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.T(requireActivity(), message.message);
                                Utils.UnAuthorizationToken(requireActivity());
                            }
                        } else {
                            Utils.T(requireActivity(), message.message);
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
    }}