package com.dollop.exam101.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.main.fragment.CategoryHomeFragment;
import com.dollop.exam101.main.fragment.HomeFragment;
import com.dollop.exam101.main.fragment.PackageListFragment;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {

    Activity activity = DashboardScreenActivity.this;
    ActivityDashboardScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    void init() {
        binding.ivNavBar.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, new HomeFragment(), "SOMETAG").
                commit();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        binding.ivNavBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
               }
        });
        binding.ivProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==binding.ivNavBar)
        {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }

        if (view==binding.ivProfile)
        {
            Utils.I(activity,ProfileActivity.class,null);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId()==R.id.category)
        {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new CategoryHomeFragment(), "Category").
                    commit();
        }
        if (item.getItemId()==R.id.home)
        {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new HomeFragment(), "Home").
                    commit();
        }
        if (item.getItemId()==R.id.packages)
        {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new PackageListFragment(), "Package").
                    commit();
        }
        if(item.getItemId()==R.id.cart){
            Utils.I(activity,MyCartActivity.class,null);


        }
        return false;
    }
}