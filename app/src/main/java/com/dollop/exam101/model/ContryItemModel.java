package com.dollop.exam101.model;

public class ContryItemModel {

    private String code;
    private int image;

    public ContryItemModel(String code, int image) {
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
