package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCoursesDetailBinding;

public class CoursesDetailActivity extends AppCompatActivity {

    Activity activity= CoursesDetailActivity.this;
    ActivityCoursesDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCoursesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}