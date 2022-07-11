package com.dollop.exam101.Basics.Retrofit;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.main.model.AllResponseModel;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Anil on 9/4/2021.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST(Const.user_signup)
    Call<AllResponseModel> userSignup(@FieldMap HashMap<String, String> hm);

    @GET(Const.aboutUs)
    Call<AllResponseModel> getAboutUs(@Header(Constants.Authorization) String token);

    @GET(Const.getBankProfile)
    Call<AllResponseModel> getBankProfile(@Header(Constants.Authorization) String token);

    @GET(Const.getBankUserDetails)
    Call<AllResponseModel> GetBankUserDetails(@Header(Constants.Authorization) String token);

    @GET(Const.getBlogDetails)
    Call<AllResponseModel> getBlogDetails(@Header(Constants.Authorization) String token);

    @FormUrlEncoded
    @POST(Const.getBankProfile)
    Call<AllResponseModel> UserBankDetails(@Field(Constants.message) String mobile);

    //Blogs
    @GET(Const.getBlogsCategory)
    Call<AllResponseModel> getBlogsCategory(@Header(Constants.Authorization) String token);

    @GET(Const.getBlogsData)
    Call<AllResponseModel> getBlogsData(@Header(Constants.Authorization) String token);

    @GET(Const.getBlogsSortBy)
    Call<AllResponseModel> getBlogsSortBy(@Header(Constants.Authorization) String token);

    @GET(Const.getBlogsFilterBy)
    Call<AllResponseModel> getBlogsFilterBy(@Header(Constants.Authorization) String token);

    //CategoryDetails
    @GET(Const.getCategory)
    Call<AllResponseModel> getCategory(@Header(Constants.Authorization) String token);

    @GET(Const.getCategoryDetails)
    Call<AllResponseModel> getCategoryDetails(@Header(Constants.Authorization) String token);

    @GET(Const.getCategoryFilter)
    Call<AllResponseModel> getCategoryFilter(@Header(Constants.Authorization) String token);


    @FormUrlEncoded
    @PUT(Const.ChangePassword)
    Call<AllResponseModel> ChangePassword(@Header(Constants.Authorization) String token);


    @FormUrlEncoded
    @POST(Const.ContactUs)
    Call<AllResponseModel> ContactUs(@Field(Constants.Authorization) String token);

    @GET(Const.getCourseList)
    Call<AllResponseModel> getCourseList(@Header(Constants.Authorization) String token);

    @GET(Const.getCourseDetails)
    Call<AllResponseModel> getCourseDetails(@Header(Constants.Authorization) String token);


    @GET(Const.getCourseMaterialProgressBar)
    Call<AllResponseModel> getCourseMaterialProgressBar(@Header(Constants.Authorization) String token);

    @GET(Const.getCourseMaterialList)
    Call<AllResponseModel> getCourseMaterialList(@Header(Constants.Authorization) String token);

    @GET(Const.getMyPackagesList)
    Call<AllResponseModel> getMyPakagesList(@Header(Constants.Authorization) String token);


    @GET(Const.getFaqsList)
    Call<AllResponseModel> getFaqsList(@Header(Constants.Authorization) String token);

    //EditProfile
    @GET(Const.getEditProfileDetails)
    Call<AllResponseModel> getEditProfileDetails(@Header(Constants.Authorization) String token);

    @FormUrlEncoded
    @POST(Const.EditProfileImage)
    Call<AllResponseModel> EditProfileImage(@Field(Constants.IMAGE_JPEG) String token);


    @FormUrlEncoded
    @POST(Const.ForgetPassword)
    Call<AllResponseModel> ForgetPassword(@Field(Constants.IMAGE_JPEG) String token);

    //Packages Details
    @GET(Const.getPackageDetails)
    Call<AllResponseModel> getPakageDetails(@Header(Constants.Authorization) String token);

    @GET(Const.getPackageDetailsCourseMaterial)
    Call<AllResponseModel> getPackageDetailsCourseMaterial(@Header(Constants.Authorization) String token);

    @GET(Const.getPackageDetailsMockTestList)
    Call<AllResponseModel> getPackageDetailsMockTestList(@Header(Constants.Authorization) String token);

    @GET(Const.getPackageDetailsMockTestListRatingNow)
    Call<AllResponseModel> getPackageDetailsMockTestListRatingNow(@Header(Constants.Authorization) String token);


    @GET(Const.AllResults)
    Call<AllResponseModel> AllResults(@Header(Constants.Authorization) String token);

    @GET(Const.AuthorList)
    Call<AllResponseModel> AuthorList(@Header(Constants.Authorization) String token);


    @GET(Const.CategoriesList)
    Call<AllResponseModel> CategoriesList(@Header(Constants.Authorization) String token);


    @GET(Const.CategoriesAllBlogsList)
    Call<AllResponseModel> CategoriesAllBlogsList(@Header(Constants.Authorization) String token);


    @GET(Const.CategoriesHomePhotographyList)
    Call<AllResponseModel> CategoriesHomePhotographyList(@Header(Constants.Authorization) String token);

}