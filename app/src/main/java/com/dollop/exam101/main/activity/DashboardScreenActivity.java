package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.main.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    AppBarConfiguration appBarConfiguration;
    NavController navController;
    Activity activity = DashboardScreenActivity.this;
    ActivityDashboardScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @SuppressLint("ResourceAsColor")
    void init() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        binding.navigationView.setNavigationItemSelectedListener(this);
       // changeNavigatioDrawerColor();

        binding.ivNavBar.setOnClickListener(this);
        binding.ivProfile.setOnClickListener(this);
        binding.ivNotification.setOnClickListener(this);
        binding.navigationView.getHeaderView(0).setOnClickListener(this);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bottom_home, R.id.bottom_category, R.id.bottom_packages, R.id.bottom_cart)
                .build();

        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


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
        if (item.getItemId() == R.id.nav_home) {
            binding.bottomNavigationView.setSelectedItemId(R.id.home);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainerView, new HomeFragment(), "Home").
                    commit();
            binding.drawerLayout.close();
        }
/*        if (item.getItemId() == R.id.nav_package) {
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
        }*/
        return false;
    }

    void changeNavigatioDrawerColor() {
        binding.navigationView.getMenu().getItem(0).getSubMenu().getItem(0);
        SpannableString spanString = new SpannableString(binding.navigationView.getMenu().getItem(0).getSubMenu().getItem(0).getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)), 0, spanString.length(), 0);
        binding.navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setTitle(spanString);
        binding.navigationView.getMenu().getItem(0).getSubMenu().getItem(0);

        binding.navigationView.getMenu().getItem(1).getSubMenu().getItem(1);
        SpannableString spanString2 = new SpannableString(binding.navigationView.getMenu().getItem(1).getSubMenu().getItem(6).getTitle().toString());
        spanString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)), 0, spanString2.length(), 0);
        binding.navigationView.getMenu().getItem(1).getSubMenu().getItem(6).setTitle(spanString2);
        binding.navigationView.getMenu().getItem(1).getSubMenu().getItem(6);
    }
}