package com.dollop.exam101.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.FragmentPackageListBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ViewPagerFragmentAdapter;
import com.dollop.exam101.main.model.PackageModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class PackageListFragment extends Fragment implements View.OnClickListener {
    FragmentPackageListBinding binding;
    Fragment fragment=PackageListFragment.this;

    ViewPagerFragmentAdapter adapter;
    private String[] labels = new String[]{"Categories","Price","Language"};


    BottomSheetDialog bottomSheetDialog;
    ArrayList<PackageModel> packageList = new ArrayList<>();
    PackageAdapter packageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackageListBinding.inflate(inflater, container, false);
        init();

       return binding.getRoot();
    }

    private void bottomsheetTask() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_filter);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
        ArrayList<Fragment>Fragment=new ArrayList<>();
        Fragment.add(new CategoriesFragment());
        Fragment.add(new PriceFragment());
        Fragment.add(new LanguageFragment());

        TabLayout tabLayout=bottomSheetDialog.findViewById(R.id.tlTabLayoutId);
        ViewPager2 viewPager2=bottomSheetDialog.findViewById(R.id.ViewPagerId);

        viewPager2.setAdapter(new ViewPagerFragmentAdapter(getParentFragmentManager(),getLifecycle(),Fragment));

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();






    }

    private void init() {
        packageList.add(new PackageModel("MS Power Point", "MS Office, Advance Power point, Animated Slides"));
        packageList.add(new PackageModel("Digital Design Thinking", "Graphic Design, Adobe software, indesgin, figma, in... Slides"));
        packageList.add(new PackageModel("Creative Express", "Adobe XD, Creative Suit, Adobe Premier, Phtoshop C...Power point, Animated Slides"));
        packageList.add(new PackageModel("Creative Art Design", "Banner Design, Logo design,Posters"));


        packageList.add(new PackageModel("MS Power Point", "MS Office, Advance Power point, Animated Slides"));
        packageList.add(new PackageModel("Digital Design Thinking", "Graphic Design, Adobe software, indesgin, figma, in... Slides"));
        packageList.add(new PackageModel("Creative Express", "Adobe XD, Creative Suit, Adobe Premier, Phtoshop C...Power point, Animated Slides"));
        packageList.add(new PackageModel("Creative Art Design", "Banner Design, Logo design,Posters"));


        packageAdapter = new PackageAdapter(getActivity(), packageList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvPackagesone.setLayoutManager(linearLayoutManager2);
        binding.rvPackagesone.setAdapter(packageAdapter);
        binding.mcvFilterId.setOnClickListener(this);
        //binding.tvAllPackage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvFilterId) {
            bottomsheetTask();
        }
    }
}