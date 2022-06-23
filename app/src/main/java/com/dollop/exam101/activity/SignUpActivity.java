package com.dollop.exam101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dollop.exam101.model.ContryItemModel;
import com.dollop.exam101.R;
import com.dollop.exam101.adapter.ContryAdapter;
import com.dollop.exam101.databinding.ActivitySignupBinding;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = SignUpActivity.this;
    ActivitySignupBinding binding;
    private ContryAdapter contryAdapter;
    private ArrayList<ContryItemModel> contryItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_signup);*/
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
            
            
            
        }

    @Override
    public void onClick(View view) {
        if (view == binding.SignUPId) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}