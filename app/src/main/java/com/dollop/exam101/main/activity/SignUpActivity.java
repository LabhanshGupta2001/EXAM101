package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dollop.exam101.Basics.UtilityTools.Utils;

import com.dollop.exam101.databinding.ActivitySignUpBinding;
import com.dollop.exam101.databinding.BottomSheetQuitExamBinding;
import com.dollop.exam101.databinding.BottomSheetStartTestBinding;
import com.dollop.exam101.main.model.ContryItemModel;
import com.dollop.exam101.R;
import com.dollop.exam101.main.adapter.ContryAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivitySignUpBinding binding;
    private ContryAdapter contryAdapter;
    private ArrayList<ContryItemModel> contryItemArrayList;

    BottomSheetDialog bottomSheetDialog,quitTestDialog;
    BottomSheetStartTestBinding bottomSheetStartTestBinding;
    BottomSheetQuitExamBinding bottomSheetQuitExamBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_signup);*/
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = SignUpActivity.this;
        sppiner();
    }

    private void sppiner() {
            init();
            Spinner spinner = findViewById(R.id.Spinner_Id);

            contryAdapter = new ContryAdapter(this, contryItemArrayList);
            spinner.setAdapter(contryAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ContryItemModel contryItem = (ContryItemModel) adapterView.getItemAtPosition(i);
                    String ClickContryCode = contryItem.getCode();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        private void init() {
            contryItemArrayList = new ArrayList<>();
            contryItemArrayList.add(new ContryItemModel("+91", R.drawable.ic_india));
            contryItemArrayList.add(new ContryItemModel("+1", R.drawable.ic_china));
            contryItemArrayList.add(new ContryItemModel("+44", R.drawable.ic_portugal));
            contryItemArrayList.add(new ContryItemModel("+49", R.drawable.ic_new_zealand));
            
            binding.SignUPId.setOnClickListener(this);
            binding.tvRegisterId.setOnClickListener(this);
            binding.llLoginWithGoogle.setOnClickListener(this);
            binding.llSignUp.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        if (view == binding.SignUPId) {
            Utils.I(SignUpActivity.this, LoginActivity.class,null);
        }else if(view==binding.tvRegisterId){
            Utils.I(SignUpActivity.this,DashboardScreenActivity.class,null);
            finish();
        } else if (view == binding.llLoginWithGoogle){
            bottomSheetDialog = new BottomSheetDialog(activity);
            bottomSheetStartTestBinding = BottomSheetStartTestBinding.inflate(getLayoutInflater());
            bottomSheetDialog.setContentView(bottomSheetStartTestBinding.getRoot());
            bottomSheetDialog.show();
        }else if (view == binding.llSignUp){
            quitTestDialog = new BottomSheetDialog(activity);
            bottomSheetQuitExamBinding = BottomSheetQuitExamBinding.inflate(getLayoutInflater());
            quitTestDialog.setContentView(bottomSheetQuitExamBinding.getRoot());
            quitTestDialog.show();
        }
    }
}