package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.databinding.ItemMockTestPackageBinding;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;

import java.util.ArrayList;


public class MockTestFragment extends Fragment  implements View.OnClickListener{
    Fragment fragment = MockTestFragment.this;
    FragmentMockTestBinding binding;
    ArrayList<String> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMockTestBinding.inflate(inflater, container, false);
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

        binding.rvMockTestList.setHasFixedSize(true);
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMockTestList.setAdapter(new PakageDetailMockTestFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }
}