package com.dollop.exam101.Basics.Retrofit;


import com.dollop.exam101.Basics.UtilityTools.Constants;


public class Const {

    public interface Url {

        //Environment  **NOTE**  Change "Live" With "Debug"  When Going Live
        String Development = Constants.Key.Debug;
        //Live
        //  String HOST_URL = "http://examoneOoneweb.dollopinfotech.com/";
        // String HOST_URL = "http://exam101.dollopinfotech.com/";
        String HOST_URL = "http://exam101stg.dollopinfotech.com/";


        //Versioning URL
        String MAIN_HOST_URL = HOST_URL + "v12/";


        String get_otp = "get_otp";
        String match_otp = "match_otp";
        String social_login = "social_login";
        String registrationApi = "registrationApi";
        String loginApi = "loginApi";
        String update_details = "update_details";
        String get_search_history = "get_search_history";
        String get_flex_box = "get_flex_box";
        String get_result = "get_result";
        String get_complaint = "get_complaint";
        String reset_password = "reset_password";
        String otp_verification = "otp_verification";
        String raise_complaint = "raise_complaint";
        String login_history = "login_history";
        String getCartDetailApi = "getCartDetailApi";
        String getPackageDetailApi = "getPackageDetailApi";
        String addToWishListApi = "addToWishListApi";
        String addToCartApi = "addToCartApi";
        String addReviewRatingApi = "addReviewRatingApi";
        String order_history = "order_history";
        String notification = "notification";
        String filter = "filter";
        String getPackageListWithFilterApi = "getPackageListWithFilterApi";
        String user = "user";
        String getLanguageApi = "getLanguageApi";
        String mock_test = "mock_test";
        String package_list = "package_list";
        String get_banner = "get_banner";
        String purchase = "purchase";
        String transaction = "transaction";
        String getUserProfileApi = "getUserProfileApi";
        String getCountryListApi = "getCountryListApi";
        String getStateListApi = "getStateListApi";
        String aboutUs = "aboutUs";
        String getBankProfile = "getBankProfile";
        String getBankUserDetails = "getBankUserDetails";
        String getBlogDetailApi = "getBlogDetailApi";
        String getPrivacyPolicyApi = "getPrivacyPolicyApi";
        String getBlogCategoryListApi = "getBlogCategoryListApi";
        String getBlogListApi = "getBlogListApi";
        String getBlogsSortBy = "getBlogsSortBy";
        String getCategory = "getCategory";
        String getCategoryDetails = "getCategoryDetails";
        String getCategoryFilter = "getCategoryFilter";
        String changePasswordApi = "changePasswordApi";
        String contactUsApi = "contactUsApi";
        String getCourseList = "getCourseList";
        String getCourseDetails = "getCourseDetails";
        String getCourseMaterialProgressBar = "getCourseMaterialProgressBar";
        String getCourseMaterialList = "getCourseMaterialList";
        String getMyPackagesList = "getMyPakagesList";
        String getFaqsList = "getFaqsList";
        String getProfileDetailApi = "getProfileDetailApi";
        String updateProfileApi = "updateProfileApi";
        String socialLoginApi = "socialLoginApi";
        String getStudentWishListApi = "getStudentWishListApi";

        String forgotPasswordApi = "forgotPasswordApi";
        String getPackageDetails = "getPakageDetails";
        String getPackageDetailsCourseMaterial = "getPackageDetailsCourseMaterial";
        String getPackageDetailsMockTestList = "getPackageDetailsMockTestList";
        String getReviewRatingListApi = "getReviewRatingListApi";
        String AllResults = "AllResults";
        String AuthorList = "AuthorList";
        String getExamListApi = "getExamListApi";
        String CategoriesAllBlogsList = "CategoriesAllBlogsList";
        String CategoriesHomePhotographyList = "CategoriesHomePhotographyList";
        String getTermConditionApi = "getTermConditionApi";
        String applyCouponCodeApi = "applyCouponCodeApi";
        String removeFromCartApi = "removeFromCartApi";
        String purchasePackageApi = "purchasePackageApi";
        String removeFromWishListApi = "removeFromWishListApi";
    }
}