package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

@SerializedName("type")
@Expose
public String type;
@SerializedName("url")
@Expose
public String url;

}