package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {

@SerializedName("topicId")
@Expose
public String topicId;
@SerializedName("topicUuid")
@Expose
public String topicUuid;
@SerializedName("topicName")
@Expose
public String topicName;

}