package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    Activity activity = SplashActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    if(Utils.IS_LOGIN()){
                        Utils.I_clear(activity,DashboardScreenActivity.class,null);
                    }else {
                        Utils.I_clear(activity,WelcomeActivity.class,null);

                    }



                }
            }

        };
        thread.start();
    }


}


 /* if (v == binding.cvViewCart) {
          Bundle bundle = new Bundle();
          bundle.putString(Constants.Key.From, Constants.Key.Restaurant);
          Utils.I(activity, DashboardActivity.class, bundle);
        }*/