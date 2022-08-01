package com.dollop.exam101.main.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewRating {
    @SerializedName("reviewRatingId")
    @Expose
    public String reviewRatingId;
    @SerializedName("packageId")
    @Expose
    public String packageId;
    @SerializedName("rattingUuid")
    @Expose
    public String rattingUuid;
    @SerializedName("studentId")
    @Expose
    public String studentId;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("createdDtm")
    @Expose
    public String createdDtm;
    @SerializedName("review")
    @Expose
    public String review;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("studentName")
    @Expose
    public String studentName;
}