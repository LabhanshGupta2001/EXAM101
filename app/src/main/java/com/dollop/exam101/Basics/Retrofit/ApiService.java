package com.dollop.exam101.Basics.Retrofit;


import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Anil on 9/4/2021.
 */
public interface ApiService {

// Nilesh..
    @GET(Const.getCountryListApi)
    Call<AllResponseModel> getCountryList();

    @GET(Const.getStateListApi)
    Call<AllResponseModel> getStateList(@Query(Constants.Key.countryUuId) String countryId);

    //EditProfile
    @GET(Const.getProfileDetailApi)
    Call<AllResponseModel> getEditProfileDetails(@Header(Constants.Key.Authorization) String token);

    @Multipart
    @POST(Const.updateProfileApi)
    Call<AllResponseModel> EditProfileImage(@Header(Constants.Key.Authorization) String token,
                                            @PartMap HashMap<String, RequestBody> hm,
                                            @Part MultipartBody.Part profilePic);

    @FormUrlEncoded
    @POST(Const.socialLoginApi)
    Call<AllResponseModel> SocialLogin(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.forgotPasswordApi)
    Call<AllResponseModel> ForgetPassword(@FieldMap HashMap<String, String> hm);

    @GET(Const.getPrivacyPolicyApi)
    Call<AllResponseModel> getPrivacyAndPolicy();

    @GET(Const.getTermConditionApi)
    Call<AllResponseModel> getTermAndCondition();

    @GET(Const.getPackageListWithFilterApi)
    Call<AllResponseModel> packageListItem(@Header(Constants.Key.Authorization) String token,
                                           @QueryMap HashMap<String, String> hm);

   /* @GET(Const.getPackageListWithFilterApi)
    Call<AllResponseModel> examPackageListItem(@Header(Constants.Key.Authorization) String token,
                                               @Query(Constants.Key.examId) String examId)*/;
    @GET(Const.getLanguageApi)
    Call<AllResponseModel> getLanguage(@Header(Constants.Key.Authorization) String token);


//______________________________********************___________________________________________//

    @GET(Const.aboutUs)
    Call<AllResponseModel> getAboutUs(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getBankProfile)
    Call<AllResponseModel> getBankProfile(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getBankUserDetails)
    Call<AllResponseModel> GetBankUserDetails(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getBlogDetailApi)
    Call<AllResponseModel> getBlogDetails(@Query(Constants.Key.uuid) String uuid);

    @FormUrlEncoded
    @POST(Const.getBankProfile)
    Call<AllResponseModel> UserBankDetails(@FieldMap HashMap<String, String> hm);

    //Blogs
    @GET(Const.getBlogCategoryListApi)
    Call<AllResponseModel> getBlogsCategory();

    @GET(Const.getBlogListApi)
    Call<AllResponseModel> getBlogsData(@Query(Constants.Key.uuid) String uuid);

    @GET(Const.getBlogsSortBy)
    Call<AllResponseModel> getBlogsSortBy(@Header(Constants.Key.Authorization) String token);

    @FormUrlEncoded
    @POST(Const.applyCouponCodeApi)
    Call<AllResponseModel> ApplyCouponCode(@Header(Constants.Key.Authorization) String token,
                                           @Field(Constants.Key.couponCode) String couponCode);


    @GET(Const.getBlogCategoryListApi)
    Call<AllResponseModel> getBlogsFilterBy();

    //CategoryDetails
    @GET(Const.getCategory)
    Call<AllResponseModel> getCategory(@Header(Constants.Key.Authorization) String token);

   /* @GET(Const.getCategoryDetails)
    Call<AllResponseModel> getCategoryDetails(@Header(Constants.Key.Authorization) String token);*/

    @GET(Const.getCategoryFilter)
    Call<AllResponseModel> getCategoryFilter(@Header(Constants.Key.Authorization) String token);


    @FormUrlEncoded
    @PUT(Const.changePasswordApi)
    Call<AllResponseModel> ChangePassword(@Header(Constants.Key.Authorization) String token,
                                          @FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.contactUsApi)
    Call<AllResponseModel> ContactUs(@FieldMap HashMap<String, String> hm);

