package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockTestModel {

@SerializedName("mockTestId")
@Expose
public String mockTestId;
@SerializedName("mockTestUuid")
@Expose
public String mockTestUuid;
@SerializedName("mockTestName")
@Expose
public String mockTestName;
@SerializedName("questionCnt")
@Expose
public String questionCnt;
@SerializedName("duration")
@Expose
public String duration;
@SerializedName("attempts")
@Expose
public String attempts;

}