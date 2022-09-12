package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Database.PdfVideoTable;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.databinding.FragmentDownloadVideoBinding;
import com.dollop.exam101.main.adapter.DownloadedVideoAdapter;

import java.util.ArrayList;

public class DownloadVideoFragment extends Fragment {
    FragmentDownloadVideoBinding binding;
    ArrayList<String> list = new ArrayList<>();
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDownloadVideoBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        activity = requireActivity();
        setVideo();
    }

    private void setVideo() {
        ArrayList<PdfVideoTable> pdfVideoList = UserDataHelper.getInstance().getPdfList();
        ArrayList<PdfVideoTable> videoList = new ArrayList<>();

        if (!pdfVideoList.isEmpty()) {
            for (int i = 0; i < pdfVideoList.size(); i++) {
                if (pdfVideoList.get(i).videoPath != null) {
                    videoList.add(pdfVideoList.get(i));
                }
            }
        }
        if (videoList.isEmpty()) {
            binding.rvVideo.setVisibility(View.GONE);
            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
        } else {
            binding.rvVideo.setVisibility(View.VISIBLE);
            binding.noResultFoundId.llParent.setVisibility(View.GONE);
        }
        binding.rvVideo.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvVideo.setAdapter(new DownloadedVideoAdapter(activity, videoList));
    }

}