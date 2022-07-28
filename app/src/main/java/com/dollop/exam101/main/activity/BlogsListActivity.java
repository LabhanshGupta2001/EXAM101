package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.AppController;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityBlogsListBinding;
import com.dollop.exam101.databinding.AlertdialogBinding;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.databinding.BottomSheetBlogShortBinding;
import com.dollop.exam101.databinding.ItemAllBlogsBinding;
import com.dollop.exam101.databinding.ItemBlogsHorizontalBinding;
import com.dollop.exam101.main.adapter.AllBlogListAdapter;
import com.dollop.exam101.main.adapter.BlogsListAdapter;
import com.dollop.exam101.main.adapter.FilterSearchAdapter;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.BlogListHeadingModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogsListActivity extends BaseActivity implements View.OnClickListener {
    Activity activity = BlogsListActivity.this;
    ActivityBlogsListBinding binding;
    BottomSheetDialog bottomSheetDialog;
    public BottomSheetDialog bottomSheetFilter;
    ApiService apiService;
    public BlogsListAdapter blogsListAdapter;
    AllBlogListAdapter allBlogListAdapter;
    FilterSearchAdapter filterSearchAdapter;
    public int position;
    ArrayList<AllBlogListModel> Blogarraylist = new ArrayList<>();


    private final ArrayList<BlogListHeadingModel> blogsList = new ArrayList<>();
    private final ArrayList<BlogListHeadingModel> blogsListFilter = new ArrayList<>();

    ItemBlogsHorizontalBinding itemBlogsHorizontalBinding;

    ItemAllBlogsBinding itemAllBlogsBinding;
    ArrayList<AllBlogListModel> allBlogListArrayList = new ArrayList<>();

    BottomSheetBlogShortBinding bottomSheetBlogShortBinding;
    BottomSheetBlogFilterBinding bottomSheetBlogFilterBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init() {

        apiService = RetrofitClient.getClient();

        if (AppController.getInstance().isOnline()) {
            getBlogsCategory(Constants.Key.blank);
            getBlogsData(Constants.Key.blank);
        } else {
            //Utils.InternetDialog(activity);
            InternetDialog();
        }

        binding.ivBack.setOnClickListener(this);
        binding.llBtnShort.setOnClickListener(this);
        binding.llBtnFilter.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetBlogShortBinding = BottomSheetBlogShortBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBlogShortBinding.getRoot());
        bottomSheetBlogShortBinding.mcvAtoZ.setOnClickListener(this);
        bottomSheetBlogShortBinding.mcvZtoA.setOnClickListener(this);
        allBlogListAdapter = new AllBlogListAdapter(activity, Blogarraylist);
        binding.rvBlogs.setAdapter(allBlogListAdapter);
        binding.rvBlogs.setLayoutManager(new LinearLayoutManager(activity));
        blogsListAdapter = new BlogsListAdapter(activity, blogsList);
        binding.rvHorizontalHeading.setAdapter(blogsListAdapter);
        binding.rvHorizontalHeading.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.llBtnShort) {
            //   bottomSheetDialog.show();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void bottomSheetFilterTask() {
        bottomSheetFilter = new BottomSheetDialog(activity);
        bottomSheetBlogFilterBinding = BottomSheetBlogFilterBinding.inflate(getLayoutInflater());
        bottomSheetFilter.setContentView(bottomSheetBlogFilterBinding.getRoot());
        filterSearchAdapter = new FilterSearchAdapter(activity, blogsListFilter);
        bottomSheetBlogFilterBinding.rvAuthorSearch.setAdapter(filterSearchAdapter);
        bottomSheetBlogFilterBinding.rvAuthorSearch.setLayoutManager(new LinearLayoutManager(activity));
        bottomSheetBlogFilterBinding.llApply.setOnClickListener(view ->
        {
            bottomSheetFilter.cancel();
        });

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) (bottomSheetBlogFilterBinding.getRoot().getParent()));
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetFilter.show();
        bottomSheetBlogFilterBinding.searchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterSearchAdapter.getFilter().filter(s.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterSearchAdapter.getFilter().filter(s.trim());
                return false;
            }
        });
        if (AppController.getInstance().isOnline()) {
            getBlogsCategory(Constants.Key.From);
        } else {
           // Utils.InternetDialog(activity);
            InternetDialog();
        }

     /*   ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        title.add("Category");
        title.add("Date");
        title.add("Author");

        fragments.add(new CategoryFragment());
        fragments.add(new DateFragment());
        fragments.add(new AuthorFragment());*/

     /*   TabLayout tabLayout = bottomSheetFilter.findViewById(R.id.tlFilter);
        ViewPager2 viewPager2 = bottomSheetFilter.findViewById(R.id.vpLaunchId);
        bottomSheetBlogFilterBinding.vpLaunchId.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(bottomSheetBlogFilterBinding.tlFilter, bottomSheetBlogFilterBinding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();*/
/*
        View tab1 = ((ViewGroup) bottomSheetBlogFilterBinding.tlFilter.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p.setMargins(20, 0, 20, 0);
        tab1.requestLayout();*/
    }

    private void getBlogsCategory(String from) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogsCategory().enqueue(new Callback<AllResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        if (from.equals(Constants.Key.From)) {
                            blogsListFilter.clear();
                            blogsListFilter.addAll(response.body().blogsCat);
                            filterSearchAdapter.notifyDataSetChanged();
                        } else {
                            blogsList.clear();
                            blogsList.addAll(response.body().blogsCat);
                            blogsListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(activity, message.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }



    public void getBlogsData(String uuid) {
        Dialog progressDialog = Utils.initProgressDialog(activity);
        apiService.getBlogsData(uuid).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().blogs.isEmpty()) {
                        binding.rvHorizontalHeading.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvHorizontalHeading.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    Blogarraylist.clear();
                    if (response.code() == StatusCodeConstant.OK) {
                        assert response.body() != null;
                        Blogarraylist.addAll(response.body().blogs);
                        binding.rvBlogs.setHasFixedSize(true);
                        allBlogListAdapter.notifyDataSetChanged();

                    } else {
                        // assert response.errorBody() != null;
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() == StatusCodeConstant.BAD_REQUEST) {
                            Utils.T(activity, message.message);
                        } else if (response.code() == StatusCodeConstant.UNAUTHORIZED) {
                            Utils.T(activity, message.message);
                            Utils.UnAuthorizationToken(activity);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                t.printStackTrace();
                progressDialog.dismiss();
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void InternetDialog() {
        Dialog dialog = new Dialog(activity,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        AlertdialogBinding alertDialogBinding = AlertdialogBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        alertDialogBinding.tvPermittManually.setText(R.string.retry);
        alertDialogBinding.tvDesc.setText(R.string.please_check_your_connection);
        alertDialogBinding.tvPermittManually.setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                init();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}