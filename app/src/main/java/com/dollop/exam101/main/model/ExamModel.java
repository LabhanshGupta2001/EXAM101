package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ExamModel {

    @SerializedName("examId")
    @Expose
    public String examId;
    @SerializedName("examName")
    @Expose
    public String examName;
    @SerializedName("examUrlSlug")
    @Expose
    public String examUrlSlug;
    @SerializedName("examUuid")
    @Expose
    public String examUuid;
    @SerializedName("subjects")
    @Expose
    public List<SubjectModel> subjects = null;

}