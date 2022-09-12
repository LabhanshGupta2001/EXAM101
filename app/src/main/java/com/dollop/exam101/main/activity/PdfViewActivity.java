package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivityPdfViewBinding;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PdfViewActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityPdfViewBinding binding;
    Bundle bundle;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        binding.ivBack.setOnClickListener(this);
        binding.ivBack2.setOnClickListener(this);
        setContentView(binding.getRoot());
        activity = PdfViewActivity.this;
        bundle = getIntent().getExtras();

      //  displayFromAsset(bundle.getString(Constants.Key.pdf));
        if(bundle.getString(Constants.Key.From).equals(Constants.Key.urlType)){

            showPDF(bundle.getString(Constants.Key.pdf));
        }else {
            showDownloadPDF(bundle.getString(Constants.Key.pdf));
        }

    }

    private void showDownloadPDF(String path) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        binding.pdfView.fromFile(new File(path))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        progressDialog.dismiss();
                    }
                }).scrollHandle(new DefaultScrollHandle(this))
                .load();
    }



    @Override
    public void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.llToolbar.setVisibility(View.GONE);
            binding.pdfView.zoomTo(30);
        } else {
            binding.llToolbar.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.llToolbar.setVisibility(View.GONE);
            binding.pdfView.zoomTo(30);
        } else {
            binding.llToolbar.setVisibility(View.VISIBLE);
        }

    }

    public void showPDF(String pdf) {
        Dialog progressDialog = Utils.initProgressDialog(activity);

        AsyncTask.execute(() -> {
            try {
             //   binding.pdfView.fromAsset("agl.pdf").load();

                final InputStream input = new URL( pdf).openStream();
                activity.runOnUiThread(() -> binding.pdfView.fromStream(input)
                        .onLoad(new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {
                                progressDialog.dismiss();
                            }
                        }).disableLongpress()
                        .spacing(10)
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Utils.T(activity, "PDF Does't Exist");
                                progressDialog.dismiss();

                            }
                        }).onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Utils.T(activity, "PDF Does't Exist");
                                progressDialog.dismiss();
                            }
                        })
                        .fitEachPage(true)
                        .pageFitPolicy(FitPolicy.BOTH)
                        .enableSwipe(true)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(activity))
                        .load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivBack) {
            onBackPressed();

        }
        if (v == binding.ivBack2) {
            onBackPressed();

        }
    }
}