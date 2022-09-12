package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.IOnBackPressed;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentHomeBinding;
import com.dollop.exam101.main.activity.AllPackageActivity;
import com.dollop.exam101.main.activity.BlogDetailActivity;
import com.dollop.exam101.main.activity.PackagesDetailActivity;
import com.dollop.exam101.main.activity.DownloadHistory;
import com.dollop.exam101.main.adapter.BannerAdapter;
import com.dollop.exam101.main.adapter.BlogsHomeAdapter;
import com.dollop.exam101.main.adapter.CourseAdapter;
import com.dollop.exam101.main.adapter.NewsAdapter;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.BannerModel;
import com.dollop.exam101.main.model.CourseModel;
import com.dollop.exam101.main.model.HomeBannerOfferModel;
import com.dollop.exam101.main.model.NewsModel;
import com.dollop.exam101.main.model.PackageModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener, IOnBackPressed {
    private final Handler sliderHandler = new Handler();
    ApiService apiService;
    String Token, middleOneRedirection, middleTwoRedirection, bottomRedirection, bannerForMiddleOne, bannerForMiddleTwo, bannerForBottom;
    Activity activity;
    ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    FragmentHomeBinding binding;
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    ArrayList<HomeBannerOfferModel> banners1 = new ArrayList<>();
    ArrayList<BannerModel> bannerModels = new ArrayList<>();
    ArrayList<PackageModel> packageModelList = new ArrayList<>();
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    CurrentAffairsFragment currentAffairsFragment = new CurrentAffairsFragment();
    BlogsFragment blogsFragment = new BlogsFragment();
    CountDownTimer countDownTimer = null;
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.vpBanner.setCurrentItem(binding.vpBanner.getCurrentItem() + 1);
            if (binding.vpBanner.getCurrentItem() == bannerModels.size() - 1) {
                countDownTimer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        binding.vpBanner.setCurrentItem(0);
                    }
                }.start();
            }
        }
    };
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();
    BlogsHomeAdapter blogsHomeAdapter;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    BannerAdapter bannerAdapter;
    PackageAdapter packageAdapter;
    NewsAdapter newsAdapter;
    CourseAdapter courseAdapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();

        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        activity = requireActivity();
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fadein);
        binding.llMain.setAnimation(animation);
        apiService = RetrofitClient.getClient();
        Token = Utils.GetSession().token;
        Utils.E("hy");
        setViewPager();
        if (AppController.getInstance().isOnline()) {
            getExamList();
            getTopTen();
            getBlogs();
            getBanner();
            binding.tvViewAll.setOnClickListener(this);
        } else {
            InternetDialog();
        }
        courseModelArrayList.clear();

        courseAdapter = new CourseAdapter(getContext(), courseModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.recyclerViewCourse.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCourse.setAdapter(courseAdapter);

        packageAdapter = new PackageAdapter(getActivity(), packageModelList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvPackages.setLayoutManager(linearLayoutManager2);
        binding.rvPackages.setAdapter(packageAdapter);

        bannerAdapter = new BannerAdapter(requireActivity(), bannerModels);
        binding.vpBanner.setAdapter(bannerAdapter);
        binding.dot2.setViewPager2(binding.vpBanner);
        binding.vpBanner.setClipToPadding(false);
        binding.vpBanner.setClipChildren(false);
        binding.vpBanner.setOffscreenPageLimit(3);
        binding.vpBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        binding.vpBanner.setPageTransformer(compositePageTransformer);
        binding.vpBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // slide duration 3 seconds

            }
        });


        binding.middleBannerOne.setOnClickListener(this);
        binding.middleBannerTwo.setOnClickListener(this);
        binding.BottomBanner.setOnClickListener(this);

    }

    private void getBanner() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.getBannerList().enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        if (response.body().bannerList != null) {
                            binding.constraintLayout4.setVisibility(View.VISIBLE);
                            bannerModels.clear();
                            bannerModels.addAll(response.body().bannerList);
                            bannerAdapter.notifyDataSetChanged();
                        } else {
                            binding.constraintLayout4.setVisibility(View.GONE);
                        }

                        if (response.body().middleObj1 != null) {
                            binding.cvMiddleBannerOne.setVisibility(View.VISIBLE);
                            if (response.body().middleObj1.bannerFor.equals(getString(R.string.custom))) {
                                middleOneRedirection = response.body().middleObj1.bannerRedirectToId;
                            } else {
                                middleOneRedirection = response.body().middleObj1.redirectUuid;
                            }
                            bannerForMiddleOne = response.body().middleObj1.bannerFor;
                            Picasso.get().load(Const.Url.HOST_URL + response.body().middleObj1.bannerImage)
                                    .error(R.drawable.vpbannerimage).into(binding.middleBannerOne);
                        } else {
                            binding.cvMiddleBannerOne.setVisibility(View.GONE);
                        }

                        if (response.body().middleObj2 != null) {
                            binding.cvMiddleBannerTwo.setVisibility(View.VISIBLE);
                            if (response.body().middleObj2.bannerFor.equals(getString(R.string.custom))) {
                                middleTwoRedirection = response.body().middleObj2.bannerRedirectToId;
                            } else {
                                middleTwoRedirection = response.body().middleObj2.redirectUuid;

                            }

                            bannerForMiddleTwo = response.body().middleObj2.bannerFor;
                            Picasso.get().load(Const.Url.HOST_URL + response.body().middleObj2.bannerImage)
                                    .error(R.drawable.vpbannerimage).into(binding.middleBannerTwo);
                        } else {
                            binding.cvMiddleBannerTwo.setVisibility(View.GONE);
                        }

                        if (response.body().bottom != null) {
                            binding.llBottomBanner.setVisibility(View.VISIBLE);
                            if (response.body().bottom.bannerFor.equals(getString(R.string.custom))) {
                                bottomRedirection = response.body().bottom.bannerRedirectToId;
                            } else {
                                bottomRedirection = response.body().bottom.redirectUuid;
                            }
                            bannerForBottom = response.body().bottom.bannerFor;
                            Picasso.get().load(Const.Url.HOST_URL + response.body().bottom.bannerImage)
                                    .error(R.drawable.vpbannerimage).into(binding.BottomBanner);
                        } else {
                            binding.llBottomBanner.setVisibility(View.GONE);
                        }


                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(getContext(), message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(getContext(), message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }


    private void getExamList() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.Examlist(Token).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        courseModelArrayList.clear();
                        courseModelArrayList.addAll(response.body().examListModels);
                        courseAdapter.notifyDataSetChanged();
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(getContext(), message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(getContext(), message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

    void getTopTen() {
        Dialog progressDialog = Utils.initProgressDialog(requireActivity());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.Key.limit, "10");
        apiService.packageListItem(Utils.GetSession().token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        packageModelList.clear();
                        assert response.body() != null;
                        packageModelList.addAll(response.body().packageModels);
                        packageAdapter.notifyDataSetChanged();
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.UnAuthorizationToken(requireActivity());
                            }
                        } else {
                            Utils.T(requireActivity(), message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

    public void getBlogs() {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogsData(Constants.Key.blank).enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    Blogarraylist.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        if (response.body().blogs != null && !response.body().blogs.isEmpty()) {
                            Blogarraylist.addAll(response.body().blogs);
                            blogsFragment.Blogarraylist.clear();
                            blogsFragment.Blogarraylist.addAll(Blogarraylist);
                        } else {
                            fragments.remove(blogsFragment);
                            binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        }
                        viewPagerFragmentAdapter.notifyDataSetChanged();


                    } else {
                        assert response.errorBody() != null;
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                            Utils.UnAuthorizationToken(activity);
                            Utils.T(activity, message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

    private void setViewPager() {
        title.clear();
        fragments.clear();
        fragments.add(currentAffairsFragment);
        fragments.add(blogsFragment);
        title.add(Constants.Key.CurrentAffairs);
        title.add(Constants.Key.Blogs);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager(), getLifecycle(), fragments);
        binding.viewPagerHome.setAdapter(viewPagerFragmentAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> tab.setText(title.get(position))).attach();
        Utils.E("setViewPager");
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {

        binding.llMain.setBackgroundColor(getResources().getColor(R.color.white));
        binding.scrollView.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvMyDownloads.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                //    binding.scrollView.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);

            }
        });
        binding.noInternetConnection.tvMyDownloads.setOnClickListener(view -> {
                Utils.I(activity, DownloadHistory.class, null);
        });

    }

    private void bannerRedirection(String bannerFor, String redirectUuid) {

        if (redirectUuid != null && !redirectUuid.equals("")) {
            if (requireContext().getString(R.string.custom).equals(bannerFor)) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(redirectUuid));
                startActivity(i);
            } else if (requireContext().getString(R.string.blog).equals(bannerFor)) {
                Bundle blogBundle = new Bundle();
                blogBundle.putString(Constants.Key.uuid, redirectUuid);
                Utils.I(activity, BlogDetailActivity.class, blogBundle);
            } else if (requireContext().getString(R.string.packageBanner).equals(bannerFor)) {
                Bundle packageBundle = new Bundle();
                packageBundle.putString(Constants.Key.packageUuId, redirectUuid);
                Utils.I(activity, PackagesDetailActivity.class, packageBundle);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvViewAll) {
            Utils.I(activity, AllPackageActivity.class, null);
            // ((DashboardScreenActivity) activity).binding.bottomNavigationView.setSelectedItemId(R.id.bottom_packages);
            Utils.I(activity, AllPackageActivity.class, null);
        } else if (view == binding.middleBannerOne) {
            bannerRedirection(bannerForMiddleOne, middleOneRedirection);
        } else if (view == binding.middleBannerTwo) {
            bannerRedirection(bannerForMiddleTwo, middleTwoRedirection);
        } else if (view == binding.BottomBanner) {
            bannerRedirection(bannerForBottom, bottomRedirection);
            // ((DashboardScreenActivity) activity).binding.bottomNavigationView.setSelectedItemId(R.id.bottom_packages);
        }

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public boolean onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            return true;
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.T(activity, getString(R.string.please_click_BACK_again_to_exit));
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
        return true;
    }


}