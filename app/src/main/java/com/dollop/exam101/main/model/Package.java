package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Package {

@SerializedName("packageId")
@Expose
public String packageId;
@SerializedName("packageName")
@Expose
public String packageName;
@SerializedName("featureImg")
@Expose
public String featureImg;
@SerializedName("mainImg")
@Expose
public String mainImg;
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
@SerializedName("studentCnt")
@Expose
public String studentCnt;
@SerializedName("rating")
@Expose
public String rating;

}