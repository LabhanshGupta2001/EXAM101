package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.main.fragment.adapter.LoginHistoryAdapter;

import java.util.ArrayList;

public class LoginHistoryActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=LoginHistoryActivity.this;
    ActivityLoginHistoryBinding binding;
    ArrayList<String>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        binding.ivBack.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvLoginHistory.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvLoginHistory.setAdapter(new LoginHistoryAdapter(activity,list));
    }

    @Override
    public void onClick(View view) {
        if(view==binding.ivBack){
            Utils.I(activity,MockTestHistoryActivity.class,null);
        }
    }
}