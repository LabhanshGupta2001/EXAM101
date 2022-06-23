package com.dollop.exam101.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dollop.exam101.R;
import com.dollop.exam101.adapter.BannerAdapter;
import com.dollop.exam101.adapter.CourseAdapter;
import com.dollop.exam101.adapter.PackageAdapter;
import com.dollop.exam101.databinding.FragmentHomeBinding;
import com.dollop.exam101.model.CourseModel;
import com.dollop.exam101.model.HomeBannerOfferModel;
import com.dollop.exam101.model.NewsModel;
import com.dollop.exam101.model.PackageModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<CourseModel> courseModelArrayList=new ArrayList<>();
    ArrayList<HomeBannerOfferModel> banners1 = new ArrayList<>();
    ArrayList<PackageModel> packageList = new ArrayList<>();
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    CountDownTimer countDownTimer = null;
    private final Handler sliderHandler = new Handler();
    BannerAdapter bannerAdapter;
    PackageAdapter packageAdapter;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


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


    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        init();

        return binding.getRoot();

    }
    private void init() {

        //getOfferBannerByUser();
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"String"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        courseModelArrayList.add(new CourseModel(R.drawable.persionimg,"Hello"));
        // Add the following lines to create RecyclerView
        CourseAdapter adapter = new CourseAdapter(getContext(), courseModelArrayList);
        //adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        binding.recyclerViewCourse.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCourse.setAdapter(adapter);


        // Banner Code
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

        // Top Package Code....

        packageList.add(new PackageModel("MS Power Point","MS Office, Advance Power point, Animated Slides"));
        packageList.add(new PackageModel("Digital Design Thinking","Graphic Design, Adobe software, indesgin, figma, in... Slides"));
        packageList.add(new PackageModel("Creative Express","Adobe XD, Creative Suit, Adobe Premier, Phtoshop C...Power point, Animated Slides"));
        packageList.add(new PackageModel("Creative Art Design","Banner Design, Logo design,Posters"));

        packageAdapter = new PackageAdapter(getActivity(),packageList);
        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        binding.rvPackages.setLayoutManager(linearLayoutManager2);
        binding.rvPackages.setAdapter(packageAdapter);

        // News Recyclerview code....

//        newsModelArrayList.add(new NewsModel());



}}