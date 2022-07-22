package com.dollop.exam101.main.fragment;

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
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.FragmentExamFilterBinding;
import com.dollop.exam101.main.adapter.CategoriesFragmentAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.CourseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamFilterFragment extends Fragment implements View.OnClickListener {
    FragmentExamFilterBinding binding;
    ArrayList<CourseModel> examArrayList = new ArrayList<>();


    ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamFilterBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();

    }

    private void init() {
        apiService = RetrofitClient.getClient();
        getFilter();
       /* list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        binding.rvExam.setHasFixedSize(true);
        binding.rvExam.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExam.setAdapter(new ExamFragmentAdapter(getContext(), list));
*/
    }

    @Override
    public void onClick(View v) {

    }

    void getFilter() {
        Dialog progressDialog = Utils.initProgressDialog(getContext());
        apiService.Examlist(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    examArrayList.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        examArrayList.addAll(response.body().examListModels);
                        binding.rvExam.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        binding.rvExam.setAdapter(new CategoriesFragmentAdapter(examArrayList, getContext()));
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

}