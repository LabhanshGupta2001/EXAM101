package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemAllBlogsBinding;
import com.dollop.exam101.main.activity.BlogDetailActivity;
import com.dollop.exam101.main.model.AllBlogListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllBlogListAdapter extends RecyclerView.Adapter<AllBlogListAdapter.MyHolder> {
    Context context;
    ArrayList<AllBlogListModel> allBlogListModelArrayList;

    public AllBlogListAdapter(Context context, ArrayList<AllBlogListModel> allBlogListModelArrayList) {
        this.context = context;
        this.allBlogListModelArrayList = allBlogListModelArrayList;
    }

    @NonNull
    @Override
    public AllBlogListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllBlogsBinding binding = ItemAllBlogsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AllBlogListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBlogListAdapter.MyHolder holder, int position) {
        AllBlogListModel allBlogListModel = allBlogListModelArrayList.get(position);
        holder.binding.tvDate.setText(allBlogListModel.blogDate);
        holder.binding.tvMainBlogHeading.setText(allBlogListModel.blogTitle);
        holder.binding.tvNewsHeading.setText(allBlogListModel.blogTitle);
        holder.binding.tvAuthorName.setText(allBlogListModel.authorName);
        Utils.Picasso(allBlogListModel.mainImg,holder.binding.ivMainBlogsImg, R.drawable.dummy);
        Utils.Picasso(allBlogListModel.featureImg,holder.binding.ivAuthorProfile, R.drawable.dummy);
        holder.binding.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(Constants.Key.urlSlug,allBlogListModel.authorName);
                Utils.I(context, BlogDetailActivity.class,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allBlogListModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemAllBlogsBinding binding;

        public MyHolder(@NonNull ItemAllBlogsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
