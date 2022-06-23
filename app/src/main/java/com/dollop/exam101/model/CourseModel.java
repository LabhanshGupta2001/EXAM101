package com.dollop.exam101.model;

public class CourseModel
{
    public int image;
    public String name;


    public CourseModel(int image, String name) {
        this.image = image;
        this.name = name;
    }
    public CourseModel(String name) {
        this.name = name;
    }
}
