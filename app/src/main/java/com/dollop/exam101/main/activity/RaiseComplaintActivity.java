package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityRaiseComplaintBinding;
import com.dollop.exam101.main.fragment.adapter.RaiseComplaintAdapter;

import java.util.ArrayList;

public class RaiseComplaintActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = RaiseComplaintActivity.this;
    ActivityRaiseComplaintBinding binding;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.mcvAdd.setOnClickListener(this);


        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        binding.rvRaiseComplaint.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvRaiseComplaint.setAdapter(new RaiseComplaintAdapter(activity, list));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.mcvAdd) {
            Utils.I(activity, RaiseComplaintFormActivity.class, null);
        }
    }
}