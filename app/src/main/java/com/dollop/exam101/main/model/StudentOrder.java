package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentOrder {

@SerializedName("orderPackageId")
@Expose
public String orderPackageId;
@SerializedName("orderId")
@Expose
public String orderId;
@SerializedName("packageName")
@Expose
public String packageName;
@SerializedName("createdDtm")
@Expose
public String createdDtm;
@SerializedName("finalPackageAmt")
@Expose
public String finalPackageAmt;
@SerializedName("transactionId")
@Expose
public String transactionId;

}