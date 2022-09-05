package com.dollop.exam101.Basics.Retrofit;


import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.main.model.AllResponseModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Anil on 9/4/2021.
 */
public interface ApiService {

    // Nilesh..Patidar
    @GET(Const.Url.getCountryListApi)
    Call<AllResponseModel> getCountryList();

    @GET(Const.Url.getStateListApi)
    Call<AllResponseModel> getStateList(@Query(Constants.Key.countryUuId) String countryId);

    //EditProfile
    @GET(Const.Url.getProfileDetailApi)
    Call<AllResponseModel> getEditProfileDetails(@Header(Constants.Key.Authorization) String token);

    @Multipart
    @POST(Const.Url.updateProfileApi)
    Call<AllResponseModel> EditProfileImage(@Header(Constants.Key.Authorization) String token,
                                            @PartMap HashMap<String, RequestBody> hm,
                                            @Part MultipartBody.Part profilePic);

    @FormUrlEncoded
    @POST(Const.Url.socialLoginApi)
    Call<AllResponseModel> SocialLogin(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.forgotPasswordApi)
    Call<AllResponseModel> ForgetPassword(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.getPrivacyPolicyApi)
    Call<AllResponseModel> getPrivacyAndPolicy();

    @GET(Const.Url.getTermConditionApi)
    Call<AllResponseModel> getTermAndCondition();

    @GET(Const.Url.getPackageListWithFilterApi)
    Call<AllResponseModel> packageListItem(@Header(Constants.Key.Authorization) String token,
                                           @QueryMap HashMap<String, String> hm);

    @GET(Const.Url.getMockTestHistoryListApi)
    Call<AllResponseModel> getMockTestHistoryListApi(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getLanguageApi)
    Call<AllResponseModel> getLanguage(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.getStudentWishListApi)
    Call<AllResponseModel> getWishList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getTopicDetailApi)
    Call<AllResponseModel> getTopicDetails(@Header(Constants.Key.Authorization) String token,
                                           @QueryMap HashMap<String, String> hm);

    @GET(Const.Url.getPracticeQuestionListApi)
    Call<AllResponseModel> getMyQuestionList(@Header(Constants.Key.Authorization) String token,
                                             @QueryMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.submitPracticeTestApi)
    Call<AllResponseModel> practiceTestSubmit(@Header(Constants.Key.Authorization) String token,
                                              @FieldMap HashMap<String, String> hm);

    @Multipart
    @POST(Const.Url.raiseComplaintApi)
    Call<AllResponseModel> addRaiseComplaint(@Header(Constants.Key.Authorization) String token,
                                             @PartMap HashMap<String, RequestBody> hm,
                                             @Part MultipartBody.Part attachment);

    @GET(Const.Url.getRaisedComplaintListApi)
    Call<AllResponseModel> getComplaintList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getBankDetailApi)
    Call<AllResponseModel> getBankDetails(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.getAffiliateStatusApi)
    Call<AllResponseModel> getAffiliateStatus(@Header(Constants.Key.Authorization) String token);

    @FormUrlEncoded
    @POST(Const.Url.sendAffiliateRequestApi)
    Call<AllResponseModel> sendAffiliateBankDetails(@Header(Constants.Key.Authorization) String token,
                                                    @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.submitMockTestApi)
    Call<AllResponseModel> submitMockTest(@Header(Constants.Key.Authorization) String token,
                                                    @FieldMap HashMap<String, String> hm);


    @GET(Const.Url.getMockTestQuestionListApi)
    Call<AllResponseModel> getMockTestQuestionList(@Header(Constants.Key.Authorization) String token,
                                                   @QueryMap HashMap<String, String> hm);

    @GET(Const.Url.getTestResultApi)
    Call<AllResponseModel> getTestResult(@Header(Constants.Key.Authorization) String token,
                                                   @QueryMap HashMap<String, String> hm);

    @GET(Const.Url.getAffiliatePurchaseListApi)
    Call<AllResponseModel> getAffiliatePurchaseList(@Header(Constants.Key.Authorization) String token,
                                                    @QueryMap HashMap<String, String> hm);

    @GET(Const.Url.getAffiliatePurchaseSummaryApi)
    Call<AllResponseModel> getAffiliatePurchaseSummary(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.getStudentMockTestListApi)
    Call<AllResponseModel> getStudentMockTestListApi(@Header(Constants.Key.Authorization) String token);

   /* @GET(Const.Url.getRaisedComplaintDetailApi)
    Call<AllResponseModel> getComplaintDetail(@Header(Constants.Key.Authorization) String token,
                                              @FieldMap HashMap<String, String> hm);*/

