package com.dollop.exam101.main.fragment;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.app.Dialog;
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
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.databinding.FragmentPackageListBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.Package;
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
    Fragment fragment=PackageListFragment.this;
    ApiService apiService;
    String token;

    BottomsheetFilterBinding bottomsheetFilterBinding;
    ViewPagerFragmentAdapter adapter;
    private String[] labels = new String[]{"Categories","Price","Language"};


    BottomSheetDialog bottomSheetDialog;
    ArrayList<Package> packageList = new ArrayList<>();
    PackageAdapter packageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackageListBinding.inflate(inflater, container, false);

        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(inflater,container,false);
        init();
       return binding.getRoot();
    }


    private void init() {
        apiService = RetrofitClient.getClient();
        token =Utils.GetSession().token;
        packageList();

      /*  packageList.add(new PackageModel("MS Power Point", "MS Office, Advance Power point, Animated Slides"));
        packageList.add(new PackageModel("Digital Design Thinking", "Graphic Design, Adobe software, indesgin, figma, in... Slides"));
        packageList.add(new PackageModel("Creative Express", "Adobe XD, Creative Suit, Adobe Premier, Phtoshop C...Power point, Animated Slides"));
        packageList.add(new PackageModel("Creative Art Design", "Banner Design, Logo design,Posters"));


        packageList.add(new PackageModel("MS Power Point", "MS Office, Advance Power point, Animated Slides"));
        packageList.add(new PackageModel("Digital Design Thinking", "Graphic Design, Adobe software, indesgin, figma, in... Slides"));
        packageList.add(new PackageModel("Creative Express", "Adobe XD, Creative Suit, Adobe Premier, Phtoshop C...Power point, Animated Slides"));
        packageList.add(new PackageModel("Creative Art Design", "Banner Design, Logo design,Posters"));

        packageAdapter = new PackageAdapter(getActivity(), packageList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvPackagesone.setLayoutManager(linearLayoutManager2);
        binding.rvPackagesone.setAdapter(packageAdapter);
        binding.mcvFilterId.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvFilterId) {
            bottomSheetTask();
        }
    }

    private void bottomSheetTask() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
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
        {
            bottomSheetDialog.cancel();
        });

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
                        packageList.clear();
                        // Bundle bundle = new Bundle();
                        assert response.body() != null;
                        packageList.addAll(response.body().packages);

                        packageAdapter = new PackageAdapter(getActivity(), packageList);
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
    }
}