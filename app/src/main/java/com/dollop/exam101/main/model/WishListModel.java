package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListModel {

    @SerializedName("wishListId")
    @Expose
    public String wishListId;
    @SerializedName("studentId")
    @Expose
    public String studentId;
    @SerializedName("wishListUuid")
    @Expose
    public String wishListUuid;
    @SerializedName("packageId")
    @Expose
    public String packageId;
    @SerializedName("wishListCreatedDtm")
    @Expose
    public String wishListCreatedDtm;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("featureImg")
    @Expose
    public String featureImg;
    @SerializedName("mainImg")
    @Expose
    public String mainImg;
    @SerializedName("packageUuid")
    @Expose
    public String packageUuid;
    @SerializedName("isPaid")
    @Expose
    public String isPaid;
    @SerializedName("validity")
    @Expose
    public String validity;
    @SerializedName("actualPrice")
    @Expose
    public String actualPrice;
    @SerializedName("discountedPrice")
    @Expose
    public String discountedPrice;
    @SerializedName("shortDesc")
    @Expose
    public String shortDesc;
    @SerializedName("packageDetail")
    @Expose
    public String packageDetail;
    @SerializedName("studentCnt")
    @Expose
    public String studentCnt;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("languageUuid")
    @Expose
    public String languageUuid;

}