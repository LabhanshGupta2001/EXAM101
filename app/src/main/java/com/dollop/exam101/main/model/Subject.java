package com.dollop.exam101.main.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

@SerializedName("subjectId")
@Expose
public String subjectId;
@SerializedName("subjectUuid")
@Expose
public String subjectUuid;
@SerializedName("subjectName")
@Expose
public String subjectName;
@SerializedName("modules")
@Expose
public List<Module> modules = null;

}