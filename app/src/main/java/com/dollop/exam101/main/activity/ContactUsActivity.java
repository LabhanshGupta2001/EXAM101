package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityContactUsBinding;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity=ContactUsActivity.this;
    ActivityContactUsBinding binding;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){

        apiService = RetrofitClient.getClient();

        binding.llSave.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.etMessage.setOnClickListener(this);
    }

    private void ContactUs(){
        HashMap<String, String> hm = new HashMap<>();
        apiService.ContactUs(hm).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(Call<AllResponseModel> call, Response<AllResponseModel> response) {

            }

            @Override
            public void onFailure(Call<AllResponseModel> call, Throwable t) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view==binding.llSave){
           finish();

        } else if(view==binding.ivBack){
            finish();
        } else if(view==binding.etMessage){
        }
    }
}