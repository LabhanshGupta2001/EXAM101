package com.dollop.exam101.main.model;

import com.dollop.exam101.Basics.Database.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllResponseModel {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("is_notification")
    @Expose
    public String isNotification;

    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("OTP")
    @Expose
    public String otp;

    @SerializedName("country")
    @Expose
    public List<CountryModel> country = null;

    @SerializedName("state")
    @Expose
    public List<StateModel> state = null;

    @SerializedName("userData")
    @Expose
    public UserData userData;

    @SerializedName("User")
    @Expose
    public UserData User;

    @SerializedName("studentData")
    @Expose
    public UserData studentData;

    @SerializedName("blogsCat")
    @Expose
    public List<BlogListHeadingModel> blogsCat = null;

    @SerializedName("blogs")
    @Expose
    public List<AllBlogListModel> blogs = null;

    @SerializedName("blog")
    @Expose
    public AllBlogListModel blog;

    @SerializedName("privacy")
    @Expose
    public PrivacyModel privacyModel;

    @SerializedName("term")
    @Expose
    public Term term;

    @SerializedName("packages")
    @Expose
    public List<PackageModel> packageModels = null;

    @SerializedName("exams")
    @Expose
    public List<CourseModel> examListModels = null;

    @SerializedName("reviewRating")
    @Expose
    public List<ReviewRating> reviewRating = null;


    @SerializedName("languages")
    @Expose
    public List<LanguageModel> languageModels = null;

    @SerializedName("packageDetail")
    @Expose
    public PackageDetailModel packageDetail;

    @SerializedName("cartData")
    @Expose
    public List<CartDatumModel> cartData = null;

    @SerializedName("subTotalAmt")
    @Expose
    public String subTotalAmt;

    @SerializedName("gstPercentage")
    @Expose
    public String gstPercentage;

    @SerializedName("grandTotalAmt")
    @Expose
    public Double grandTotalAmt;

    @SerializedName("wishList")
    @Expose
    public List<WishListModel> wishList = null;

    @SerializedName("topicDetail")
    @Expose
    public TopicDetailModel topicDetailModel;

    @SerializedName("QuestionList")
    @Expose
    public QuestionListModel questionListModel;

}