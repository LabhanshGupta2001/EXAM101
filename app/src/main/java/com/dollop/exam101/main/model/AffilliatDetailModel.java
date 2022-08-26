package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AffilliatDetailModel implements Serializable {

    @SerializedName("affiliate_req_id")
    @Expose
    public String affiliateReqId;
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
    @SerializedName("reqStatus")
    @Expose
    public String reqStatus;

}