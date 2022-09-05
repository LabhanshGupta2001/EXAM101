package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AffiliatePurchaseModel {

    @SerializedName("studentName")
    @Expose
    public String studentName;
    @SerializedName("studentEmail")
    @Expose
    public String studentEmail;
    @SerializedName("countryCode")
    @Expose
    public String countryCode;
    @SerializedName("studentMobileNo")
    @Expose
    public String studentMobileNo;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("pkgPrice")
    @Expose
    public String pkgPrice;
    @SerializedName("pkgAffCommisionPercent")
    @Expose
    public String pkgAffCommisionPercent;
    @SerializedName("pkgAffCommisionAmt")
    @Expose
    public String pkgAffCommisionAmt;
    @SerializedName("couponCode")
    @Expose
    public String couponCode;

}