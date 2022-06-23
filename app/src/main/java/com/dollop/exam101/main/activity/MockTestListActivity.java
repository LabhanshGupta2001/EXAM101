package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.main.adapter.MockTestListAdapter;
import com.dollop.exam101.databinding.ActivityMockTestListBinding;

import java.util.ArrayList;

public class MockTestListActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = MockTestListActivity.this;
    ActivityMockTestListBinding binding;
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMockTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialise();
    }

    private void initialise() {
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(new MockTestListAdapter(activity, list));
    }


    @Override
    public void onClick(View view) {

    }
}