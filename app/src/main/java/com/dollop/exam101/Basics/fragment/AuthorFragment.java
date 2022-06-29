package com.dollop.exam101.Basics.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.Basics.adapter.AuthorHistoryAdapter;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentAuthorBinding;

import java.util.ArrayList;

public class AuthorFragment extends Fragment implements View.OnClickListener{
    FragmentAuthorBinding binding;
    ArrayList<String> list = new ArrayList<>();
    public AuthorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthorBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();
    }

    private void init() {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvAuthorSearch.setHasFixedSize(true);
        binding.rvAuthorSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAuthorSearch.setAdapter(new AuthorHistoryAdapter(getContext(), list));
    }

    @Override
    public void onClick(View view) {
    }
}