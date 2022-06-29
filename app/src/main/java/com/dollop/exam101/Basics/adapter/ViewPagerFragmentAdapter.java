package com.dollop.exam101.Basics.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
  ArrayList<Fragment>fragment;

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment>fragments) {

        super(fragmentManager, lifecycle);
        this.fragment=fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
   return fragment.get(position);

    }

    @Override
    public int getItemCount() {
        return fragment.size();
    }
}
