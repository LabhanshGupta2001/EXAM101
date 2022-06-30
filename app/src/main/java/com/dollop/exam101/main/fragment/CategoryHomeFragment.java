package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.databinding.FragmentCategoryHomeBinding;
import com.dollop.exam101.main.adapter.CategoryHomeAdapter;

import java.util.ArrayList;

public class CategoryHomeFragment extends Fragment implements View.OnClickListener{
    Fragment fragment=CategoryHomeFragment.this;
    FragmentCategoryHomeBinding binding;
    ArrayList<String> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentCategoryHomeBinding.inflate(inflater, container, false);
        init();

        return binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");


        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategories.setHasFixedSize(true);
        binding.rvCategories.setAdapter(new CategoryHomeAdapter(getContext(),list));
    }


    @Override
    public void onClick(View view) {

    }
}