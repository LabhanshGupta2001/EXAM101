package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentMockTest {

    @SerializedName("orderMockTestId")
    @Expose
    public String orderMockTestId;
    @SerializedName("mockTestName")
    @Expose
    public String mockTestName;
    @SerializedName("createdDtm")
    @Expose
    public String createdDtm;
    @SerializedName("packageExpiryDtm")
    @Expose
    public String packageExpiryDtm;
    @SerializedName("attemptRemaining")
    @Expose
    public String attemptRemaining;
    @SerializedName("remainingDays")
    @Expose
    public String remainingDays;

}