package com.dollop.exam101.main.model;

public class CountryItem {

    private String code;
    private int image;

    public CountryItem(String code, int image) {
        this.code = code;
        this.image = image;
    }

    public String getCode()
    {
        return code;
    }
    public int getImage()
    {
        return image;
    }

}
