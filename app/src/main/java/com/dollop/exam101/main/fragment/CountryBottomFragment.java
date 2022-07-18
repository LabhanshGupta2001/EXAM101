package com.dollop.exam101.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.UtilityTools.OnRecycleViewItemCountryClick;
import com.dollop.exam101.databinding.FragmentBottomCountryBinding;
import com.dollop.exam101.main.activity.OnCountrySelectListner;
import com.dollop.exam101.main.adapter.CountryAdapter;
import com.dollop.exam101.main.model.CountryModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CountryBottomFragment extends BottomSheetDialogFragment implements OnRecycleViewItemCountryClick {
    FragmentBottomCountryBinding binding;
    ArrayList<CountryModel> countryModels = new ArrayList<>();
    Activity activity;

    OnCountrySelectListner onCountrySelectListner;
    OnRecycleViewItemCountryClick onRecycleViewItemClick;

    public CountryBottomFragment(ArrayList<CountryModel> countryModels, OnCountrySelectListner onCountrySelectListner) {
        this.onCountrySelectListner = onCountrySelectListner;
        this.countryModels = countryModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomCountryBinding.inflate(inflater, container, false);
        activity = requireActivity();
        onRecycleViewItemClick = this;
       // initViews();
        return binding.getRoot();

    }

   /* private void initViews() {
        binding.rvCountryListId.setLayoutManager(new LinearLayoutManager(requireActivity()));
        CountryAdapter countryAdapter = new CountryAdapter(countryModels, activity, onRecycleViewItemClick);
        binding.rvCountryListId.setAdapter(countryAdapter);
        binding.etNameId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                ArrayList<CountryModel> countryModelsFilter = new ArrayList<>();
                for (int i = 0; i < countryModels.size(); i++) {

                    if (countryModels.get(i).toString().toLowerCase().contains(binding.etNameId.getText().toString().toLowerCase())) {

                        countryModelsFilter.add(countryModels.get(i));
                    }
                }
                CountryAdapter countryAdapter = new CountryAdapter(countryModelsFilter, activity, onRecycleViewItemClick);
                binding.rvCountryListId.setAdapter(countryAdapter);

            }
        });
    }*/

    @Override
    public void onClickItem(CountryModel countryModel) {
        dismiss();
        onCountrySelectListner.onCountrySelected(countryModel);

    }
}
