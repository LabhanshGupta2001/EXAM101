package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.adapter.PakageDetailRatingAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    Fragment fragment = CourseMaterialFragment.this;
    FragmentCourseMaterialBinding binding;
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;

    BottomSheetDialog bottomSheetDialog;

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

        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFirst.setAdapter(new PakageDetailPrimaryAdapter(getContext(), list));

        binding.rvRatingId.setHasFixedSize(true);
        binding.rvRatingId.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRatingId.setAdapter(new PakageDetailRatingAdapter(getContext(), list));

    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvRateId) {
            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_ratenow);
            bottomSheetDialog.show();
        }
    }
}