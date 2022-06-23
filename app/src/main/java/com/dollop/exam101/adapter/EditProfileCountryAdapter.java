package com.dollop.exam101.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dollop.exam101.model.CountryItem;
import com.dollop.exam101.R;

import java.util.ArrayList;

public class EditProfileCountryAdapter extends ArrayAdapter {

    public EditProfileCountryAdapter(Context context , ArrayList<CountryItem>contrylist)
    {
        super(context,0,contrylist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private  View initView(int posision,View convertView ,ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_spinner,parent,false);

        }
        ImageView imageViewFlag = convertView.findViewById(R.id.iv_flag_india_id);
        TextView id =convertView.findViewById(R.id.tv_contry_code_id);
        CountryItem contryItem = (CountryItem) getItem(posision);
        if(contryItem != null) {
            imageViewFlag.setImageResource(contryItem.getImage());
            id.setText(contryItem.getCode());
        }
        return convertView;
    }
}
