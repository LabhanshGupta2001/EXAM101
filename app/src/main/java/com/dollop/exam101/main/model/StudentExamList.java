package com.dollop.exam101.main.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentExamList {

@SerializedName("message")
@Expose
public String message;
@SerializedName("exams")
@Expose
public List<Exam> exams = null;

}