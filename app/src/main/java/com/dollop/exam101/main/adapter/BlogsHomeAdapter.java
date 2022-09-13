package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemAllBlogsBinding;
import com.dollop.exam101.main.activity.BlogDetailActivity;
import com.dollop.exam101.main.model.AllBlogListModel;

import java.util.ArrayList;

public class BlogsHomeAdapter extends RecyclerView.Adapter<BlogsHomeAdapter.MyViewHolder> {
    ArrayList<AllBlogListModel> blogArraylist = new ArrayList<>();
    Context context;

    public BlogsHomeAdapter(Context context, ArrayList<AllBlogListModel> blogarraylist) {
        this.blogArraylist = blogarraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllBlogsBinding binding = ItemAllBlogsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AllBlogListModel allBlogListModel = blogArraylist.get(position);
        holder.binding.tvDate.setText(allBlogListModel.blogDate);
        holder.binding.tvMainBlogHeading.setText(allBlogListModel.blogTitle);
        holder.binding.tvNewsHeading.setText(allBlogListModel.blogTitle);
        holder.binding.tvAuthorName.setText(allBlogListModel.authorName);
        Utils.Picasso(allBlogListModel.featureImg, holder.binding.ivMainBlogsImg, R.drawable.dummy);
        Utils.Picasso(allBlogListModel.authorImage, holder.binding.ivAuthorProfile, R.drawable.dummy);
        holder.binding.tvReadMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.uuid, allBlogListModel.blogUuid);
            Utils.I(context, BlogDetailActivity.class, bundle);
        });
        holder.binding.ivMainBlogsImg.setOnClickListener(v -> {
            holder.binding.tvReadMore.performClick();
        });
    }

    @Override
    public int getItemCount() {
        return blogArraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAllBlogsBinding binding;

        public MyViewHolder(ItemAllBlogsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
