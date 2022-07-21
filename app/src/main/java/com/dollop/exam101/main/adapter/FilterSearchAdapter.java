package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemBlogsHorizontalBinding;
import com.dollop.exam101.databinding.ItemCategoryBinding;
import com.dollop.exam101.main.activity.BlogsListActivity;
import com.dollop.exam101.main.model.BlogListHeadingModel;

import java.util.ArrayList;
import java.util.List;

public class FilterSearchAdapter extends RecyclerView.Adapter<FilterSearchAdapter.MyHolder> implements Filterable {
    Context context;
    ArrayList<BlogListHeadingModel> blogListHeadingModelArrayList;
    List<BlogListHeadingModel> filterList;

    int pos=0;

    public FilterSearchAdapter(Context context, ArrayList<BlogListHeadingModel> blogListHeadingModelArrayList) {
        this.context = context;
        this.blogListHeadingModelArrayList = blogListHeadingModelArrayList;
        filterList = blogListHeadingModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new FilterSearchAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        BlogListHeadingModel blogListHeadingModel = filterList.get(position);
        holder.binding.tvItem.setText(blogListHeadingModel.blogCatName);

        holder.binding.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BlogsListActivity)context).getBlogsData(blogListHeadingModel.blogCatUrlSlug);
              //  new BlogsListAdapter(context,blogListHeadingModelArrayList,blogListHeadingModel.blogCatId);

             //   ((BlogsListActivity)context).DataChangeBlogListAdapter(holder.getAdapterPosition());
                ((BlogsListActivity)context).bottomSheetFilter.dismiss();
            }
        });
       }



    @Override
    public int getItemCount() {
        return filterList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterList = blogListHeadingModelArrayList;
                } else {
                    List<BlogListHeadingModel> filteredList = new ArrayList<>();
                    for (BlogListHeadingModel row : blogListHeadingModelArrayList) {
                        if (row.blogCatName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (List<BlogListHeadingModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public MyHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
