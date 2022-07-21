package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.databinding.ActivityCategoryDetailsBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.main.adapter.CategoryDetailAdapter;
import com.dollop.exam101.main.adapter.CategoryDetailSecondaryAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.ExamFilterFragment;
import com.dollop.exam101.main.fragment.LanguageFragment;
import com.dollop.exam101.main.fragment.PriceFragment;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = CategoryDetailsActivity.this;
    ActivityCategoryDetailsBinding binding;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    ApiService apiService;

    BottomSheetDialog bottomSheetFilter;
    BottomsheetFilterBinding bottomsheetFilterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        apiService = RetrofitClient.getClient();

        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvPackages.setAdapter(new CategoryDetailAdapter(activity, list));
        binding.rvPackages.setHasFixedSize(true);
        binding.rvPackages.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");

        binding.rvPackagesSecondary.setAdapter(new CategoryDetailSecondaryAdapter(activity, arrayList));
        binding.rvPackagesSecondary.setHasFixedSize(true);
        binding.rvPackagesSecondary.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onClick(View view) {

        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.tvFilter) {
            bottomSheetFilterTask();
        }
    }

    private void bottomSheetFilterTask() {
        bottomSheetFilter = new BottomSheetDialog(activity);
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetFilter.setContentView(bottomsheetFilterBinding.getRoot());

        bottomsheetFilterBinding.tvllSave.setOnClickListener(view ->
        {
            bottomSheetFilter.cancel();
        });

       /* BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomsheetFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
*/
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomsheetFilterBinding.getRoot().getParent()));
        bottomSheetFilter.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetFilter.show();

        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add("Exam");
        title.add("Price");
        title.add("Language");


        fragments.add(new ExamFilterFragment());
        fragments.add(new PriceFragment());
        fragments.add(new LanguageFragment());

        /*TabLayout tabLayout = bottomSheetFilter.findViewById(R.id.tlFilter);
        ViewPager2 viewPager2 = bottomSheetFilter.findViewById(R.id.vpLaunchId);*/
        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(bottomsheetFilterBinding.tlTabLayoutId, bottomsheetFilterBinding.ViewPagerId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();

        View tab1 = ((ViewGroup) bottomsheetFilterBinding.tlTabLayoutId.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();
    }

    private void getCategories() {
        apiService.getCategory("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    private void getCategoryDetails() {
        apiService.getCategoryDetails("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    private void getCategoryFilter() {
        apiService.getCategoryFilter("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
}