package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.databinding.ActivityCourseListBinding;
import com.dollop.exam101.main.adapter.CourseListAdapter;
import com.dollop.exam101.main.model.CourseListModel;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity=CourseListActivity.this;
    ActivityCourseListBinding binding;
    ArrayList<CourseListModel> courseListModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


    }
   void init()
    {
        binding.ivBack.setOnClickListener(this);

        courseListModels.add(new CourseListModel("Adobe Software","Digital Design Thinking","04 Jul,2022","20","30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software","Digital Design Thinking","04 Jul,2022","20","30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software","Digital Design Thinking","04 Jul,2022","20","30 DAYS"));
        courseListModels.add(new CourseListModel("Adobe Software","Digital Design Thinking","04 Jul,2022","0","30 DAYS"));

        CourseListAdapter adapter=new CourseListAdapter(activity,courseListModels);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.rvCourseList.setLayoutManager(linearLayoutManager3);
        binding.rvCourseList.setAdapter(adapter);

    }

    @Override
    public void onClick(View view)
    {
        if (view == binding.ivBack)
        {
            onBackPressed();
        }
    }
}