package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dollop.exam101.databinding.ActivityAffiliatePurchaseListBinding;
import com.dollop.exam101.databinding.BottomSheetAffiliatePurchaseListBinding;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.fragment.PurchaseListFragment;
import com.dollop.exam101.main.fragment.TransactionHistoryFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class AffiliatePurchaseListActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = AffiliatePurchaseListActivity.this;
    ActivityAffiliatePurchaseListBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetAffiliatePurchaseListBinding bottomSheetAffiliatePurchaseListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAffiliatePurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);

        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add("Purchase List");
        title.add("Transaction History");

        fragments.add(new PurchaseListFragment());
        fragments.add(new TransactionHistoryFragment());


        /* TabLayout tabLayout = binding.tabLayout;
          ViewPager2 viewPager2 = binding.vpLaunchId;*/
          binding.vpLaunchId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack){
            onBackPressed();
        } else if (view == binding.tvFilter){
            bottomSheetFilterTask();
        }
    }

    private void bottomSheetFilterTask() {

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetAffiliatePurchaseListBinding = BottomSheetAffiliatePurchaseListBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetAffiliatePurchaseListBinding.getRoot());
        bottomSheetDialog.show();

    }
}