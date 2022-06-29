package com.dollop.exam101.main.model;

public class CourseListModel {

    public String TopicName;
    public String CourseName;
    public String ExpireDate;
    public String LeftDate;
    public String PackageDurationDay;

    public CourseListModel() {
    }

    public CourseListModel(String topicName, String courseName, String expireDate, String leftDate, String packageDurationDay) {
        TopicName = topicName;
        CourseName = courseName;
        ExpireDate = expireDate;
        LeftDate = leftDate;
        PackageDurationDay = packageDurationDay;
    }
}
