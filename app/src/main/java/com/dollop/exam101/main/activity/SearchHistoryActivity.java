package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.SearchHistoryTable;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.BaseActivity;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.StatusCodeConstant;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ActivitySearchHistoryBinding;
import com.dollop.exam101.main.adapter.PackageAdapter;
import com.dollop.exam101.main.adapter.ResentSearchHistoryAdapter;
import com.dollop.exam101.main.model.AllResponseModel;
import com.dollop.exam101.main.model.PackageModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHistoryActivity extends BaseActivity implements View.OnClickListener {
    public ActivitySearchHistoryBinding binding;
    Activity activity = SearchHistoryActivity.this;
    ApiService apiService;
    ArrayList<SearchHistoryTable> searchHistoryTableArrayList = new ArrayList<>();
    ResentSearchHistoryAdapter adapter;
    ArrayList<PackageModel> packageModelList = new ArrayList<>();
    PackageAdapter packageAdapter;
    SearchHistoryTable searchHistoryTable = new SearchHistoryTable();


    private void init() {
        apiService = RetrofitClient.getClient();
        binding.ivBack.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);
        packageList();

        searchHistoryTableArrayList.clear();
        searchHistoryTableArrayList = UserDataHelper.getInstance().getSearchList();

        if (searchHistoryTableArrayList == null || searchHistoryTableArrayList.isEmpty()) {
            binding.llSearchHistory.setVisibility(View.GONE);
            binding.rvSearchHistory.setVisibility(View.GONE);
            //binding.rvPackages.setVisibility(View.VISIBLE);
        } else {
            binding.llSearchHistory.setVisibility(View.VISIBLE);
            binding.rvSearchHistory.setVisibility(View.VISIBLE);
        }

        adapter = new ResentSearchHistoryAdapter(activity, searchHistoryTableArrayList);
        binding.rvSearchHistory.setHasFixedSize(true);
        binding.rvSearchHistory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        binding.rvSearchHistory.setAdapter(adapter);


        binding.SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().equals(Constants.Key.blank)) {
                    binding.llSearchHistory.setVisibility(View.VISIBLE);
                    binding.rvSearchHistory.setVisibility(View.VISIBLE);
                    binding.rvPackages.setVisibility(View.GONE);
                    binding.noResultFoundId.llParent.setVisibility(View.GONE);
                } else {
                    searchHistoryTable.searchItem = query.trim();
                    UserDataHelper.getInstance().insertSearchHistoryData(searchHistoryTable);
                    packageAdapter.getFilter().filter(query.trim());
                    searchHistoryTableArrayList = UserDataHelper.getInstance().getSearchList();
                    Utils.E("222searchHistoryTableArrayList::" + searchHistoryTableArrayList);
                    if (searchHistoryTableArrayList.size() > 6) {
                        UserDataHelper.getInstance().deleteFirstRow();
                    }
                  //  UserDataHelper.getInstance().isExistData(query);
                    binding.rvPackages.setVisibility(View.VISIBLE);
                    binding.llSearchHistory.setVisibility(View.GONE);
                    binding.rvSearchHistory.setVisibility(View.GONE);

                    adapter = new ResentSearchHistoryAdapter(activity, searchHistoryTableArrayList);
                    binding.rvSearchHistory.setHasFixedSize(true);
                    binding.rvSearchHistory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
                    binding.rvSearchHistory.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().equals(Constants.Key.blank)) {
                    if (searchHistoryTableArrayList.isEmpty()) {
                        binding.llSearchHistory.setVisibility(View.GONE);
                        binding.rvSearchHistory.setVisibility(View.GONE);
                    } else {
                        binding.llSearchHistory.setVisibility(View.VISIBLE);
                        binding.rvSearchHistory.setVisibility(View.VISIBLE);
                    }
                    binding.rvPackages.setVisibility(View.GONE);
                    binding.noResultFoundId.llParent.setVisibility(View.GONE);
                } else {
                    packageAdapter.getFilter().filter(newText.trim());
                    binding.rvPackages.setVisibility(View.VISIBLE);
                    binding.llSearchHistory.setVisibility(View.GONE);
                    binding.rvSearchHistory.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

        // flexbox set
     /*   arrayList.clear();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        binding.rvFlexBox.setHasFixedSize(true);
        binding.rvFlexBox.setLayoutManager(layoutManager);
        binding.rvFlexBox.setAdapter(new FlexBoxAdapter(activity, arrayList));*/

    }


   /* @SuppressLint("NotifyDataSetChanged")
    private void packagesharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.Key.SearchHistoryItem, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        set.addAll(arrayList);
        editor.putStringSet(Constants.Key.SearchHistoryItem, set);
        editor.apply();
        Utils.E("AddSetData:::"+set);
        adapter.notifyDataSetChanged();
    }*/

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            finish();
        } else if (view == binding.tvClear) {
            binding.llSearchHistory.setVisibility(View.GONE);
            binding.rvSearchHistory.setVisibility(View.GONE);
            searchHistoryTableArrayList.clear();
            UserDataHelper.getInstance().deleteSearchAll();
            adapter.notifyDataSetChanged();
        }
    }

    public void setPage() {
        Utils.E("set_noResultFoundId");
        if (binding.noResultFoundId.llParent.getVisibility() == View.GONE) {
            binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
            binding.rvPackages.setVisibility(View.GONE);
            binding.llSearchHistory.setVisibility(View.GONE);
            binding.rvSearchHistory.setVisibility(View.GONE);
        }
    }

    public void gonePage() {
        Utils.E("set_noResultFoundId");
        binding.noResultFoundId.llParent.setVisibility(View.GONE);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSearchText(String text) {
        binding.SearchView.setQuery(text, false);
        binding.SearchView.clearFocus();
        searchHistoryTable.searchItem = text.trim();
        UserDataHelper.getInstance().insertSearchHistoryData(searchHistoryTable);
        searchHistoryTableArrayList = UserDataHelper.getInstance().getSearchList();
        //adapter.notifyDataSetChanged();
        adapter = new ResentSearchHistoryAdapter(activity, searchHistoryTableArrayList);
        binding.rvSearchHistory.setHasFixedSize(true);
        binding.rvSearchHistory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        binding.rvSearchHistory.setAdapter(adapter);
    }

    private void packageList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.Key.examId, Constants.Key.blank);
        hashMap.put(Constants.Key.languageId, Constants.Key.blank);
        apiService.packageListItem(Utils.GetSession().token, hashMap).enqueue(new Callback<AllResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AllResponseModel> call, @NonNull Response<AllResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (response.body().packageModels.isEmpty()) {
                        binding.rvPackages.setVisibility(View.GONE);
                        binding.noResultFoundId.llParent.setVisibility(View.VISIBLE);
                    } else {
                        // binding.rvPackages.setVisibility(View.VISIBLE);
                        binding.noResultFoundId.llParent.setVisibility(View.GONE);
                    }
                    if (response.code() == StatusCodeConstant.OK) {
                        packageModelList.clear();
                        assert response.body() != null;
                        packageModelList.addAll(response.body().packageModels);

                        packageAdapter = new PackageAdapter(activity, packageModelList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                        binding.rvPackages.setLayoutManager(linearLayoutManager);
                        binding.rvPackages.setAdapter(packageAdapter);
                    } else {
                        assert response.errorBody() != null;
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        if (response.code() != StatusCodeConstant.BAD_REQUEST) {
                            if (response.code() == StatusCodeConstant.UNAUTHORIZED) {

                                Utils.UnAuthorizationToken(activity);
                            }
                        } else {
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
                Utils.E("getMessage::" + t.getMessage());
            }
        });
    }


    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // remove focus from edit text on click outside
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                final boolean globalVisibleRect;
                globalVisibleRect = v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}