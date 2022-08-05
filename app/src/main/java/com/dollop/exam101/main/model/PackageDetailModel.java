package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageDetailModel {

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
    @SerializedName("packageUuid")
    @Expose
    public String packageUuid;
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
    @SerializedName("packageDetail")
    @Expose
    public String packageDetail;
    @SerializedName("mocktestIds")
    @Expose
    public String mocktestIds;
    @SerializedName("languageIds")
    @Expose
    public String languageIds;
    @SerializedName("exams")
    @Expose
    public List<ExamModel> examModels = null;
    @SerializedName("mockTests")
    @Expose
    public List<MockTestModel> mockTests = null;
    @SerializedName("languages")
    @Expose
    public List<LanguageModel> languageModels = null;

}