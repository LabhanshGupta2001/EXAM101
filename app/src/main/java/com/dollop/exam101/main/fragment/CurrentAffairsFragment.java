package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCurrentAffairsBinding;
import com.dollop.exam101.main.adapter.BlogsHomeAdapter;
import com.dollop.exam101.main.adapter.NewsAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.NewsModel;

import java.util.ArrayList;

public class CurrentAffairsFragment extends Fragment implements View.OnClickListener {
    ApiService apiService;
    Activity activity;
    public static FragmentCurrentAffairsBinding binding;
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();
    BlogsHomeAdapter blogsHomeAdapter;
    NewsAdapter newsAdapter;
    private int PageHeight=0;
//    public  int height=0;



    public CurrentAffairsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCurrentAffairsBinding.inflate(inflater,container,false);
        Utils.E("FragmentCurrentAffairsBinding");
        init();
        return binding.getRoot();

    }

    private void init() {
        newsModelArrayList.clear();
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));

        newsAdapter = new NewsAdapter(getActivity(), newsModelArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvCurrentAffairs.setLayoutManager(linearLayoutManager3);
        binding.rvCurrentAffairs.setAdapter(newsAdapter);

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (PageHeight==0)
                PageHeight = binding.getRoot().getHeight();
            }
        }.start();
    }


    @Override
    public void onClick(View v) {

    }
    @Override
    public void onResume() {
        super.onResume();
        if (PageHeight != 0) {
            binding.rvCurrentAffairs.setMinimumHeight(PageHeight);
            Utils.E("CountDownTimer:rvCurrentAffairs:" + PageHeight);
        }
    }
}