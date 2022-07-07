package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCategoryBinding;
import com.dollop.exam101.databinding.FragmentPurchaseListBinding;
import com.dollop.exam101.main.adapter.CategoryAdapter;
import com.dollop.exam101.main.adapter.PurchaseListAdapter;

import java.util.ArrayList;


public class PurchaseListFragment extends Fragment implements View.OnClickListener {
    FragmentPurchaseListBinding binding;
    ArrayList<String> list = new ArrayList<>();

    public PurchaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();
    }

    private void init(){
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvPurchaseList.setHasFixedSize(true);
        binding.rvPurchaseList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPurchaseList.setAdapter(new PurchaseListAdapter(getContext(),list));
    }

    @Override
    public void onClick(View v) {

    }
}