package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityMyWishlistBinding;
import com.dollop.exam101.main.adapter.MockTestQuestionAdapter;
import com.dollop.exam101.main.adapter.MyWishListAdapter;

import java.util.ArrayList;

public class MyWishlistActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = MyWishlistActivity.this;
    ActivityMyWishlistBinding binding;
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }
    private void init(){
        getWishlist();
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack){
            onBackPressed();
        }
    }
    void getWishlist()
    {
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        MyWishListAdapter myWishListAdapter = new MyWishListAdapter(activity,list);
        binding.rvWishList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvWishList.setAdapter(myWishListAdapter);
    }
}