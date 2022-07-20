package com.dollop.exam101.main.model;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllBlogListModel {

    @SerializedName("blogCatId")
    @Expose
    public String blogCatId;
    @SerializedName("blogCatUrlSlug")
    @Expose
    public String blogCatUrlSlug;
    @SerializedName("blogCatName")
    @Expose
    public String blogCatName;
    @SerializedName("blogId")
    @Expose
    public String blogId;
    @SerializedName("blogUrlSlug")
    @Expose
    public String blogUrlSlug;
    @SerializedName("authorName")
    @Expose
    public String authorName;
    @SerializedName("blogDate")
    @Expose
    public String blogDate;
    @SerializedName("blogTitle")
    @Expose
    public String blogTitle;
    @SerializedName("featureImg")
    @Expose
    public String featureImg;
    @SerializedName("mainImg")
    @Expose
    public String mainImg;
    @SerializedName("blogSortDesc")
    @Expose
    public String blogSortDesc;
    @SerializedName("blogDetail")
    @Expose
    public String blogDetail;

}
