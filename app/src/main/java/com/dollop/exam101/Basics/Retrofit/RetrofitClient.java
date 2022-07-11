package com.dollop.exam101.Basics.Retrofit;


import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anil on 9/4/2021.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static ApiService getClient() {

        /*if(!AppController.getInstance().isOnline()){
            Utils.T_Long(AppController.getContext(),"Please check your Internet Connection");
        }*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);

        if (Const.Development.equals(Constants.Debug)) {
            client.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        return retrofit.create(ApiService.class);
    }



}
