package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityRaiseComplaintFormBinding;

public class RaiseComplaintFormActivity extends AppCompatActivity implements View.OnClickListener {

    public String fileName;
    Activity activity = RaiseComplaintFormActivity.this;
    ActivityRaiseComplaintFormBinding binding;
    Intent intent = new Intent();

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseComplaintFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llSave.setOnClickListener(this);
        binding.llHigh.setOnClickListener(this);
        binding.llNormal.setOnClickListener(this);
        binding.llCritical.setOnClickListener(this);
        binding.mcvUpload.setOnClickListener(this);
        binding.llUploadPdfDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.llSave) {
            finish();
        } else if (view == binding.llNormal) {
            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image.setVisibility(View.VISIBLE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        } else if (view == binding.llHigh) {
            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image2.setVisibility(View.VISIBLE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image3.setVisibility(View.GONE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));


        } else if (view == binding.llCritical) {
            binding.tvCritical.setTextColor(ContextCompat.getColor(activity, R.color.full_black));
            binding.Image3.setVisibility(View.VISIBLE);
            binding.llCritical.setBackground(ContextCompat.getDrawable(activity, R.drawable.theme_strock_bg));


            binding.tvNormal.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image.setVisibility(View.GONE);
            binding.llNormal.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

            binding.tvHigh.setTextColor(ContextCompat.getColor(activity, R.color.sub_text));
            binding.Image2.setVisibility(View.GONE);
            binding.llHigh.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_strock_bg));

        } else if (view == binding.mcvUpload) {
            intent = getFileChooserIntent();
            startActivityForResult(intent, 100);
        } else if (view == binding.llUploadPdfDone) {
            intent = getFileChooserIntent();
            startActivityForResult(intent, 100);
        }
    }

    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String Fpath = data.getDataString();
        // Utils.T(activity,Fpath);
        // Utils.E("path:: "+Fpath);
        fileName = getFileName(Fpath);
        Utils.E("file name:: " + fileName);
        Utils.T(activity, fileName);

        if (resultCode != RESULT_CANCELED) {
            binding.llUpload.setVisibility(View.GONE);
            binding.llUploadPdfDone.setVisibility(View.VISIBLE);
            binding.tvUploadFilename.setText(fileName);
        }

        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK && data != null) {
                    binding.llUpload.setVisibility(View.GONE);
                    binding.llUploadPdfDone.setVisibility(View.VISIBLE);
                    binding.tvUploadFilename.setText(fileName);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    binding.llUpload.setVisibility(View.VISIBLE);
                    binding.llUploadPdfDone.setVisibility(View.GONE);
                }
                break;
        }
    }
}