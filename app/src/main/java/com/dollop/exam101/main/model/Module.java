package com.dollop.exam101.main.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

@SerializedName("moduleId")
@Expose
public String moduleId;
@SerializedName("moduleUuid")
@Expose
public String moduleUuid;
@SerializedName("moduleName")
@Expose
public String moduleName;
@SerializedName("topics")
@Expose
public List<Topic> topics = null;

}