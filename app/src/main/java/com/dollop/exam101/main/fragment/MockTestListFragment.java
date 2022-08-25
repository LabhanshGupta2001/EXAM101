package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentCartBinding;
import com.dollop.exam101.databinding.FragmentMockTestBinding;
import com.dollop.exam101.databinding.FragmentMockTestListBinding;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.activity.ProfileActivity;
import com.dollop.exam101.main.adapter.MockTestListAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.StudentMockTest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestListFragment extends Fragment implements View.OnClickListener {
    FragmentMockTestListBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();
    ApiService apiService;
    List<StudentMockTest> studentMockTestList =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMockTestListBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();
        return binding.getRoot();
    }

    private void init() {
        apiService = RetrofitClient.getClient();
        getMockTestList();
        binding.ivBack.setOnClickListener(this);
    }
    void setData() {
        binding.rvMockTestList.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvMockTestList.setAdapter(new MockTestListAdapter(activity, studentMockTestList));
    }

    private void getMockTestList(){
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getStudentMockTestListApi(Utils.GetSession().token).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        studentMockTestList.clear();
                        if(response.body().studentMockTestsList==null)
                        {
                            binding.dataNoFoundId.llParent.setVisibility(View.GONE);
                            binding.rvMockTestList.setVisibility(View.VISIBLE);
                            studentMockTestList=response.body().studentMockTestsList;
                            setData();
                        }else {
                                binding.dataNoFoundId.llParent.setVisibility(View.VISIBLE);
                                binding.rvMockTestList.setVisibility(View.GONE);
                        }

                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.alert(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.UnAuthorizationToken(activity);
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
    public void onClick(View view) {
        if (view == binding.ivBack) {
            ((DashboardScreenActivity) activity).navController.popBackStack();
        }
    }
}