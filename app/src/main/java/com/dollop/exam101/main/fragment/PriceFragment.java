package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentPriceBinding;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class PriceFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentPriceBinding binding;
    Activity  activity;
    public String minValue = "", maxValue = "";
    OnItemClicked onItemClicked;
    private String From;

    public PriceFragment(String Key, OnItemClicked onItemClicked) {
        From = Key;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPriceBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();

    }

    private void init() {

        binding.rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object MinValue, Object MaxValue) {
                Utils.T(getActivity(), "seekbar progress " + MinValue);
                Utils.T(getActivity(), "seekbar progress " + MaxValue);

                binding.tvStartReat.setVisibility(View.VISIBLE);
                binding.tvStartReatTwo.setVisibility(View.VISIBLE);

                binding.tvStartReat.setText("₹" + MinValue);
                binding.tvStartReatTwo.setText("₹" + MaxValue);

                minValue = binding.tvStartReat.getText().toString().trim();
                maxValue = binding.tvStartReatTwo.getText().toString().trim();

                Utils.E("minvalue;;"+minValue);
                Utils.E("maxValue;;"+maxValue);

            }
        });
        RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(activity);
        seekBar.setRangeValues(500, 5000);
        seekBar.setSelectedMinValue(500);
        seekBar.setSelectedMaxValue(5000);

        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

            }
        });
        seekBar.setNotifyWhileDragging(true);
    }

    @Override
    public void onClick(View v) {

    }
}