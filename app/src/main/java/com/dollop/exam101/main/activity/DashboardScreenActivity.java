package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.IOnBackPressed;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.Basics.firebase.FirebaseService;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityDashboardScreenBinding;
import com.dollop.exam101.databinding.NavHeaderDashboardBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;


public class DashboardScreenActivity extends BaseActivity implements View.OnClickListener {

    public NavController navController;
    public ActivityDashboardScreenBinding binding;
    AppBarConfiguration appBarConfiguration;
    Activity activity = DashboardScreenActivity.this;
    NavHeaderDashboardBinding navHeaderDashboardBinding;
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();


    }

    @Override
    public void onBackPressed() {
        Fragment fragment = Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment())
                .getChildFragmentManager().getFragments().get(0);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
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
        navHeaderDashboardBinding.llDownloadHistory.setOnClickListener(this);
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
                R.id.bottom_home, R.id.bottom_category, R.id.bottom_packages, R.id.bottom_cart).build();
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


        NavOptions options = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.in_from_left)
                .setExitAnim(R.anim.lefttoright)
                .setPopEnterAnim(R.anim.in_from_left)
                .setPopExitAnim(R.anim.lefttoright)
                .build();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        navController.navigate(R.id.bottom_home, null, options);
                        break;
                    case R.id.bottom_category:
                        navController.navigate(R.id.bottom_category, null, options);
                        break;
                    case R.id.bottom_packages:
                        navController.navigate(R.id.bottom_packages, null, options);
                        break;
                    case R.id.bottom_cart:
                        navController.navigate(R.id.bottom_cart, null, options);
                        break;
                    default:
                        binding.bottomNavigationView.setVisibility(View.GONE);
                        break;
                }
                return false;
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
//                        binding.bottomNavigationView.setVisibility(View.VISIBLE);
                        // binding.appBarLayout.setVisibility(View.VISIBLE);
                        binding.llNotification.setVisibility(View.VISIBLE);
                        binding.ivLogo.setVisibility(View.VISIBLE);
                        binding.tvHeading.setVisibility(View.GONE);

                        break;
                    case R.id.bottom_category:
                        binding.llNotification.setVisibility(View.GONE);
                        binding.ivLogo.setVisibility(View.GONE);
                        binding.tvHeading.setVisibility(View.VISIBLE);
                        binding.tvHeading.setText(R.string.my_exam);
                        break;
                    case R.id.bottom_packages:
                        binding.llNotification.setVisibility(View.GONE);
                        binding.ivLogo.setVisibility(View.GONE);
                        binding.tvHeading.setVisibility(View.VISIBLE);
                        binding.tvHeading.setText(R.string.mock_test);

                        break;
                    case R.id.bottom_cart:
                        navHeaderDashboardBinding.ivHome.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                        navHeaderDashboardBinding.tvHome.setTextColor(ContextCompat.getColor(activity, R.color.black));
                        binding.llNotification.setVisibility(View.GONE);
                        binding.ivLogo.setVisibility(View.GONE);
                        binding.tvHeading.setVisibility(View.VISIBLE);
                        binding.tvHeading.setText(R.string.cart);


                        // binding.appBarLayout.setVisibility(View.GONE);
//                        binding.bottomNavigationView.setVisibility(View.GONE);

                        break;
                    default:
                        // binding.appBarLayout.setVisibility(View.GONE);
//                        binding.bottomNavigationView.setVisibility(View.GONE);

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
        if (view == navHeaderDashboardBinding.llDownloadHistory) {
            binding.drawerLayout.close();
            Utils.I(activity, DownloadHistory.class, null);
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