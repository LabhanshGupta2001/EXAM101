package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.databinding.FragmentAllResultsBinding;
import com.dollop.exam101.main.adapter.MockHistoryAllResultsAdapter;

import java.util.ArrayList;


public class AllResultsFragment extends Fragment implements View.OnClickListener {
    Fragment fragment=AllResultsFragment.this;
    FragmentAllResultsBinding binding;
    ArrayList<String>list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllResultsBinding.inflate(inflater, container, false);
        init();

        return  binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");


        binding.rvFragmentAllResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFragmentAllResults.setHasFixedSize(true);
        binding.rvFragmentAllResults.setAdapter(new MockHistoryAllResultsAdapter(getContext(),list));
    }

    @Override
    public void onClick(View view) {

    }
}