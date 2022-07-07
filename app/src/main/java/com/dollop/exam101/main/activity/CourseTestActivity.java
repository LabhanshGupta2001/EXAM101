package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityCourseTestBinding;
import com.dollop.exam101.main.adapter.CourseTestQuestionAdapter;

import java.util.ArrayList;

public class CourseTestActivity extends AppCompatActivity implements View.OnClickListener {

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
        binding.ivBack.setOnClickListener(this);
        binding.ivAbout.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        getQuestin();    
        
    }
    void getQuestin()
    {
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("1) What is Software ");
        arrayList.add("2) what is Android");
        arrayList.add("3) what is XML");
        arrayList.add("4) what is Pointer");
        arrayList.add("5) what is Intent And How To Use it what is Intent And How To Use it ");
        binding.rvQuestion.setAdapter(new CourseTestQuestionAdapter(activity,arrayList));
        binding.rvQuestion.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onClick(View view) {
        if (view==binding.ivBack)
        {
            finish();
        }
        else if(view == binding.ivAbout)
        {
            Toast.makeText(activity, "About This App", Toast.LENGTH_SHORT).show();
        }else if (view == binding.btnSubmit)
        {
            Utils.I(activity, TestResultActivity.class,null);
        }
    }


}