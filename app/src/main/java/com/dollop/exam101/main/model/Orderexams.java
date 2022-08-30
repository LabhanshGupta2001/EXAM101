package com.dollop.exam101.main.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderexams {

@SerializedName("examId")
@Expose
public String examId;
@SerializedName("examUuid")
@Expose
public String examUuid;
@SerializedName("examName")
@Expose
public String examName;
@SerializedName("topicIds")
@Expose
public String topicIds;
@SerializedName("completedPercentage")
@Expose
public String completedPercentage;
@SerializedName("lastActivityDate")
@Expose
public String lastActivityDate;
@SerializedName("subjects")
@Expose
public List<Subject> subjects = null;

}