package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseModel
{
    @SerializedName("examId")
    @Expose
    public String examId;
    @SerializedName("examName")
    @Expose
    public String examName;
    @SerializedName("uuid")
    @Expose
    public String uuid;
}
