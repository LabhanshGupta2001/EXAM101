package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestResultQuestion {

@SerializedName("testAttemptQuestionId")
@Expose
public String testAttemptQuestionId;
@SerializedName("questionId")
@Expose
public String questionId;
@SerializedName("questionUuid")
@Expose
public String questionUuid;
@SerializedName("question")
@Expose
public String question;
@SerializedName("questionResult")
@Expose
public String questionResult;
@SerializedName("solution")
@Expose
public String solution;
@SerializedName("correctAns")
@Expose
public String correctAns;
@SerializedName("studentAnswer")
@Expose
public String studentAnswer;

}