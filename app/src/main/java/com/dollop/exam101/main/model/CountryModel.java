package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel {

        @SerializedName("countryId")
        @Expose
        public String countryId;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("currencyCode")
        @Expose
        public String currencyCode;
        @SerializedName("sortName")
        @Expose
        public String sortName;
        @SerializedName("currencySymbol")
        @Expose
        public String currencySymbol;
        @SerializedName("currencyName")
        @Expose
        public String currencyName;
        @SerializedName("phoneCode")
        @Expose
        public String phoneCode;
        @SerializedName("capital")
        @Expose
        public String capital;
        @SerializedName("flag")
        @Expose
        public String flag;
        @SerializedName("unit")
        @Expose
        public String unit;

        @Override
        public String toString() {
                return "CountryModel{" +
                        "countryId='" + countryId + '\'' +
                        ", countryName='" + countryName + '\'' +
                        ", currencyCode='" + currencyCode + '\'' +
                        ", sortName='" + sortName + '\'' +
                        ", currencySymbol='" + currencySymbol + '\'' +
                        ", currencyName='" + currencyName + '\'' +
                        ", phoneCode='" + phoneCode + '\'' +
                        ", capital='" + capital + '\'' +
                        ", flag='" + flag + '\'' +
                        ", unit='" + unit + '\'' +
                        '}';
        }
}
