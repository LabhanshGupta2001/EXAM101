package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AffilliatPurchaseListModel {

    @SerializedName("grandTotalAmt")
    @Expose
    public Float grandTotalAmt;
    @SerializedName("receivedAmt")
    @Expose
    public Integer receivedAmt;
    @SerializedName("remainingAmt")
    @Expose
    public Float remainingAmt;
    @SerializedName("affiliatePurchaseList")
    @Expose
    public List<AffiliatePurchaseModel> affiliatePurchaseModelList = null;

}