package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerModel {

    @SerializedName("bannerId")
    @Expose
    public String bannerId;
    @SerializedName("bannerUuid")
    @Expose
    public String bannerUuid;
    @SerializedName("bannerSlug")
    @Expose
    public String bannerSlug;
    @SerializedName("bannerRedirectToId")
    @Expose
    public String bannerRedirectToId;
    @SerializedName("bannerType")
    @Expose
    public String bannerType;
    @SerializedName("bannerFor")
    @Expose
    public String bannerFor;
    @SerializedName("bannerImage")
    @Expose
    public String bannerImage;
    @SerializedName("redirectUuid")
    @Expose
    public String redirectUuid;

}