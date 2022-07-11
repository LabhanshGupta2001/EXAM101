package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dollop.exam101.Basics.UtilityTools.Utils;

import com.dollop.exam101.databinding.ActivitySignUpBinding;
import com.dollop.exam101.databinding.BottomSheetQuitExamBinding;
import com.dollop.exam101.databinding.BottomSheetStartTestBinding;
import com.dollop.exam101.main.model.ContryItemModel;
import com.dollop.exam101.R;
import com.dollop.exam101.main.adapter.ContryAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivitySignUpBinding binding;
    private ContryAdapter contryAdapter;
    private ArrayList<ContryItemModel> contryItemArrayList;
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;

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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        binding.llLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // startActivity(new Intent(activity, DashboardScreenActivity.class));
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Log.e(String.valueOf(activity), "handleSignInResult: "+personName );
                Toast.makeText(this, "successfully Sing in", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(activity, DashboardScreenActivity.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("massage", e.toString());
        }
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