package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.OnItemClicked;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentLanguageBinding;
import com.dollop.exam101.main.adapter.LanguageAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.LanguageModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LanguageFragment extends Fragment implements View.OnClickListener{
    FragmentLanguageBinding binding;
    ApiService apiService;
    Activity activity;
    private  String From;
    ArrayList<LanguageModel> LanguageList = new ArrayList<>();
   public LanguageAdapter languageAdapter;
    OnItemClicked onItemClicked;
    public LanguageFragment languageFragment;

    public LanguageFragment(String Key, OnItemClicked onItemClicked) {
        From = Key;
        this.onItemClicked= onItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLanguageBinding.inflate(inflater,container,false);
        activity = requireActivity();
        apiService = RetrofitClient.getClient();
        init();
        return   binding.getRoot();
    }

    private void init() {

        setLanguage();

    }

    private void setLanguage() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.getLanguage(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        LanguageList.clear();
                        LanguageList.addAll(response.body().languageModels);
                        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        languageAdapter=new LanguageAdapter(LanguageList, getContext(),From);
                        binding.rvLanguage.setAdapter(languageAdapter);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(getContext(), message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(getContext(), message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}