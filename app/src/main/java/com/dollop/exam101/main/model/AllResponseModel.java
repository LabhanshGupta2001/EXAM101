package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllResponseModel {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("is_notification")
    @Expose
    public String isNotification;

    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("OTP")
    @Expose
    public String otp;

}