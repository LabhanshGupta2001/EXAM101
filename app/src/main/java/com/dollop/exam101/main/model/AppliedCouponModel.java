package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppliedCouponModel {

    @SerializedName("subTotalAmt")
    @Expose
    public Float subTotalAmt;
    @SerializedName("gstPercentage")
    @Expose
    public String gstPercentage;
    @SerializedName("gstAmt")
    @Expose
    public Float gstAmt;
    @SerializedName("grandTotalAmt")
    @Expose
    public Float grandTotalAmt;
    @SerializedName("discountedAmt")
    @Expose
    public String discountedAmt;

}