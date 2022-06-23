package com.dollop.exam101.model;

public class CountryItemModel {

    private String code;
    private int image;

    public CountryItemModel(String code, int image) {
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
