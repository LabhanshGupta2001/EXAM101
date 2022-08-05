package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class QuestionListModel {

    @SerializedName("orderExamUuid")
    @Expose
    public String orderExamUuid;
    @SerializedName("orderExamId")
    @Expose
    public String orderExamId;
    @SerializedName("topicUuid")
    @Expose
    public String topicUuid;
    @SerializedName("topicId")
    @Expose
    public String topicId;
    @SerializedName("questions")
    @Expose
    public List<QuestionModel> questions = null;

}