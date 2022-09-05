package com.dollop.exam101.main.model;

import com.dollop.exam101.main.adapter.TestAnsAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("questionId")
    @Expose
    public String questionId;
    @SerializedName("question")
    @Expose
    public String question;

    @SerializedName("options")
    @Expose
    public List<Option> options = null;

    public int SelectedAnswer = -1;




    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", SelectedAnswer=" + SelectedAnswer +
                '}';
    }
}