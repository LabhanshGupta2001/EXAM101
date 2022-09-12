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
import com.dollop.exam101.databinding.FragmentDonloadPdfBinding;
import com.dollop.exam101.main.adapter.DownloadedPdfAdapter;

import java.util.ArrayList;


public class DonloadPdfFragment extends Fragment {
    FragmentDonloadPdfBinding binding;
    ArrayList<String> list = new ArrayList<>();
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDonloadPdfBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        activity = requireActivity();
        setPdf();
    }

    private void    setPdf() {
        ArrayList<PdfVideoTable> pdfList = UserDataHelper.getInstance().getPdfList();
        ArrayList<PdfVideoTable> pdfList1 = new ArrayList<>();

        if (!pdfList.isEmpty()) {
            for (int i = 0; i < pdfList.size(); i++) {
                if (pdfList.get(i).pdfPath!=null) {
                    pdfList1.add(pdfList.get(i));
                }
            }
        }
        if (pdfList1.isEmpty()) {
            binding.rvPdf.setVisibility(View.GONE);
            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
        } else {
            binding.rvPdf.setVisibility(View.VISIBLE);
            binding.noResultFoundId.llParent.setVisibility(View.GONE);
        }
        binding.rvPdf.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvPdf.setAdapter(new DownloadedPdfAdapter(activity, pdfList1));
    }
}