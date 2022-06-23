package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dollop.exam101.R;

public class SplashActivity extends AppCompatActivity{
    Activity activity= SplashActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        };thread.start();
    }



}


 /* if (v == binding.cvViewCart) {
          Bundle bundle = new Bundle();
          bundle.putString(Constants.From, Constants.Restaurant);
          Utils.I(activity, DashboardActivity.class, bundle);
        }*/