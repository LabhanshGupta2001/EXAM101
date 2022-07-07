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
import com.dollop.exam101.databinding.NavHeaderDashboardBinding;
import com.dollop.exam101.main.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreenActivity extends AppCompatActivity implements View.OnClickListener{

    AppBarConfiguration appBarConfiguration;
    NavController navController;
    Activity activity = DashboardScreenActivity.this;
    ActivityDashboardScreenBinding binding;
    NavHeaderDashboardBinding navHeaderDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @SuppressLint("ResourceAsColor")
    void init() {
        navigationSetup();
        binding.ivNavBar.setOnClickListener(this);
        binding.ivProfile.setOnClickListener(this);
        binding.ivNotification.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);

        navHeaderDashboardBinding = navHeaderDashboardBinding.bind(binding.navigationView.getHeaderView(0));
        navHeaderDashboardBinding.llHeader.setOnClickListener(this);
        navHeaderDashboardBinding.llLogout.setOnClickListener(this);
        navHeaderDashboardBinding.llAbout.setOnClickListener(this);
        navHeaderDashboardBinding.llSettings.setOnClickListener(this);
        navHeaderDashboardBinding.llBlogs.setOnClickListener(this);
        navHeaderDashboardBinding.llPrivacyPolicy.setOnClickListener(this);
        navHeaderDashboardBinding.llHome.setOnClickListener(this);
        navHeaderDashboardBinding.llMyPackage.setOnClickListener(this);
        navHeaderDashboardBinding.llMyWishlist.setOnClickListener(this);
        navHeaderDashboardBinding.llFaq.setOnClickListener(this);
        navHeaderDashboardBinding.llContactUs.setOnClickListener(this);
        navHeaderDashboardBinding.llTermCondition.setOnClickListener(this);
        navHeaderDashboardBinding.llRaiseAComplant.setOnClickListener(this);

    }

    private void navigationSetup() {
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bottom_home, R.id.bottom_category, R.id.bottom_packages, R.id.bottom_cart)
                .build();

        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
      //  navHeaderDashboardBinding = navHeaderDashboardBinding.bind(binding.navigationView.getHeaderView(0));
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
        if (view == binding.ivSearch)
        {
            Utils.I(activity,SearchHistoryActivity.class,null);
        }

        if (view == binding.navigationView.getHeaderView(0)) {
            Utils.I(activity, ProfileActivity.class, null);
            binding.drawerLayout.close();
        }

        // Navigatin Drawer Click

        if (view == navHeaderDashboardBinding.llHome)
        {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainerView, new HomeFragment(), "Home").
                    commit();
            binding.drawerLayout.close();
        }
        if (view == navHeaderDashboardBinding.llMyPackage) {
            Utils.I(activity, CourseListActivity.class, null);
            binding.drawerLayout.close();
        }

        if (view == navHeaderDashboardBinding.llMyWishlist) {
            Utils.I(activity, MyWishlistActivity.class, null);
            binding.drawerLayout.close();
        }
        if (view == navHeaderDashboardBinding.llBlogs) {
            binding.drawerLayout.close();
            Utils.I(activity, BlogsListActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llSettings) {
            binding.drawerLayout.close();
            Utils.I(activity, SettingActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llFaq)
        {

        }
        if (view == navHeaderDashboardBinding.llContactUs) {
            binding.drawerLayout.closeDrawers();
            Utils.I(activity, ContactUsActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llAbout) {
             Utils.I(activity, AboutUsActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llTermCondition) {
              Utils.I(activity, TermAndConditionActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llPrivacyPolicy) {
             Utils.I(activity, PrivacyPolicyActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llHeader) {
            Utils.I(activity, ProfileActivity.class, null);
            binding.drawerLayout.closeDrawers();
        }
        if (view == navHeaderDashboardBinding.llRaiseAComplant) {
            Utils.I(activity, RaiseComplaintActivity.class, null);
            binding.drawerLayout.closeDrawers();
        }
        if (view == navHeaderDashboardBinding.llLogout)
        {
            Utils.I(activity,LoginActivity.class,null);

        }
        if (view == navHeaderDashboardBinding.llFaq)
        {
            Utils.I(activity,FaqsActivity.class,null);
        }

    }


}