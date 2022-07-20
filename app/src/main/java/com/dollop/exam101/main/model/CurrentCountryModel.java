package com.dollop.exam101.main.model;

public class CurrentCountryModel {
    public String Region;
    public String CountryCode;
    public String countryName;

    public CurrentCountryModel(String region, String countryCode,String countryName) {
        Region = region;
        CountryCode = countryCode;
        this.countryName = countryName;
    }

}
