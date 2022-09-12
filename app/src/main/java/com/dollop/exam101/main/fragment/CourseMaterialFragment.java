package com.dollop.exam101.main.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.FragmentCourseMaterialBinding;
import com.dollop.exam101.main.adapter.PakageDetailPrimaryAdapter;
import com.dollop.exam101.main.model.SubjectModel;

import java.util.ArrayList;
import java.util.List;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    //ArrayList<ExamModel> examModelArrayList;
    public List<SubjectModel> subjectModelArrayList = new ArrayList<>();
    Activity activity;
    FragmentCourseMaterialBinding binding;
    ApiService apiService;
    PakageDetailPrimaryAdapter pakageDetailPrimaryAdapter;
    private int PageHeight = 0;

    public CourseMaterialFragment() {
        //    this.subjectModelArrayList = ArrayList;
        Utils.E("subjectModelArrayList::" + subjectModelArrayList);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseMaterialBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {
        apiService = RetrofitClient.getClient();
        Utils.E("subjectModelArrayList222::" + subjectModelArrayList);
        binding.rvFirst.setNestedScrollingEnabled(false);
        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        pakageDetailPrimaryAdapter = new PakageDetailPrimaryAdapter(getContext(), subjectModelArrayList);
        binding.rvFirst.setAdapter(pakageDetailPrimaryAdapter);
    }

    public void UpdateData() {
        if (pakageDetailPrimaryAdapter!=null) {
            pakageDetailPrimaryAdapter.notifyDataSetChanged();
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    if (PageHeight == 0)
                        PageHeight = binding.rvFirst.getHeight();
                    binding.rvFirst.setMinimumHeight(PageHeight);
                    Utils.E("CountDownTimer:rvFirst:" + PageHeight);

                }
            }.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PageHeight = 0;
        binding.rvFirst.setMinimumHeight(PageHeight);
        Utils.E("PageHeight :" + this + " " + PageHeight);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity);
        AlertdialogBinding alertDialogBinding = AlertdialogBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        alertDialogBinding.tvPermittManually.setText(R.string.retry);
        alertDialogBinding.tvDesc.setText(R.string.please_check_your_connection);
        alertDialogBinding.tvPermittManually.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}