package com.dollop.exam101.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.databinding.ActivityForgotPasswordBinding;
import com.dollop.exam101.main.fragment.HomeFragment;
import com.dollop.exam101.main.fragment.PackageListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener {
    Activity  activity=DashboardScreenActivity.this;
    ActivityDashboardScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, new PackageListFragment(), "SOMETAG").
                commit();


        init();
    }
    private void init(){
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if (item.getItemId()==R.id.Cart)
                {
                    Utils.I(activity,MyCartActivity.class,null);
                }
                            return false;
            }
        });

    }
    @Override
    public void onClick(View view) {

    }
}
