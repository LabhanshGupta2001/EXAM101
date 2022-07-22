package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


import com.dollop.exam101.Basics.UtilityTools.Utils;

import com.dollop.exam101.databinding.FragmentPriceBinding;

public class PriceFragment extends Fragment implements View.OnClickListener{
    Fragment fragment;
    FragmentPriceBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPriceBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();

    }

    private void init() {

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Utils.T(getActivity(),"seekbar progress "+progress );
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                binding.tvPricePopup.setVisibility(View.VISIBLE);
                binding.tvPricePopup.setText("₹" + progress);
                binding.tvPricePopup.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Utils.T(getActivity(),"seekbar touch started! ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Utils.T(getActivity(),"seekbar touch stopped!! ");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}