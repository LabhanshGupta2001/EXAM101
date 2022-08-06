package com.dollop.exam101.main.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import com.dollop.exam101.main.model.ModuleModel;
import com.dollop.exam101.main.model.SubjectModel;

import java.util.ArrayList;
import java.util.List;


public class CourseMaterialFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    FragmentCourseMaterialBinding binding;
    //ArrayList<ExamModel> examModelArrayList;
    List<SubjectModel> subjectModelArrayList ;
    ApiService apiService;
    List<ModuleModel> stringArrayList = new ArrayList<>();

    public CourseMaterialFragment(List<SubjectModel> ArrayList) {
        this.subjectModelArrayList = ArrayList;
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
       /* subjectModelArrayList.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");*/
        // subjectModelArrayList.clear();
       /* for (int i = 0; i < examModelArrayList.size(); i++) {
            subjectModelArrayList.addAll(examModelArrayList.get(i));
            stringArrayList.addAll(subjectModelArrayList.get(i).modules);
        }*/
        //subjectModelArrayList.addAll(subjectModelArrayList);
        Utils.E("subjectModelArrayList222::" + subjectModelArrayList);
        binding.rvFirst.setNestedScrollingEnabled(false);
        binding.rvFirst.setHasFixedSize(true);
        binding.rvFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFirst.setAdapter(new PakageDetailPrimaryAdapter(getContext(), subjectModelArrayList));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Dialog_Alert);
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