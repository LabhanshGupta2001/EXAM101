package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.R;

import java.util.ArrayList;

public class FlexBoxAdapter extends RecyclerView.Adapter<FlexBoxAdapter.Myholder> {
    Context context;
    ArrayList<String> arrayList;

    public FlexBoxAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flex_box, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {

        public Myholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
