package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectModel {

@SerializedName("subjectId")
@Expose
public String subjectId;
@SerializedName("subjectName")
@Expose
public String subjectName;
@SerializedName("subjectUuid")
@Expose
public String subjectUuid;
@SerializedName("modules")
@Expose
public List<ModuleModel> modules = null;

}