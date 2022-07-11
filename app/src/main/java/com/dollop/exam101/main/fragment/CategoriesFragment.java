package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCategoriesBinding;
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.main.adapter.CategoriesFragmentAdapter;
import com.dollop.exam101.main.adapter.ExamFragmentAdapter;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment implements View.OnClickListener{
    Fragment fragment = CategoriesFragment.this;
    FragmentCategoriesBinding binding;
    ArrayList<String> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
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

        binding.rvCategoriesItem.setHasFixedSize(true);
        binding.rvCategoriesItem.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategoriesItem.setAdapter(new CategoriesFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }
}