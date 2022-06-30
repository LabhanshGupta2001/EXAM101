package com.dollop.exam101.main.fragment;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollop.exam101.R;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.databinding.BottomsheetFilterBinding;
import com.dollop.exam101.databinding.FragmentPackageListBinding;
import com.dollop.exam101.databinding.ItemBlogsHorizontalBinding;
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

    BottomsheetFilterBinding bottomsheetFilterBinding;
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

        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(inflater,container,false);
        init();
       return binding.getRoot();
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

    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvFilterId) {
            bottomsheetTask();
        }
    }

    private void bottomsheetTask() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomsheetFilterBinding = BottomsheetFilterBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomsheetFilterBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomsheetFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
        ArrayList<Fragment>Fragment=new ArrayList<>();
        Fragment.add(new CategoriesFragment());
        Fragment.add(new PriceFragment());
        Fragment.add(new LanguageFragment());
       // bottomsheetFilterBinding.ViewPagerId


      /*  TabLayout tabLayout=bottomSheetDialog.findViewById(R.id.tlTabLayoutId);
        ViewPager2 viewPager2=bottomSheetDialog.findViewById(R.id.ViewPagerId);
*/
        bottomsheetFilterBinding.ViewPagerId.setAdapter(new ViewPagerFragmentAdapter(getParentFragmentManager(),getLifecycle(),Fragment));

        new TabLayoutMediator(bottomsheetFilterBinding.tlTabLayoutId, bottomsheetFilterBinding.ViewPagerId, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();

        View tab1 = ((ViewGroup) bottomsheetFilterBinding.tlTabLayoutId.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();


    }

}