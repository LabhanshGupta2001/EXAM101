package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockTestQuestion {

    @SerializedName("orderMockTestId")
    @Expose
    public String orderMockTestId;
    @SerializedName("mockTestName")
    @Expose
    public String mockTestName;
    @SerializedName("orderMockTestUuid")
    @Expose
    public String orderMockTestUuid;
    @SerializedName("questionCnt")
    @Expose
    public Integer questionCnt;
    @SerializedName("duration")
    @Expose
    public String duration;
    @SerializedName("questions")
    @Expose
    public List<Question> questions = null;
}