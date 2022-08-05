package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QuestionModel {

    @SerializedName("questionId")
    @Expose
    public String questionId;
    @SerializedName("questionUuid")
    @Expose
    public String questionUuid;
    @SerializedName("question")
    @Expose
    public String question;
    @SerializedName("options")
    @Expose
    public String options;

}