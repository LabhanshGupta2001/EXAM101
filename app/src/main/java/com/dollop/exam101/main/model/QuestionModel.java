package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


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
    public List<OptionModel> options = null;

    public boolean isClicked = false;
    public int answer = 0;

}