package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {

@SerializedName("examId")
@Expose
public String examId;
@SerializedName("examUuid")
@Expose
public String examUuid;
@SerializedName("examName")
@Expose
public String examName;
@SerializedName("purchaseDtm")
@Expose
public String purchaseDtm;
@SerializedName("packageExpiryDtm")
@Expose
public String packageExpiryDtm;
@SerializedName("orderExamId")
@Expose
public String orderExamId;
@SerializedName("orderExamUuid")
@Expose
public String orderExamUuid;
@SerializedName("examUrlSlug")
@Expose
public String examUrlSlug;

}