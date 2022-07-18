package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("studentId")
@Expose
public String studentId;
@SerializedName("studentName")
@Expose
public String studentName;
@SerializedName("studentEmail")
@Expose
public String studentEmail;
@SerializedName("studentMobileNo")
@Expose
public String studentMobileNo;
@SerializedName("countryCode")
@Expose
public String countryCode;
@SerializedName("countryName")
@Expose
public String countryName;
@SerializedName("stateName")
@Expose
public String stateName;
@SerializedName("fcmId")
@Expose
public String fcmId;
@SerializedName("mobileVerified")
@Expose
public String mobileVerified;
@SerializedName("emailVerified")
@Expose
public String emailVerified;
@SerializedName("roleType")
@Expose
public String roleType;
@SerializedName("token")
@Expose
public String token;

}