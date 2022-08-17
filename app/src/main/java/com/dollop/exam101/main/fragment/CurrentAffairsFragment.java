package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.databinding.FragmentCurrentAffairsBinding;
import com.dollop.exam101.databinding.FragmentHomeBinding;
import com.dollop.exam101.main.adapter.BlogsHomeAdapter;
import com.dollop.exam101.main.adapter.NewsAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.NewsModel;

import java.util.ArrayList;

public class CurrentAffairsFragment extends Fragment implements View.OnClickListener {
    ApiService apiService;
    Activity activity;
    public FragmentCurrentAffairsBinding binding;
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();
    BlogsHomeAdapter blogsHomeAdapter;
    NewsAdapter newsAdapter;

    public CurrentAffairsFragment(ArrayList<AllBlogListModel> blogListModels) {
        this.Blogarraylist.addAll(blogListModels);
        binding.rvCurrentAffairs.setVisibility(View.GONE);
    }

    public CurrentAffairsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCurrentAffairsBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();

    }

    private void init() {
        newsModelArrayList.clear();
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));
        newsModelArrayList.add(new NewsModel(String.valueOf(getResources().getString(R.string.covid19)), String.valueOf(getResources().getString(R.string.tens_of_thousands_of_people_have_been_marching_in_the_belgain)),
                String.valueOf(getResources().getString(R.string._12th_april)), String.valueOf(getResources().getString(R.string._09_20_pm)), R.drawable.maskimg));

        newsAdapter = new NewsAdapter(getActivity(), newsModelArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvCurrentAffairs.setLayoutManager(linearLayoutManager3);
        binding.rvCurrentAffairs.setAdapter(newsAdapter);

        blogsHomeAdapter = new BlogsHomeAdapter(getActivity(), Blogarraylist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvBlogs.setLayoutManager(linearLayoutManager);
        binding.rvBlogs.setAdapter(blogsHomeAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}