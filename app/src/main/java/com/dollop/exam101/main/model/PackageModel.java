package com.dollop.exam101.main.model;

public class PackageModel
{
    public String packageHeading;
    public String packageDescription;
    public String rupes;
    public String totalRupes;
    public String days;
    public String ratingNum;
    public String ratingCount;

    public PackageModel() {
    }

    public PackageModel(String packageHeading, String packageDescription) {
        this.packageHeading = packageHeading;
        this.packageDescription = packageDescription;
    }

    public PackageModel(String packageHeading, String packageDescription, String rupes, String totalRupes, String days, String ratingNum, String ratingCount) {
        this.packageHeading = packageHeading;
        this.packageDescription = packageDescription;
        this.rupes = rupes;
        this.totalRupes = totalRupes;
        this.days = days;
        this.ratingNum = ratingNum;
        this.ratingCount = ratingCount;
    }
}
