package com.dollop.exam101.Basics.Retrofit;


import com.dollop.exam101.Basics.UtilityTools.Constants;


public class Const {

    //Environment  **NOTE**  Change "Live" With "Debug"  When Going Live
    public static String Development = Constants.Debug;
    //Live
    public static final String HOST_URL = "http://examoneOoneweb.dollopinfotech.com/";

    //Versioning URL
    public static final String MAIN_HOST_URL = HOST_URL + "v12/";


    public static final String FLAG_URL = "https://countryflagsapi.com/png/";

    public static final String get_otp = "get_otp";
    public static final String match_otp = "match_otp";
    public static final String social_login = "social_login";
    public static final String user_signup = "user_signup";
    public static final String getUserProfileApi = "getUserProfileApi";
    public static final String country_code = "country_code.json";

    public static final String updateUserProfileApi = "updateUserProfileApi";
}