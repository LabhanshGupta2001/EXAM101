package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.main.fragment.HomeFragment;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=DashboardScreenActivity.this;
    ActivityDashboardScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, new HomeFragment(), "SOMETAG").
                commit();
        init();
    }
    private  void init(){
        binding.rlNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==binding.rlNotification){
            Utils.I(activity,NotificationActivity.class,null);
        }
    }
}