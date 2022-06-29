package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.databinding.ActivityBlogsListBinding;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.databinding.BottomSheetBlogShortBinding;
import com.dollop.exam101.databinding.ItemAllBlogsBinding;
import com.dollop.exam101.databinding.ItemBlogsHorizontalBinding;
import com.dollop.exam101.main.adapter.AllBlogListAdapter;
import com.dollop.exam101.main.adapter.BlogsListAdapter;
import com.dollop.exam101.main.fragment.AuthorFragment;
import com.dollop.exam101.main.fragment.CategoryFragment;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.BlogListHeadingModel;
import com.dollop.exam101.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class BlogsListActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = BlogsListActivity.this;
    ActivityBlogsListBinding binding;
    BottomSheetDialog bottomSheetDialog, bottomSheetFilter;

    ItemBlogsHorizontalBinding itemBlogsHorizontalBinding;
    ArrayList<BlogListHeadingModel> blogListHeadingModelArrayList = new ArrayList<>();

    ItemAllBlogsBinding itemAllBlogsBinding;
    ArrayList<AllBlogListModel> allBlogListArrayList = new ArrayList<>();

    BottomSheetBlogShortBinding bottomSheetBlogShortBinding;
    BottomSheetBlogFilterBinding bottomSheetBlogFilterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogsListBinding.inflate(getLayoutInflater());

        itemAllBlogsBinding = ItemAllBlogsBinding.inflate(getLayoutInflater());
        setContentView(itemAllBlogsBinding.getRoot());

        itemBlogsHorizontalBinding = ItemBlogsHorizontalBinding.inflate(getLayoutInflater());
        setContentView(itemBlogsHorizontalBinding.getRoot());

        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.llBtnShort.setOnClickListener(this);
        binding.llBtnFilter.setOnClickListener(this);

        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetBlogShortBinding = BottomSheetBlogShortBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBlogShortBinding.getRoot());
        bottomSheetBlogShortBinding.mcvAtoZ.setOnClickListener(this);
        bottomSheetBlogShortBinding.mcvZtoA.setOnClickListener(this);


        BlogListHeadingModel blogListHeadingModel = new BlogListHeadingModel();
        BlogListHeadingModel blogListHeadingModel1 = new BlogListHeadingModel();
        BlogListHeadingModel blogListHeadingModel2 = new BlogListHeadingModel();
        BlogListHeadingModel blogListHeadingModel3 = new BlogListHeadingModel();

        blogListHeadingModel.Heading = "All Blogs";
        blogListHeadingModel1.Heading = "Composition";
        blogListHeadingModel2.Heading = "Exposure";
        blogListHeadingModel3.Heading = "Practical Photography";

        blogListHeadingModelArrayList.clear();
        blogListHeadingModelArrayList.add(blogListHeadingModel);
        blogListHeadingModelArrayList.add(blogListHeadingModel1);
        blogListHeadingModelArrayList.add(blogListHeadingModel2);
        blogListHeadingModelArrayList.add(blogListHeadingModel3);

        binding.rvHorizontalHeading.setHasFixedSize(true);
        binding.rvHorizontalHeading.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.rvHorizontalHeading.setAdapter(new BlogsListAdapter(activity, blogListHeadingModelArrayList));

        AllBlogListModel allBlogListModel = new AllBlogListModel();
        AllBlogListModel allBlogListModel1 = new AllBlogListModel();
        AllBlogListModel allBlogListModel2 = new AllBlogListModel();
        AllBlogListModel allBlogListModel3 = new AllBlogListModel();
        AllBlogListModel allBlogListModel4 = new AllBlogListModel();
        AllBlogListModel allBlogListModel5 = new AllBlogListModel();
        AllBlogListModel allBlogListModel6 = new AllBlogListModel();

        allBlogListModel.Date = "2 June, 2021";
        allBlogListModel.MainBlogHeading = "5 Ways Google Cloud...";
        allBlogListModel.NewsHeading = "Guide to Using the Right sit amet, elit. Velit" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel.AuthorName = "Author Name";
        allBlogListModel.BlogMainImg = R.drawable.user_profile;
        allBlogListModel.AuthorProfile = R.drawable.user_profile;

        allBlogListModel1.Date = "Today";
        allBlogListModel1.MainBlogHeading = "Using the Right DevOps Tools...";
        allBlogListModel1.NewsHeading = "Guide to Using the Right sit amet, elit. Velit" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel1.AuthorName = "Patel Saheb";
        allBlogListModel1.BlogMainImg = R.drawable.user_profile;
        allBlogListModel1.AuthorProfile = R.drawable.user_profile;

        allBlogListModel2.Date = "1 Jan,2021";
        allBlogListModel2.MainBlogHeading = "The Right DevOps Tools 2021...";
        allBlogListModel2.NewsHeading = "Guide to Using the Right sit amet, elit. Velit\n" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel2.AuthorName = "Aryan jayswal";
        allBlogListModel2.BlogMainImg = R.drawable.user_profile;
        allBlogListModel2.AuthorProfile = R.drawable.user_profile;

        allBlogListModel3.Date = "1 Jan,2021";
        allBlogListModel3.MainBlogHeading = "The Right DevOps Tools 2021...";
        allBlogListModel3.NewsHeading = "Guide to Using the Right sit amet, elit. Velit" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel3.AuthorName = "Guru Patidar";
        allBlogListModel3.BlogMainImg = R.drawable.user_profile;
        allBlogListModel3.AuthorProfile = R.drawable.user_profile;

        allBlogListModel4.Date = "Yestarday";
        allBlogListModel4.MainBlogHeading = "The Right DevOps Tools 2021...";
        allBlogListModel4.NewsHeading = "Guide to Using the Right sit amet, elit. Velit\n" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel4.AuthorName = "Labhansh gupta";
        allBlogListModel4.BlogMainImg = R.drawable.user_profile;
        allBlogListModel4.AuthorProfile = R.drawable.user_profile;

        allBlogListModel5.Date = "1 Jan,2021";
        allBlogListModel5.MainBlogHeading = "The Right DevOps Tools 2021...";
        allBlogListModel5.NewsHeading = "Guide to Using the Right sit amet, elit. Velit" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel5.AuthorName = "Nilu Patel";
        allBlogListModel5.BlogMainImg = R.drawable.user_profile;
        allBlogListModel5.AuthorProfile = R.drawable.user_profile;

        allBlogListModel6.Date = "1 Jan,2021";
        allBlogListModel6.MainBlogHeading = "The Right DevOps Tools 2021...";
        allBlogListModel6.NewsHeading = "Guide to Using the Right sit amet, elit. Velit\n" +
                "sit ornare tortor arcu, euismod...";
        allBlogListModel6.AuthorName = "Aman Rathor";
        allBlogListModel6.BlogMainImg = R.drawable.user_profile;
        allBlogListModel6.AuthorProfile = R.drawable.user_profile;

        allBlogListArrayList.clear();
        allBlogListArrayList.add(allBlogListModel);
        allBlogListArrayList.add(allBlogListModel1);
        allBlogListArrayList.add(allBlogListModel2);
        allBlogListArrayList.add(allBlogListModel3);
        allBlogListArrayList.add(allBlogListModel4);
        allBlogListArrayList.add(allBlogListModel5);
        allBlogListArrayList.add(allBlogListModel6);

        binding.rvBlogs.setHasFixedSize(true);
        binding.rvBlogs.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        binding.rvBlogs.setAdapter(new AllBlogListAdapter(activity, allBlogListArrayList));
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.llBtnShort) {
            bottomSheetDialog.show();
        } else if (view == bottomSheetBlogShortBinding.mcvAtoZ) {
            bottomSheetBlogShortBinding.mcvAtoZ.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.TvBgColor));
            bottomSheetBlogShortBinding.mcvAtoZ.setStrokeColor(ContextCompat.getColor(activity, R.color.theme));
            bottomSheetBlogShortBinding.tvAtoZ.setTextColor(ContextCompat.getColor(activity, R.color.theme));

            bottomSheetBlogShortBinding.mcvZtoA.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.background));
            bottomSheetBlogShortBinding.mcvZtoA.setStrokeColor(ContextCompat.getColor(activity, R.color.StrokeColorLightBlue));
            bottomSheetBlogShortBinding.tvZtoA.setTextColor(ContextCompat.getColor(activity, R.color.primaryColor));
        } else if (view == bottomSheetBlogShortBinding.mcvZtoA) {
            bottomSheetBlogShortBinding.mcvZtoA.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.TvBgColor));
            bottomSheetBlogShortBinding.mcvZtoA.setStrokeColor(ContextCompat.getColor(activity, R.color.theme));
            bottomSheetBlogShortBinding.tvZtoA.setTextColor(ContextCompat.getColor(activity, R.color.theme));

            bottomSheetBlogShortBinding.mcvAtoZ.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.background));
            bottomSheetBlogShortBinding.mcvAtoZ.setStrokeColor(ContextCompat.getColor(activity, R.color.StrokeColorLightBlue));
            bottomSheetBlogShortBinding.tvAtoZ.setTextColor(ContextCompat.getColor(activity, R.color.primaryColor));
        } else if (view == binding.llBtnFilter) {
            bottomSheetFilterTask();
        }
    }

    private void bottomSheetFilterTask() {

        bottomSheetFilter = new BottomSheetDialog(activity);
        bottomSheetBlogFilterBinding = BottomSheetBlogFilterBinding.inflate(getLayoutInflater());
        bottomSheetFilter.setContentView(bottomSheetBlogFilterBinding.getRoot());

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetBlogFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetFilter.show();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add("Category");
        title.add("Date");
        title.add("Author");

        fragments.add(new CategoryFragment());
      //  fragments.add(new DateFragment());
        fragments.add(new AuthorFragment());

        // TabLayout tabLayout = bottomSheetFilter.findViewById(R.id.tlFilter);
        //  ViewPager2 viewPager2 = bottomSheetFilter.findViewById(R.id.vpLaunchId);
     //   bottomSheetBlogFilterBinding.vpLaunchId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(bottomSheetBlogFilterBinding.tlFilter, bottomSheetBlogFilterBinding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();

        View tab1 = ((ViewGroup) bottomSheetBlogFilterBinding.tlFilter.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();
    }
}