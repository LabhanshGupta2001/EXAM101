package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.PdfVideoTable;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemDownloadedPdfBinding;
import com.dollop.exam101.main.activity.PdfViewActivity;

import java.util.ArrayList;

public class DownloadedPdfAdapter extends RecyclerView.Adapter<DownloadedPdfAdapter.MyHolder> {
    Context context;
    ArrayList<PdfVideoTable> list;

    public DownloadedPdfAdapter(Context context, ArrayList<PdfVideoTable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DownloadedPdfAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDownloadedPdfBinding binding = ItemDownloadedPdfBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedPdfAdapter.MyHolder holder, @SuppressLint("RecycleView") int position) {
        PdfVideoTable pdfVideoTable = list.get(position);
        holder.binding.tvTopicName.setText(pdfVideoTable.topicName);
        holder.binding.tvTopicDescriptioin.setText(HtmlCompat.fromHtml(pdfVideoTable.topicDescription, 0));
        holder.binding.tvDate.setText(pdfVideoTable.date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.pdf, pdfVideoTable.pdfPath);
                bundle.putString(Constants.Key.From, Constants.Key.Document);
                Utils.I(context, PdfViewActivity.class, bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void OpenPDFFile() {

        Intent objIntent = new Intent(Intent.ACTION_VIEW);
        //  objIntent.setDataAndType(Uri.parse(fileDownLoad.toString()), "application/pdf");
        objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(objIntent);//Staring the pdf viewer
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemDownloadedPdfBinding binding;

        public MyHolder(@NonNull ItemDownloadedPdfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
