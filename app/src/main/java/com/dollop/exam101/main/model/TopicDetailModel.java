package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TopicDetailModel {

    @SerializedName("topicId")
    @Expose
    public String topicId;
    @SerializedName("topicUuid")
    @Expose
    public String topicUuid;
    @SerializedName("topicName")
    @Expose
    public String topicName;
    @SerializedName("topicSummary")
    @Expose
    public String topicSummary;
    @SerializedName("topicDetail")
    @Expose
    public String topicDetail;
    @SerializedName("pdfFile")
    @Expose
    public ArrayList<String> pdfFile = null;
    @SerializedName("video")
    @Expose
    public ArrayList<Video> video = null;
    @SerializedName("orderExamUuid")
    @Expose
    public String orderExamUuid;
    @SerializedName("orderExamId")
    @Expose
    public String orderExamId;

}