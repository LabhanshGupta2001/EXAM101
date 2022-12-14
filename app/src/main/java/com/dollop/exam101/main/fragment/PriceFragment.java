package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentPriceBinding;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class PriceFragment extends Fragment implements View.OnClickListener {
    public String minValue = "", maxValue = "";
    Fragment fragment;
    FragmentPriceBinding binding;
    Activity activity;
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

        rangeSlider();

    }
    private void rangeSlider() {
        binding.rangeSeekbar.setValues(500.0f, 5000.0f);
        binding.rangeSeekbar.setMinSeparation(50.0f);
        binding.rangeSeekbar.setTrackHeight(14);
        binding.rangeSeekbar.setCustomThumbDrawable(R.drawable.range_thumb);
        binding.rangeSeekbar.setThumbRadius(20);
        binding.rangeSeekbar.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                currencyFormat.setCurrency(Currency.getInstance("INR"));
                currencyFormat.setMaximumFractionDigits(0);
                return currencyFormat.format(value);
            }
        });
        binding.rangeSeekbar.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                minValue = values.get(0).toString();
                maxValue = values.get(1).toString();
                Utils.E("minValue::::" + minValue);
                Utils.E("maxValue::::" + maxValue);
                binding.tvStartReat.setText("???" + String.format("%.0f", values.get(0)));
                binding.tvStartReatTwo.setText("???" + String.format("%.0f", values.get(1)));
            }
        });
    }
    @Override
    public void onClick(View v) {

    }
}