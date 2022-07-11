package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityRaiseComplaintFormBinding;

public class RaiseComplaintFormActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = RaiseComplaintFormActivity.this;
    ActivityRaiseComplaintFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llHigh.setOnClickListener(this);
        binding.llNormal.setOnClickListener(this);
        binding.llCritical.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llSave) {
            finish();
        } else if (view == binding.llNormal) {
            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image.setVisibility(View.VISIBLE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        } else if (view == binding.llHigh) {
            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image2.setVisibility(View.VISIBLE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


        } else if (view == binding.llCritical) {
            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image3.setVisibility(View.VISIBLE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        }
    }
}