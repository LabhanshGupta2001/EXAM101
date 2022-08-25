package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityMockTestQuestionsBinding;
import com.dollop.exam101.databinding.BottomSheetQuitExamBinding;
import com.dollop.exam101.main.adapter.MockTestQuestionAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MockTestQuestionsActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = MockTestQuestionsActivity.this;
    ActivityMockTestQuestionsBinding binding;
    BottomSheetDialog quitTestDialog;
    BottomSheetQuitExamBinding bottomSheetQuitExamBinding;
    ArrayList<String> list = new ArrayList<>();
    int layoutWidth = 1;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMockTestQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        MockTestQuestionAdapter mockTestQuestionAdapter = new MockTestQuestionAdapter(list, binding.vpQuestion, getApplicationContext());
        binding.vpQuestion.setAdapter(mockTestQuestionAdapter);

        layoutWidth = binding.vpQuestion.getWidth();
        binding.tvAllPages.setText(String.valueOf(layoutWidth));

    }

    private void init() {
        setBottomSheet();
        apiService = RetrofitClient.getClient();
        binding.tvQuitExam.setOnClickListener(this);
        binding.tvButtonSubmit.setOnClickListener(this);
        binding.mcvMoveBack.setOnClickListener(this);
        binding.mcvMoveNext.setOnClickListener(this);
        bottomSheetQuitExamBinding.mcvBtnQuitTest.setOnClickListener(this);
        bottomSheetQuitExamBinding.mcvBtnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvQuitExam) {
            quitTestDialog.show();

        } else if (view == binding.tvButtonSubmit) {
            Utils.I(activity, TestResultActivity.class, null);
            finish();
        } else if (view == binding.mcvMoveNext) {
            binding.vpQuestion.setCurrentItem((Integer) getItem(+1), true);
        } else if (view == binding.mcvMoveBack) {
            binding.vpQuestion.setCurrentItem((Integer) getItem(-1), true);
        } else if (view == bottomSheetQuitExamBinding.mcvBtnCancel) {
            quitTestDialog.cancel();
        } else if (view == bottomSheetQuitExamBinding.mcvBtnQuitTest) {
            Utils.I(activity, TestResultActivity.class, null);
            finish();
        }
    }

    private Object getItem(int i) {
        return binding.vpQuestion.getCurrentItem() + i;
    }

    void setBottomSheet() {
        quitTestDialog = new BottomSheetDialog(activity);
        bottomSheetQuitExamBinding = BottomSheetQuitExamBinding.inflate(getLayoutInflater());
        quitTestDialog.setContentView(bottomSheetQuitExamBinding.getRoot());
    }

    void submit() {

    }
}