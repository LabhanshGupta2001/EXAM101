package com.dollop.exam101.model;

public class NewsModel
{
    public String topic;
    public String newsHeading;
    public String date;
    public String time;
    public int image;

    public NewsModel() {
    }

    public NewsModel(String topic, String newsHeading, String date, String time, int image) {
        this.topic = topic;
        this.newsHeading = newsHeading;
        this.date = date;
        this.time = time;
        this.image = image;
    }


}
