package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dollop.exam101.R;
import com.dollop.exam101.fragment.HomeFragment;

public class DashboardScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, new HomeFragment(), "SOMETAG").
                commit();
    }
}