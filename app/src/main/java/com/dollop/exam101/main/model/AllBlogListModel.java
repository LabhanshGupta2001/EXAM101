package com.dollop.exam101.main.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllBlogListModel {
    @SerializedName("blogCatId")
    @Expose
    public String blogCatId;
    @SerializedName("blogCatUuid")
    @Expose
    public String blogCatUuid;
    @SerializedName("blogCatUrlSlug")
    @Expose
    public String blogCatUrlSlug;
    @SerializedName("blogCatName")
    @Expose
    public String blogCatName;
    @SerializedName("blogId")
    @Expose
    public String blogId;
    @SerializedName("blogUuid")
    @Expose
    public String blogUuid;
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
    @SerializedName("blogAuthorName")
    @Expose
    public String blogAuthorName;
    @SerializedName("authorbio")
    @Expose
    public String authorbio;
    @SerializedName("authorImage")
    @Expose
    public String authorImage;
    @SerializedName("authorFBLink")
    @Expose
    public String authorFBLink;
    @SerializedName("authorInstaLink")
    @Expose
    public String authorInstaLink;
    @SerializedName("authorLinkdedLink")
    @Expose
    public String authorLinkdedLink;
    @SerializedName("authorYoutubeLink")
    @Expose
    public String authorYoutubeLink;
    @SerializedName("authorTwitLink")
    @Expose
    public String authorTwitLink;
}
