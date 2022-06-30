package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.databinding.FragmentCategoryBinding;
import com.dollop.exam101.main.adapter.CategoryAdapter;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements View.OnClickListener{
    FragmentCategoryBinding binding;
    ArrayList<String> list = new ArrayList<>();
    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
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
        list.add("1");
        list.add("1");

        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategory.setAdapter(new CategoryAdapter(getContext(),list));
    }

    @Override
    public void onClick(View v) {

    }
}