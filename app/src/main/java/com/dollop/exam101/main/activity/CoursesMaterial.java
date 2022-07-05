package com.dollop.exam101.main.activity;

import static com.dollop.exam101.Basics.UtilityTools.AppController.getContext;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.databinding.ActivityCoursesMaterialBinding;
import com.dollop.exam101.main.adapter.CourseMaterialSubjectAdapter;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;

import java.util.ArrayList;

public class CoursesMaterial extends AppCompatActivity {
    Activity activity=CoursesMaterial.this;
    ActivityCoursesMaterialBinding  binding;
    ArrayList<String> list = new ArrayList<>();
    private Boolean dropdown = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCoursesMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setProgress(50);
        binding.progressBar.setMax(100);

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

}