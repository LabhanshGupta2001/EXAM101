package com.dollop.exam101.main.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.main.activity.BlogDetailActivity;
import com.dollop.exam101.main.model.AllBlogListModel;

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
            holder.binding.tvDate.setText(allBlogListModel.Date);
            holder.binding.tvMainBlogHeading.setText(allBlogListModel.MainBlogHeading);
            holder.binding.tvNewsHeading.setText(allBlogListModel.NewsHeading);
            holder.binding.tvAuthorName.setText(allBlogListModel.AuthorName);
            holder.binding.ivMainBlogsImg.setImageResource(allBlogListModel.BlogMainImg);
            holder.binding.ivAuthorProfile.setImageResource(allBlogListModel.AuthorProfile);

            holder.binding.tvReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.I(context, BlogDetailActivity.class,null);
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
