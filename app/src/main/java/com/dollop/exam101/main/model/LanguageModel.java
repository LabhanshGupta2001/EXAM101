package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageModel {

@SerializedName("languageId")
@Expose
public String languageId;
@SerializedName("languageName")
@Expose
public String languageName;

}