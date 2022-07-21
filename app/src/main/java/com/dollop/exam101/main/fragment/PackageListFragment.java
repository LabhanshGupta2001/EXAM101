package com.dollop.exam101.main.fragment;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.databinding.FragmentPackageListBinding;
import com.dollop.exam101.databinding.ItemBlogsHorizontalBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.PackageModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

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
    private String[] labels = new String[]{"Exams","Price","Language"};


    BottomSheetDialog bottomSheetDialog;
    ArrayList<PackageModel> packageList = new ArrayList<>();
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
        token = Utils.GetSession().token;
        packageList();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvFilterId) {
            bottomsheetTask();
        }
    }

    private void bottomsheetTask() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomsheetFilterBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomsheetFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
    void packageList() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.packageList(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}