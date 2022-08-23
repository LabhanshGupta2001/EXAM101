package com.dollop.exam101.main.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintModel {

    @SerializedName("complaintId")
    @Expose
    public String complaintId;
    @SerializedName("complaintSubject")
    @Expose
    public String complaintSubject;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("complaintDescription")
    @Expose
    public String complaintDescription;
    @SerializedName("complaintPriority")
    @Expose
    public String complaintPriority;
    @SerializedName("attachment")
    @Expose
    public String attachment;
    @SerializedName("complaintRemark")
    @Expose
    public String complaintRemark;
    @SerializedName("complaintStatus")
    @Expose
    public String complaintStatus;
    @SerializedName("createdDtm")
    @Expose
    public String createdDtm;

}