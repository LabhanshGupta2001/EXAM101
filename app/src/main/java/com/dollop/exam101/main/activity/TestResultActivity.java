package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityTestResultBinding;
import com.dollop.exam101.main.adapter.CategoryDetailAdapter;
import com.dollop.exam101.main.adapter.TestResultAdapter;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = TestResultActivity.this;
    ActivityTestResultBinding binding;
    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvListOfQuestion.setAdapter(new TestResultAdapter(activity,list));
        binding.rvListOfQuestion.setHasFixedSize(true);
        binding.rvListOfQuestion.setLayoutManager(new LinearLayoutManager(activity));

    }
    private void init(){
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == binding.ivBack){
            onBackPressed();
        }
    }
}