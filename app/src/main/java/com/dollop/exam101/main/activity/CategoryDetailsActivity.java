package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.main.fragment.adapter.CategoryDetailAdapter;
import com.dollop.exam101.main.fragment.adapter.CategoryDetailSecondaryAdapter;

import java.util.ArrayList;

public class CategoryDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=CategoryDetailsActivity.this;
    ActivityCategoryDetailsBinding binding;
    ArrayList<String>list=new ArrayList<>();
    ArrayList<String>arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.ivBack.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvPackages.setAdapter(new CategoryDetailAdapter(activity,list));
        binding.rvPackages.setHasFixedSize(true);
        binding.rvPackages.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));


        arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");

        binding.rvPackagesSecondary.setAdapter(new CategoryDetailSecondaryAdapter(activity,arrayList));
        binding.rvPackagesSecondary.setHasFixedSize(true);
        binding.rvPackagesSecondary.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onClick(View view) {

        if(view==binding.ivBack){
            finish();
        }
    }
}