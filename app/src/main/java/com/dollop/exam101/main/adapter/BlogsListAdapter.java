package com.dollop.exam101.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.main.model.BlogListHeadingModel;
import com.dollop.exam101.R;

import java.util.ArrayList;

public class BlogsListAdapter extends RecyclerView.Adapter<BlogsListAdapter.MyHolder> {
    Context context;
    ArrayList<BlogListHeadingModel> blogListHeadingModelArrayList;
    int pos=0;

    public BlogsListAdapter(Context context, ArrayList<BlogListHeadingModel> blogListHeadingModelArrayList) {
        this.context = context;
        this.blogListHeadingModelArrayList = blogListHeadingModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBlogsHorizontalBinding binding = ItemBlogsHorizontalBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BlogsListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        BlogListHeadingModel blogListHeadingModel = blogListHeadingModelArrayList.get(position);
        holder.binding.tvBlogHeading.setText(blogListHeadingModel.Heading);

        holder.binding.tvBlogHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });
        if (pos == position){
                holder.binding.tvBlogHeading.setBackgroundResource(R.drawable.theme_backround);
                holder.binding.tvBlogHeading.setTextColor(ContextCompat.getColor(context,R.color.white));
                holder.binding.cvCategories.setStrokeColor(ContextCompat.getColor(context,R.color.theme));

        } else{
                holder.binding.tvBlogHeading.setBackgroundResource(R.color.light_red);
                holder.binding.tvBlogHeading.setTextColor(ContextCompat.getColor(context,R.color.sub_text));
                holder.binding.cvCategories.setStrokeColor(ContextCompat.getColor(context,R.color.HorizontallineColor));
            }
       }

    @Override
    public int getItemCount() {
        return blogListHeadingModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemBlogsHorizontalBinding binding;

        public MyHolder(@NonNull ItemBlogsHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
