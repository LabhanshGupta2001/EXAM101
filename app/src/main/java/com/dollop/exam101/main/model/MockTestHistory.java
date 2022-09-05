package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockTestHistory {

    @SerializedName("testAttemptId")
    @Expose
    public String testAttemptId;
    @SerializedName("createdDtm")
    @Expose
    public String createdDtm;
    @SerializedName("mockTestName")
    @Expose
    public String mockTestName;
    @SerializedName("questionCnt")
    @Expose
    public String questionCnt;
    @SerializedName("correctAnsCnt")
    @Expose
    public String correctAnsCnt;

}