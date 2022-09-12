package com.dollop.exam101.main.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentHomeBinding;
import com.dollop.exam101.main.activity.AllPackageActivity;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
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
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private final Handler sliderHandler = new Handler();
    ApiService apiService;
    String Token;
    Activity activity;
    FragmentHomeBinding binding;
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    ArrayList<HomeBannerOfferModel> banners1 = new ArrayList<>();
    ArrayList<BannerModel> bannerModels = new ArrayList<>();
    ArrayList<PackageModel> packageModelList = new ArrayList<>();
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    CountDownTimer countDownTimer = null;
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();
    BlogsHomeAdapter blogsHomeAdapter;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    BannerAdapter bannerAdapter;
    PackageAdapter packageAdapter;
    NewsAdapter newsAdapter;
    CourseAdapter courseAdapter;


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

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        activity = requireActivity();
        apiService = RetrofitClient.getClient();
        Token = Utils.GetSession().token;
        if (AppController.getInstance().isOnline()) {
            getExamList();
            getTopTen();
            getBlogs();
            getBanner();
            binding.tvViewAll.setOnClickListener(this);
        } else {
            //Utils.InternetDialog(activity);
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


       /* banners1.clear();
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        */
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


        // News Recyclerview code....

     /*   newsModelArrayList.clear();
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));

        newsAdapter = new NewsAdapter(getActivity(), newsModelArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvNews.setLayoutManager(linearLayoutManager3);
        binding.rvNews.setAdapter(newsAdapter);
*/
        // Calendar View Code...
        //binding.cvCalendar.setPointerIcon();

    }

    private void getBanner(){
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.getBannerLit().enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        bannerModels.clear();
                        bannerModels.addAll(response.body().bannerModelList);
                        bannerAdapter.notifyDataSetChanged();
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
        hashMap.put(Constants.Key.limit,"10");
        apiService.packageListItem(Utils.GetSession().token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                   /* if (response.body().packageModels.isEmpty()) {
                        binding.rvPackages.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvPackagesone.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }*/
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
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    Blogarraylist.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Blogarraylist.addAll(response.body().blogs);
                        title.clear();
                        title.add(Constants.Key.CurrentAffairs);
                        fragments.add(new CurrentAffairsFragment());

                        if (Blogarraylist.isEmpty() || Blogarraylist.equals(Constants.Key.blank)) {
                        } else {

                            title.add(Constants.Key.Blogs);
                            fragments.add(new BlogsFragment(Blogarraylist));
                        }

                        binding.viewPagerHome.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), getLifecycle(), fragments));

                        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> {
                            tab.setText(title.get(position));
                        }).attach();


                    } else {
                        // assert response.errorBody() != null;
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.UnAuthorizationToken(activity);
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

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {

        binding.llMain.setBackgroundColor(getResources().getColor(R.color.white));
        binding.scrollView.setVisibility(View.GONE);
        binding.noInternetConnection.llParentNoInternet.setVisibility(View.VISIBLE);
        binding.noInternetConnection.tvRetry.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                binding.scrollView.setVisibility(View.VISIBLE);
                binding.noInternetConnection.llParentNoInternet.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvViewAll) {
            Utils.I(activity, AllPackageActivity.class,null);
           // ((DashboardScreenActivity) activity).binding.bottomNavigationView.setSelectedItemId(R.id.bottom_packages);
        }
    }
}