package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.main.model.CountryItem;
import com.dollop.exam101.R;
import com.dollop.exam101.main.adapter.EditProfileCountryAdapter;
import com.dollop.exam101.main.adapter.EditProfileAdapter;
import com.dollop.exam101.databinding.ActivityEditProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Activity activity = EditProfileActivity.this;
    ActivityEditProfileBinding binding;
    ImageView imageView;
    EditProfileCountryAdapter contryAdapter;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private ArrayList<CountryItem> contryItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        binding.mcvProfileSelector.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llspinner.setOnClickListener(this);

        initList();
        spinner();
        imageView = binding.ivProfile;
        String[] countryNames = {"India", "China", "Australia", "Portugle", "America", "New Zealand"};
        int flags[] = {R.drawable.ic_india, R.drawable.ic_china, R.drawable.ic_flag_of_australia, R.drawable.ic_portugal, R.drawable.ic_portugal, R.drawable.ic_new_zealand};
        binding.Spinner.setOnItemSelectedListener(this);
        EditProfileAdapter editProfileAdapter = new EditProfileAdapter(getApplicationContext(), flags, countryNames);
        binding.Spinner.setAdapter(editProfileAdapter);
        contryAdapter = new EditProfileCountryAdapter(activity, contryItemArrayList);
        binding.SpinnerCountry.setAdapter(contryAdapter);
        binding.SpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CountryItem contryItem = (CountryItem) adapterView.getItemAtPosition(i);
                String ClickContryCode = contryItem.getCode();
                Utils.E(ClickContryCode);
                Log.e("ClickContryCode",ClickContryCode);
                binding.tvContryCodeId.setText(ClickContryCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvProfileSelector) {
            if (checkAndRequestPermissions(activity)) {
                chooseImage(activity);
            }
        } else if (view == binding.llSave) {
            Utils.I(activity, MockTestListActivity.class, null);
        }else if(view==binding.llspinner){
            binding.SpinnerCountry.performClick();

        }

    }

    private void chooseImage(Activity activity) {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    showSettingsDialog();
                    Toast.makeText(getApplicationContext(), "FlagUp Requires Access to Your Storage.", Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(activity);
                }
                break;
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        imageView.setImageURI(selectedImage);
                    }
                    break;
            }
        }
    }


    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void spinner() {

    }
    private void initList() {
        contryItemArrayList = new ArrayList<>();
        contryItemArrayList.add(new CountryItem("+91", R.drawable.ic_india));
        contryItemArrayList.add(new CountryItem("+1", R.drawable.ic_china));
        contryItemArrayList.add(new CountryItem("+44", R.drawable.ic_new_zealand));
        contryItemArrayList.add(new CountryItem("+49", R.drawable.ic_portugal));
    }
}

