package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class PackagesDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = PackagesDetailActivity.this;
    ActivityPackagesDetailBinding binding;
    /* ItemPakagesDetailsSecondryBinding itemPakagesDetailsSecondryBinding;*/
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;


    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackagesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        list.clear();
        list.add("1");
        list.add("1");


        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvFirst.setAdapter(new PakageDetailPrimaryAdapter(activity, list));


        binding.rvRatingId.setHasFixedSize(true);
        binding.rvRatingId.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvRatingId.setAdapter(new PakageDetailRatingAdapter(activity, list));

    }

    private void init() {
      /*  itemPakagesDetailsSecondryBinding.ivRotedArrow.setOnClickListener(this);
        binding.llLogoutDevice.setOnClickListener(this);*/
        binding.tvRateId.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        /*if (view == binding.llLogoutDevice) {
            if (!dropdown) {
                binding.llLogoutDevice.setVisibility(View.VISIBLE);
               itemPakagesDetailsSecondryBinding.ivRotedArrow.animate().rotation(180).setDuration(100).start();
               return;
            } else {
               binding.llLogoutDevice.setVisibility(View.GONE);
                itemPakagesDetailsSecondryBinding.ivRotedArrow.animate().rotation(0).setDuration(100).start();
                return;
            }
        }*/


        if (view == binding.tvRateId) {
            bottomSheetDialog = new BottomSheetDialog(activity);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_ratenow);
            /* bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);*/
            bottomSheetDialog.show();
        }
    }
}