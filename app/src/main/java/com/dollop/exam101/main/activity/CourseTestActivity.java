package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCourseTestBinding;
import com.dollop.exam101.main.adapter.CourseTestQuestionAdapter;

import java.util.ArrayList;

public class CourseTestActivity extends AppCompatActivity {

    Activity activity=CourseTestActivity.this;
    ActivityCourseTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCourseTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    void init()
    {
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("1) What is Software ");
        arrayList.add("2) what is Android");
        arrayList.add("3) what is XML");
        arrayList.add("4) what is Pointer");
        arrayList.add("5) what is Intent And How To Use it what is Intent And How To Use it ");
        binding.rvQuestion.setAdapter(new CourseTestQuestionAdapter(activity,arrayList));
        binding.rvQuestion.setLayoutManager(new LinearLayoutManager(activity));

        //binding.rvQuestion.setLayoutManager();
    }
}