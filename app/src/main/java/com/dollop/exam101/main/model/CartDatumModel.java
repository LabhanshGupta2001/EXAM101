package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDatumModel {


    @SerializedName("packageId")
    @Expose
    public String packageId;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("cartUuid")
    @Expose
    public String cartUuid;
    @SerializedName("discountedPrice")
    @Expose
    public String discountedPrice;
    @SerializedName("packageUuid")
    @Expose
    public String packageUuid;
    @SerializedName("isWishListed")
    @Expose
    public String isWishListed;
    @SerializedName("wishListUuid")
    @Expose
    public String wishListUuid;
}