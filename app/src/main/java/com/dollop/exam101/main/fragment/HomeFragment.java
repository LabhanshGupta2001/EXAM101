package com.dollop.exam101.main.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentHomeBinding;
import com.dollop.exam101.main.adapter.BannerAdapter;
import com.dollop.exam101.main.adapter.CourseAdapter;
import com.dollop.exam101.main.adapter.NewsAdapter;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseModel;
import com.dollop.exam101.main.model.HomeBannerOfferModel;
import com.dollop.exam101.main.model.NewsModel;
import com.dollop.exam101.main.model.Package;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private final Handler sliderHandler = new Handler();
    ApiService apiService;
    FragmentHomeBinding binding;
    Fragment fragment = HomeFragment.this;
    String Token;
    ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
    ArrayList<HomeBannerOfferModel> banners1 = new ArrayList<>();
    ArrayList<Package> packageList = new ArrayList<>();
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    CountDownTimer countDownTimer = null;
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.vpBanner.setCurrentItem(binding.vpBanner.getCurrentItem() + 1);
            if (binding.vpBanner.getCurrentItem() == banners1.size() - 1) {
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
    BannerAdapter bannerAdapter;
    PackageAdapter packageAdapter;
    NewsAdapter newsAdapter;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        Token = Utils.GetSession().token;
        //getOfferBannerByUser();
        getTopTen();
        courseModelArrayList.clear();
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "String"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.user_profile, "Hello"));
        // Add the following lines to create RecyclerView
        CourseAdapter adapter = new CourseAdapter(getContext(), courseModelArrayList);
        //adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.recyclerViewCourse.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCourse.setAdapter(adapter);



        // Banner Code
        banners1.clear();
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        banners1.add(new HomeBannerOfferModel(R.drawable.vpbannerimage));
        bannerAdapter = new BannerAdapter(getActivity(), banners1);
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
        newsModelArrayList.clear();
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

        // Calendar View Code...
        //binding.cvCalendar.setPointerIcon();

    }

    void getBanner() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getBanner(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    void getUser() {
        HashMap<String, String> hm = new HashMap<>();
        apiService.getUser(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }

    void getTopTen() {
        Dialog progressDialog = Utils.initProgressDialog(requireActivity());
        apiService.packageListItem(Token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        packageList.clear();
                       // Bundle bundle = new Bundle();
                        assert response.body() != null;
                        packageList.addAll(response.body().packages);

                        packageAdapter = new PackageAdapter(getActivity(), packageList);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        binding.rvPackages.setLayoutManager(linearLayoutManager2);
                        binding.rvPackages.setAdapter(packageAdapter);

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                                Utils.T(requireActivity(), message.message);
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
}