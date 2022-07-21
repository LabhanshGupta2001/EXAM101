package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.BottomSheetRatenowBinding;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.main.adapter.OverviewCourseDetailsAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentCourseMaterialBinding binding;
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;

    ApiService apiService;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetRatenowBinding bottomSheetRatenowBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseMaterialBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    private void init() {

        apiService= RetrofitClient.getClient();
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
            RatingBottomSheet();

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void RatingBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        BottomSheetRatenowBinding bottomSheetRatenowBinding = BottomSheetRatenowBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetRatenowBinding.getRoot());

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(((View) bottomSheetRatenowBinding.getRoot().getParent()));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        // bottomSheetBehavior.setHalfExpandedRatio(0.9f);
        // bottomSheetBehavior.setMaxHeight(binding.llChild.getHeight());
        bottomSheetBehavior.setSkipCollapsed(true);

        bottomSheetDialog.show();
        bottomSheetRatenowBinding.tvRateNow.setOnClickListener(view1 ->
        {
            String rating = "Rating is :" + bottomSheetRatenowBinding.rating.getRating();
            Utils.T(getContext(),"Rating: "+rating);
            bottomSheetDialog.cancel();
        });
       /* bottomSheetRatenowBinding.etShareThoughts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setupFullHeight(bottomSheetRatenowBinding);
                return false;
            }
        });*/
    }

    private void setupFullHeight(BottomSheetRatenowBinding bottomSheetRatenowBinding) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
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

    private void getPackageDetailsCourseMaterial(){
        apiService.getPackageDetailsCourseMaterial("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void GetPackageDetailsMockTestListRatingNow(){
        apiService.getPackageDetailsMockTestListRatingNow("").enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {

            }
        });
    }
}