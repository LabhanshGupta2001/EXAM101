package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.main.adapter.ExamFragmentAdapter;
import com.dollop.exam101.main.adapter.PakageDetailMockTestFragmentAdapter;

import java.util.ArrayList;

public class ExamFilterFragment extends Fragment implements View.OnClickListener{
    Fragment fragment = ExamFilterFragment.this;
    FragmentExamFilterBinding binding;
    ArrayList<String> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamFilterBinding.inflate(inflater, container, false);
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

        binding.rvExam.setHasFixedSize(true);
        binding.rvExam.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExam.setAdapter(new ExamFragmentAdapter(getContext(), list));

    }

    @Override
    public void onClick(View v) {

    }
}