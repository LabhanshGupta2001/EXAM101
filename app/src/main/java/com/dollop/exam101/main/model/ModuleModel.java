package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModuleModel {

@SerializedName("moduleId")
@Expose
public String moduleId;
@SerializedName("moduleName")
@Expose
public String moduleName;
@SerializedName("moduleUuid")
@Expose
public String moduleUuid;
@SerializedName("topics")
@Expose
public List<TopicDetailModel> topics = null;

}