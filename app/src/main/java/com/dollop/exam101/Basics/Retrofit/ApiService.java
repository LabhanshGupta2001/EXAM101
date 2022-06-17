package com.dollop.exam101.Basics.Retrofit;

import com.dollop.exam101.Basics.UtilityTools.Constants;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Anil on 9/4/2021.
 */
public interface ApiService {



    @FormUrlEncoded
    @POST(Const.user_signup)
    Call<Response> userSignup(@FieldMap HashMap<String, String> hm);



}
