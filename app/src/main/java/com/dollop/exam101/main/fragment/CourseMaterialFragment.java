package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.main.adapter.OverviewCourseDetailsAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    Fragment fragment = CourseMaterialFragment.this;
    FragmentCourseMaterialBinding binding;
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseMaterialBinding.inflate(inflater, container, false);
        init();

        return binding.getRoot();
    }

    private void init() {
        binding.tvRateId.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvFirst.setNestedScrollingEnabled(false);
        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFirst.setAdapter(new PakageDetailPrimaryAdapter(getContext(), list));

        binding.rvOverView.setNestedScrollingEnabled(false);
        binding.rvOverView.setHasFixedSize(true);
        binding.rvOverView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvOverView.setAdapter(new OverviewCourseDetailsAdapter(getContext(), list));

        binding.rvRatingId.setNestedScrollingEnabled(false);
        binding.rvRatingId.setHasFixedSize(true);
        binding.rvRatingId.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRatingId.setAdapter(new PakageDetailRatingAdapter(getContext(), list));

    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvRateId) {
            bottomSheetDialog = new BottomSheetDialog(getContext());
            BottomSheetRatenowBinding bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
            bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());

            bottomSheetDialog.show();
            bottomSheetRatenowBinding.tvRateNow.setOnClickListener(view1 ->
            {
                String rating = "Rating is :" + bottomSheetRatenowBinding.rating.getRating();
                Utils.T(getContext(),"Rating: "+rating);
                bottomSheetDialog.cancel();
            });

            bottomSheetRatenowBinding.etShareThoughts.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    setupFullHeight(bottomSheetRatenowBinding);
                    return false;
                }
            });
        } else if (view == bottomSheetRatenowBinding.etShareThoughts){

        }
    }

    private void setupFullHeight(BottomSheetRatenowBinding bottomSheetRatenowBinding) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}