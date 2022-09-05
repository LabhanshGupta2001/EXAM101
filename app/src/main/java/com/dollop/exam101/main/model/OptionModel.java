package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OptionModel {

    @SerializedName("options")
    @Expose
    public String options;
    @SerializedName("options_1")
    @Expose
    public String options1;
    @SerializedName("options_2")
    @Expose
    public String options2;

}