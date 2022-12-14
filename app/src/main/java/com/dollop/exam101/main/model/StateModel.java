package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateModel {
    @SerializedName("stateId")
    @Expose
    public String stateId;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("stateName")
    @Expose
    public String stateName;

}