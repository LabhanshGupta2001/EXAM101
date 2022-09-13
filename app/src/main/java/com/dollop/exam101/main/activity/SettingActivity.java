package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = SettingActivity.this;
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.GetSession().isPasswordGenerated.equals(Constants.Key.Yes)) {
            binding.tvChangePassword.setText(Constants.Key.SetPassword);
        }
        binding.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.T(activity, "Notifications are turned on");
                }else {
                    Utils.T(activity, "Notifications are turned off");
                }
            }
        });
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llLoginHistory.setOnClickListener(this);
        binding.llChangePassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llLoginHistory) {
            Utils.I(activity, LoginHistoryActivity.class, null);
        } else if (view == binding.llChangePassword) {
            Utils.I(activity, ChangePasswordActivity.class, null);
        }

    }
}