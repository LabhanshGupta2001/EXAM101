package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentPurchaseListBinding;
import com.dollop.exam101.databinding.FragmentTransactionHistoryBinding;
import com.dollop.exam101.main.adapter.PurchaseListAdapter;
import com.dollop.exam101.main.adapter.TransactionHistoryAdapter;

import java.util.ArrayList;

public class TransactionHistoryFragment extends Fragment implements View.OnClickListener {
    FragmentTransactionHistoryBinding binding;
    ArrayList<String> list = new ArrayList<>();

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater,container,false);
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
        list.add("1");


        binding.rvTransactionHistory.setHasFixedSize(true);
        binding.rvTransactionHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTransactionHistory.setAdapter(new TransactionHistoryAdapter(getContext(),list));
    }

    @Override
    public void onClick(View v) {

    }
}