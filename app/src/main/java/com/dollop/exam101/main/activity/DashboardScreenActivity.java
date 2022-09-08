package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.Basics.firebase.FirebaseService;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.databinding.NavHeaderDashboardBinding;

import java.util.Objects;


public class DashboardScreenActivity extends BaseActivity implements View.OnClickListener {

    public  NavController navController;
    public ActivityDashboardScreenBinding binding;
    AppBarConfiguration appBarConfiguration;
    Activity activity = DashboardScreenActivity.this;
    NavHeaderDashboardBinding navHeaderDashboardBinding;
    ApiService apiService;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();


    }

    @Override
    public void onBackPressed() {

        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.bottom_home){
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
       Utils.T(activity, getString(R.string.please_click_BACK_again_to_exit));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        } else if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.bottom_cart)
        {

            super.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        navHeaderDashboardBinding.tvName.setText(Utils.GetSession().studentName);
        navHeaderDashboardBinding.tvEmail.setText(Utils.GetSession().studentEmail);

        if (Utils.GetSession().profilePic != null && (!Utils.GetSession().profilePic.equals(""))) {
            Utils.Picasso(Utils.GetSession().profilePic, navHeaderDashboardBinding.ivProfile, R.drawable.dummy);
        }
    }

    @SuppressLint("ResourceAsColor")
    void init() {
        FirebaseService.GenerateToken(activity);
        navHeaderDashboardBinding = NavHeaderDashboardBinding.bind(binding.navigationView.getHeaderView(0));
        navigationSetup();
        apiService = RetrofitClient.getClient();
        binding.ivNavBar.setOnClickListener(this);
        //binding.ivProfile.setOnClickListener(this);
        binding.rvNotification.setOnClickListener(this);
         binding.ivSearch.setOnClickListener(this);
        navHeaderDashboardBinding.llHeader.setOnClickListener(this);
        navHeaderDashboardBinding.llLogout.setOnClickListener(this);
        navHeaderDashboardBinding.llAbout.setOnClickListener(this);
        navHeaderDashboardBinding.llSettings.setOnClickListener(this);
        navHeaderDashboardBinding.llBlogs.setOnClickListener(this);
        navHeaderDashboardBinding.llPrivacyPolicy.setOnClickListener(this);
        navHeaderDashboardBinding.llHome.setOnClickListener(this);
        navHeaderDashboardBinding.llMyPackage.setOnClickListener(this);
        // navHeaderDashboardBinding.llMyWishlist.setOnClickListener(this);
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
        binding.drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.status_bar));
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Utils.changeStatusBarColor(activity, R.color.transparent);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Utils.changeStatusBarColor(activity, R.color.status_bar);
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController,
                                             @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                switch (navDestination.getId()) {
                    case R.id.bottom_home:
                        navHeaderDashboardBinding.ivHome.setColorFilter(ContextCompat.getColor(activity, R.color.theme));
                        navHeaderDashboardBinding.tvHome.setTextColor(ContextCompat.getColor(activity, R.color.theme));
                        binding.bottomNavigationView.setVisibility(View.VISIBLE);
                        binding.appBarLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_category:
                    case R.id.bottom_packages:
                    case R.id.bottom_cart:
                        navHeaderDashboardBinding.ivHome.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                        navHeaderDashboardBinding.tvHome.setTextColor(ContextCompat.getColor(activity, R.color.black));
                        binding.bottomNavigationView.setVisibility(View.GONE);
                        binding.appBarLayout.setVisibility(View.GONE);
                        break;
                    default:
                        binding.bottomNavigationView.setVisibility(View.GONE);
                        binding.appBarLayout.setVisibility(View.GONE);
                        break;
                }

            }
        });

    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onClick(View view) {
        if (view == binding.ivNavBar) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }
        if (view == binding.rvNotification) {
            Utils.I(activity, NotificationActivity.class, null);
        }
        if (view == binding.ivNavBar) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }
        if (view == binding.ivSearch) {
            Utils.I(activity, SearchHistoryActivity.class, null);
        }

        if (view == binding.navigationView.getHeaderView(0)) {
            Utils.I(activity, ProfileActivity.class, null);
            binding.drawerLayout.close();
        }/*if (view == binding.ivProfile) {
            Utils.I(activity, ProfileActivity.class, null);
        }*/

        // Navigatin Drawer Click

        if (view == navHeaderDashboardBinding.llHome) {
            navController.navigate(R.id.bottom_home);
            binding.drawerLayout.close();
        }
        if (view == navHeaderDashboardBinding.llMyPackage) {
            Utils.I(activity, CourseListActivity.class, null);
        }

       /* if (view == navHeaderDashboardBinding.llMyWishlist) {
            Utils.I(activity, MyWishlistActivity.class, null);
            binding.drawerLayout.close();
        }*/
        if (view == navHeaderDashboardBinding.llBlogs) {
            binding.drawerLayout.close();
            Utils.I(activity, BlogsListActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llSettings) {
            binding.drawerLayout.close();
            Utils.I(activity, SettingActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llFaq) {

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
        }
        if (view == navHeaderDashboardBinding.llRaiseAComplant) {
            Utils.I(activity, RaiseComplaintActivity.class, null);
        }
        if (view == navHeaderDashboardBinding.llLogout) {
            Utils.logoutAlertDialog(activity);
        }
        if (view == navHeaderDashboardBinding.llFaq) {
            Utils.I(activity, FaqsActivity.class, null);
        }

    }


}