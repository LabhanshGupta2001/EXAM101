package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogListHeadingModel {
    @SerializedName("blogCatId")
    @Expose
    public String blogCatId;
    @SerializedName("blogCatUrlSlug")
    @Expose
    public String blogCatUrlSlug;
    @SerializedName("blogCatName")
    @Expose
    public String blogCatName;
    @SerializedName("blogCatUuid")
    @Expose
    public String blogCatUuid;
}
