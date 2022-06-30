package com.dollop.exam101.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.main.fragment.CategoryHomeFragment;
import com.dollop.exam101.main.fragment.HomeFragment;
import com.dollop.exam101.main.fragment.PackageListFragment;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, new HomeFragment(), "SOMETAG").
                commit();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.navigationView.setNavigationItemSelectedListener(this);

        binding.ivNavBar.setOnClickListener(this);
        binding.ivProfile.setOnClickListener(this);
        binding.ivNotification.setOnClickListener(this);
        binding.navigationView.getHeaderView(0).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivNavBar) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }

        if (view == binding.ivProfile) {
            Utils.I(activity, ProfileActivity.class, null);
        }
        if (view == binding.ivNotification) {
            Utils.I(activity, NotificationActivity.class, null);
        }
        if (view == binding.ivNavBar) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }

        if (view == binding.navigationView.getHeaderView(0)) {
            Utils.I(activity, ProfileActivity.class, null);
            binding.drawerLayout.close();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.category) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new CategoryHomeFragment(), "Category").
                    commit();
        }
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new HomeFragment(), "Home").
                    commit();
        }
        if (item.getItemId() == R.id.packages) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new PackageListFragment(), "Package").
                    commit();
        }
        if (item.getItemId() == R.id.cart) {
            Utils.I(activity, MyCartActivity.class, null);
        }
        if (item.getItemId() == R.id.nav_home) {
            binding.drawerLayout.close();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, new HomeFragment(), "Home Fragment").
                    commit();
        }
        if (item.getItemId() == R.id.nav_package) {
            Utils.I(activity, CourseListActivity.class, null);
            binding.drawerLayout.close();
        }
        if (item.getItemId() == R.id.nav_blogs) {
            binding.drawerLayout.close();
            Utils.I(activity, BlogsListActivity.class, null);
        }
        if (item.getItemId() == R.id.nav_setting) {
            binding.drawerLayout.close();
            Utils.I(activity, SettingActivity.class, null);
        }
        if (item.getItemId() == R.id.nav_faq) {

        }
        if (item.getItemId() == R.id.nav_contect_us) {
            binding.drawerLayout.closeDrawers();
            Utils.I(activity, ContactUsActivity.class, null);
        }
        if (item.getItemId() == R.id.nav_aboutus) {
            //  Utils.I(activity, .class, null);
        }
        if (item.getItemId() == R.id.nav_term_conditions) {
            // Utils.I(activity, .class, null);
        }
        if (item.getItemId() == R.id.nav_privacy_policy) {
            // Utils.I(activity, BlogsListActivity.class, null);
        }
        if (item.getItemId() == R.id.nav_header_profile) {
            Utils.I(activity, ProfileActivity.class, null);
            binding.drawerLayout.closeDrawers();
        }
        if (item.getItemId() == R.id.nav_raise_a_complaint) {
            Utils.I(activity, RaiseComplaintActivity.class, null);
            binding.drawerLayout.closeDrawers();
        }
        return false;
    }
}