package com.dollop.exam101.main.model;

import com.dollop.exam101.Basics.Database.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllResponseModel {
    @SerializedName("studentexam")
    @Expose
    public List<Studentexam> studentexam = null;
    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("testAttemptUuid")
    @Expose
    public String testAttemptUuid;

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


    @SerializedName("Orderexams")
    @Expose
    public Orderexams orderexams;

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



    @SerializedName("complaintList")
    @Expose
    public List<ComplaintModel> complaintList = null;

    @SerializedName("studentOrders")
    @Expose
    public List<StudentOrder> studentOrders = null;
    @SerializedName("loginHistoryData")
    @Expose
    public List<LoginHistoryDatum> loginHistoryData = null;

    @SerializedName("bankDetails")
    @Expose
    public BankDetailsModel bankDetailsModel;

    @SerializedName("affilliatDetail")
    @Expose
    public AffilliatDetailModel affilliatDetailModel;

    @SerializedName("studentMockTests")
    @Expose
    public List<StudentMockTest> studentMockTestsList = null;

    @SerializedName("appliedCoupon")
    @Expose
    public AppliedCouponModel appliedCouponModel;

    @SerializedName("affilliatPurchaseList")
    @Expose
    public AffilliatPurchaseListModel affilliatPurchaseListModel;

    @SerializedName("affilliatPurchaseSummary")
    @Expose
    public AffilliatPurchaseSummaryModel affilliatPurchaseSummaryModel;

    @SerializedName("mockTestHistory")
    @Expose
    public List<MockTestHistory> mockTestHistory = null;

    @SerializedName("mockTestQuestion")
    @Expose
    public MockTestQuestion mockTestQuestion;

    @SerializedName("totalQuestionCnt")
    @Expose
    public Integer totalQuestionCnt;

    @SerializedName("correntQuestionCnt")
    @Expose
    public Integer correntQuestionCnt;

    @SerializedName("questions")
    @Expose
    public List<TestResultQuestion> testResultQuestions = null;
    @SerializedName("bannerList")
    @Expose
    public List<BannerModel> bannerList = null;
    @SerializedName("bottom")
    @Expose
    public Bottom bottom;
    @SerializedName("middleObj1")
    @Expose
    public MiddleObj1 middleObj1;
    @SerializedName("middleObj2")
    @Expose
    public MiddleObj2 middleObj2;



    @SerializedName("bannerList")
    @Expose
    public List<BannerModel> bannerModelList = null;
}