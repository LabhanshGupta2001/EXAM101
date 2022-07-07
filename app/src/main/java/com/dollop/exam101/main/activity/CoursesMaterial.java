package com.dollop.exam101.main.activity;

import static com.dollop.exam101.Basics.UtilityTools.AppController.getContext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityCoursesMaterialBinding;
import com.dollop.exam101.main.adapter.CourseMaterialSubjectAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;

import java.util.ArrayList;

public class CoursesMaterial extends AppCompatActivity implements View.OnClickListener {
    Activity activity = CoursesMaterial.this;
    ActivityCoursesMaterialBinding binding;
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.ivBack.setOnClickListener(this);

    }

    void init() {
        setProgress();
        getCourseList();
    }

    void setProgress() {
        binding.progressBar.setProgress(50);
        binding.progressBar.setMax(100);
    }

    void getCourseList() {
        list.clear();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        binding.rvCourseList.setHasFixedSize(true);
        binding.rvCourseList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvCourseList.setAdapter(new CourseMaterialSubjectAdapter(activity, list));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack)
        {
            Utils.I(activity,ProfileActivity.class,null);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.I(activity,ProfileActivity.class,null);
        finish();
    }
}