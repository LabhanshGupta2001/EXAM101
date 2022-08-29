package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailsModel {

    @SerializedName("affiliateCode")
    @Expose
    public String affiliateCode;
    @SerializedName("accPayeeName")
    @Expose
    public String accPayeeName;
    @SerializedName("accNumber")
    @Expose
    public String accNumber;
    @SerializedName("ifscCode")
    @Expose
    public String ifscCode;
    @SerializedName("acBranchName")
    @Expose
    public String acBranchName;
    @SerializedName("accType")
    @Expose
    public String accType;

}