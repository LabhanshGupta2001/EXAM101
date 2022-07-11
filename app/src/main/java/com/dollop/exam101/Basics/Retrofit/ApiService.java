package com.dollop.exam101.Basics.Retrofit;

import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Anil on 9/4/2021.
 */
public interface ApiService {


    @FormUrlEncoded
    @POST(Const.user_signup)
    Call<AllResponseModel> userSignup(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.user_login)
    Call<AllResponseModel> userLogin(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.update_details)
    Call<AllResponseModel> updateBankDetails(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.get_result)
    Call<AllResponseModel> getResult(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.get_search_history)
    Call<AllResponseModel> getSearchHistory(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.get_flex_box)
    Call<AllResponseModel> getFlexBox(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.reset_password)
    Call<AllResponseModel> resetPassword(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.lang)
    Call<AllResponseModel> setLanguage(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.rapse_complaint)
    Call<AllResponseModel> raiseComplaint(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.otp_verification)
    Call<AllResponseModel> otpVerification(@Field("otp") String otp);

    @FormUrlEncoded
    @GET(Const.get_complaint)
    Call<AllResponseModel> getComplaintList(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.order_history)
    Call<AllResponseModel> getorderHistory(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.login_history)
    Call<AllResponseModel> getloginHistory(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.get_cart)
    Call<AllResponseModel> getCartList(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.wish_list)
    Call<AllResponseModel> getWishList(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.notification)
    Call<AllResponseModel> getNotification(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.filter)
    Call<AllResponseModel> getFilter(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.get_banner)
    Call<AllResponseModel> getBanner(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.top_ten)
    Call<AllResponseModel> getTopTen(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.user)
    Call<AllResponseModel> getUser(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.mock_test)
    Call<AllResponseModel> getMockTest(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.package_list)
    Call<AllResponseModel> packageList(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.purchase)
    Call<AllResponseModel> getPurchaseList(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @GET(Const.transaction)
    Call<AllResponseModel> getTransactionHistory(@FieldMap HashMap<String, String> hm);

}