    @GET(Const.getCourseList)
    Call<AllResponseModel> getCourseList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getCourseDetails)
    Call<AllResponseModel> getCourseDetails(@Header(Constants.Key.Authorization) String token);


    @GET(Const.getCourseMaterialProgressBar)
    Call<AllResponseModel> getCourseMaterialProgressBar(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getCourseMaterialList)
    Call<AllResponseModel> getCourseMaterialList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getMyPackagesList)
    Call<AllResponseModel> getMyPakagesList(@Header(Constants.Key.Authorization) String token);


    @GET(Const.getFaqsList)
    Call<AllResponseModel> getFaqsList(@Header(Constants.Key.Authorization) String token);


    //Packages Details
    @GET(Const.getPackageDetails)
    Call<AllResponseModel> getPakageDetails(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getPackageDetailsCourseMaterial)
    Call<AllResponseModel> getPackageDetailsCourseMaterial(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getPackageDetailsMockTestList)
    Call<AllResponseModel> getPackageDetailsMockTestList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getReviewRatingListApi)
    Call<AllResponseModel> getPackageDetailsMockTestListRatingNow(@Header(Constants.Key.Authorization) String token,
                                                                  @Query(Constants.Key.packageUuid) String packageUuid );//


    @GET(Const.AllResults)
    Call<AllResponseModel> AllResults(@Header(Constants.Key.Authorization) String token);

    @GET(Const.AuthorList)
    Call<AllResponseModel> AuthorList(@Header(Constants.Key.Authorization) String token);


    @GET(Const.CategoriesHomePhotographyList)
    Call<AllResponseModel> CategoriesHomePhotographyList(@Header(Constants.Key.Authorization) String token);


    //labhansh

    @FormUrlEncoded
    @POST(Const.registrationApi)
    Call<AllResponseModel> userSignup(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.loginApi)
    Call<AllResponseModel> userLogin(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.update_details)
    Call<AllResponseModel> updateBankDetails(@FieldMap HashMap<String, String> hm);

    @GET(Const.get_result)
    Call<AllResponseModel> getResult(@FieldMap HashMap<String, String> hm);

    @GET(Const.get_search_history)
    Call<AllResponseModel> getSearchHistory(@FieldMap HashMap<String, String> hm);

    @GET(Const.get_flex_box)
    Call<AllResponseModel> getFlexBox(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.reset_password)
    Call<AllResponseModel> resetPassword(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.raise_complaint)
    Call<AllResponseModel> raiseComplaint(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.otp_verification)
    Call<AllResponseModel> otpVerification(@FieldMap HashMap<String, String> hm);

    @GET(Const.get_complaint)
    Call<AllResponseModel> getComplaintList(@FieldMap HashMap<String, String> hm);

    @GET(Const.order_history)
    Call<AllResponseModel> getorderHistory(@FieldMap HashMap<String, String> hm);

    @GET(Const.login_history)
    Call<AllResponseModel> getloginHistory(@FieldMap HashMap<String, String> hm);

    @GET(Const.getCartDetailApi)
    Call<AllResponseModel> getCartList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.getPackageDetailApi)
    Call<AllResponseModel> getPackageDetailApi(@Header(Constants.Key.Authorization) String token,
                                               @Query(Constants.Key.packageUuid) String packageUuid);

    @FormUrlEncoded
    @POST(Const.addToWishListApi)
    Call<AllResponseModel> addWishlist(@Header(Constants.Key.Authorization) String token,
                                       @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.addToCartApi)
    Call<AllResponseModel> addCart(@Header(Constants.Key.Authorization) String token,
                                       @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.addReviewRatingApi)
    Call<AllResponseModel> addRatingReview(@Header(Constants.Key.Authorization) String token,
                                       @FieldMap HashMap<String, String> hm);

    @GET(Const.notification)
    Call<AllResponseModel> getNotification(@FieldMap HashMap<String, String> hm);

    @GET(Const.filter)
    Call<AllResponseModel> getFilter(@FieldMap HashMap<String, String> hm);

    @GET(Const.get_banner)
    Call<AllResponseModel> getBanner(@FieldMap HashMap<String, String> hm);


    @GET(Const.user)
    Call<AllResponseModel> getUser(@FieldMap HashMap<String, String> hm);

    @GET(Const.mock_test)
    Call<AllResponseModel> getMockTest(@FieldMap HashMap<String, String> hm);

    @GET(Const.package_list)
    Call<AllResponseModel> packageList(@FieldMap HashMap<String, String> hm);

    @GET(Const.purchase)
    Call<AllResponseModel> getPurchaseList(@FieldMap HashMap<String, String> hm);

    @GET(Const.transaction)
    Call<AllResponseModel> getTransactionHistory(@FieldMap HashMap<String, String> hm);



    //Dashboard

    @GET(Const.getExamListApi)
    Call<AllResponseModel> Examlist(@Header(Constants.Key.Authorization) String token);

}