//______________________________********************___________________________________________//

    @GET(Const.Url.aboutUs)
    Call<AllResponseModel> getAboutUs(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getBankProfile)
    Call<AllResponseModel> getBankProfile(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getBankUserDetails)
    Call<AllResponseModel> GetBankUserDetails(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getBlogDetailApi)
    Call<AllResponseModel> getBlogDetails(@Query(Constants.Key.uuid) String uuid);

    @FormUrlEncoded
    @POST(Const.Url.getBankProfile)
    Call<AllResponseModel> UserBankDetails(@FieldMap HashMap<String, String> hm);

    //Blogs
    @GET(Const.Url.getBlogCategoryListApi)
    Call<AllResponseModel> getBlogsCategory();

    @GET(Const.Url.getBlogListApi)
    Call<AllResponseModel> getBlogsData(@Query(Constants.Key.uuid) String uuid);

    @GET(Const.Url.getBlogsSortBy)
    Call<AllResponseModel> getBlogsSortBy(@Header(Constants.Key.Authorization) String token);

    @FormUrlEncoded
    @POST(Const.Url.applyCouponCodeApi)
    Call<AllResponseModel> ApplyCouponCode(@Header(Constants.Key.Authorization) String token,
                                           @Field(Constants.Key.couponCode) String couponCode);


    @GET(Const.Url.getBlogCategoryListApi)
    Call<AllResponseModel> getBlogsFilterBy();

    //CategoryDetails
    @GET(Const.Url.getCategory)
    Call<AllResponseModel> getCategory(@Header(Constants.Key.Authorization) String token);

   /* @GET(Const.Url.getCategoryDetails)
    Call<AllResponseModel> getCategoryDetails(@Header(Constants.Key.Authorization) String token);*/

    @GET(Const.Url.getCategoryFilter)
    Call<AllResponseModel> getCategoryFilter(@Header(Constants.Key.Authorization) String token);


    @FormUrlEncoded
    @PUT(Const.Url.changePasswordApi)
    Call<AllResponseModel> ChangePassword(@Header(Constants.Key.Authorization) String token,
                                          @FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.Url.contactUsApi)
    Call<AllResponseModel> ContactUs(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.getCourseList)
    Call<AllResponseModel> getCourseList(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.getCourseMaterialProgressBar)
    Call<AllResponseModel> getCourseMaterialProgressBar(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getCourseMaterialList)
    Call<AllResponseModel> getCourseMaterialList(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.getFaqsList)
    Call<AllResponseModel> getFaqsList(@Header(Constants.Key.Authorization) String token);


    //Packages Details
    @GET(Const.Url.getPackageDetails)
    Call<AllResponseModel> getPakageDetails(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getPackageDetailsCourseMaterial)
    Call<AllResponseModel> getPackageDetailsCourseMaterial(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getPackageDetailsMockTestList)
    Call<AllResponseModel> getPackageDetailsMockTestList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getReviewRatingListApi)
    Call<AllResponseModel> getPackageDetailsMockTestListRatingNow(@Header(Constants.Key.Authorization) String token,
                                                                  @Query(Constants.Key.packageUuId) String packageUuid);//


    @GET(Const.Url.AllResults)
    Call<AllResponseModel> AllResults(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.AuthorList)
    Call<AllResponseModel> AuthorList(@Header(Constants.Key.Authorization) String token);


    @GET(Const.Url.CategoriesHomePhotographyList)
    Call<AllResponseModel> CategoriesHomePhotographyList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.downloadInvoiceApi)
    Call<AllResponseModel> downloadInvoice(@Header(Constants.Key.Authorization) String token);

    //labhansh

    @FormUrlEncoded
    @POST(Const.Url.registrationApi)
    Call<AllResponseModel> userSignup(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.Url.loginApi)
    Call<AllResponseModel> userLogin(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.get_result)
    Call<AllResponseModel> getResult(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.get_search_history)
    Call<AllResponseModel> getSearchHistory(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.get_flex_box)
    Call<AllResponseModel> getFlexBox(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @PUT(Const.Url.resetPasswordApi)
    Call<AllResponseModel> resetPassword(@Header(Constants.Key.Authorization) String token,
                                         @FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Const.Url.raise_complaint)
    Call<AllResponseModel> raiseComplaint(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.otp_verification)
    Call<AllResponseModel> otpVerification(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.order_history)
    Call<AllResponseModel> getorderHistory(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.getLoginHistoryApi)
    Call<AllResponseModel> getLoginHistory(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getCartDetailApi)
    Call<AllResponseModel> getCartList(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getPackageDetailApi)
    Call<AllResponseModel> getPackageDetailApi(@Header(Constants.Key.Authorization) String token,
                                               @Query(Constants.Key.packageUuId) String packageUuid);

    @FormUrlEncoded
    @POST(Const.Url.addToWishListApi)
    Call<AllResponseModel> addWishlist(@Header(Constants.Key.Authorization) String token,
                                       @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.addToCartApi)
    Call<AllResponseModel> addCart(@Header(Constants.Key.Authorization) String token,
                                   @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Const.Url.addReviewRatingApi)
    Call<AllResponseModel> addRatingReview(@Header(Constants.Key.Authorization) String token,
                                           @FieldMap HashMap<String, String> hm);

    @GET(Const.Url.notification)
    Call<AllResponseModel> getNotification(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.filter)
    Call<AllResponseModel> getFilter(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.get_banner)
    Call<AllResponseModel> getBanner(@FieldMap HashMap<String, String> hm);


    @GET(Const.Url.user)
    Call<AllResponseModel> getUser(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.mock_test)
    Call<AllResponseModel> getMockTest(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.package_list)
    Call<AllResponseModel> packageList(@FieldMap HashMap<String, String> hm);

    @GET(Const.Url.purchase)
    Call<AllResponseModel> getPurchaseList(@FieldMap HashMap<String, String> hm);


    //Dashboard

    @GET(Const.Url.getExamListApi)
    Call<AllResponseModel> Examlist(@Header(Constants.Key.Authorization) String token);

    @GET(Const.Url.getStudentExamListApi)
    Call<AllResponseModel> getStudentExamListApi(@Header(Constants.Key.Authorization) String token,
                                                 @Query(Constants.Key.device_type) String device_type);


    @GET(Const.Url.removeFromCartApi)
    Call<AllResponseModel> removeFromCart(@Header(Constants.Key.Authorization) String token, @Query(Constants.Key.cartUuId) String cartUuId);

    @FormUrlEncoded
    @POST(Const.Url.purchasePackageApi)
    Call<AllResponseModel> purchasePackage(@Header(Constants.Key.Authorization) String token,
                                           @FieldMap HashMap<String, String> hashMap);


    @GET(Const.Url.getStudentExamDetailApi)
    Call<AllResponseModel> getStudentExamDetailApi(@Header(Constants.Key.Authorization) String token,
                                                   @QueryMap HashMap<String, String> hashMap);


    @DELETE(Const.Url.removeFromWishListApi + "/{" + Constants.Key.wishListUuid + "}")
    Call<AllResponseModel> removeFromWishList(@Header(Constants.Key.Authorization) String token,
                                              @Path(Constants.Key.wishListUuid) String wishListUuid);

    @FormUrlEncoded
    @PUT(Const.Url.updateBankDetailApi)
    Call<AllResponseModel> updateBankDetail(@Header(Constants.Key.Authorization) String token,
                                            @FieldMap HashMap<String, String> hashMap);

    @GET(Const.Url.getStudentOrderListApi)
    Call<AllResponseModel> getStudentOrderListApi(@Header(Constants.Key.Authorization) String token);

}