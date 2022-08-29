package com.dollop.exam101.main.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginHistoryDatum {

@SerializedName("studentLoginHistoryId")
@Expose
public String studentLoginHistoryId;
@SerializedName("studentId")
@Expose
public String studentId;
@SerializedName("sessionData")
@Expose
public SessionData sessionData;
@SerializedName("machineIp")
@Expose
public String machineIp;
@SerializedName("userAgent")
@Expose
public String userAgent;
@SerializedName("agentString")
@Expose
public String agentString;
@SerializedName("platform")
@Expose
public String platform;
@SerializedName("loginHistoryDtm")
@Expose
public String loginHistoryDtm;

}