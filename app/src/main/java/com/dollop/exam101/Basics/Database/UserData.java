package com.dollop.exam101.Basics.Database;

import android.database.sqlite.SQLiteDatabase;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    public static final String TABLE_NAME = "examoneOone";
    //    All Key
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KeyUserName = "userName";
    public static final String KeyUserEmail = "userEmail";
    public static final String KeyUserMobile = "userPhone";
    public static final String Key_Token = "token";
    public static final String KEY_USER_Profile_PIC = "Profile_pic";
    public static final String KEY_USER_device_type = "deviceType";
    public static final String KEY_USER_fcm_id = "fcm_id";
    public static final String KEY_selfReferralCode = "selfReferralCode";
    public static final String KEY_registerReferralCode = "registerReferralCode";
    public static final String KEY_primeReferral = "primeReferral";
    public static final String KEY_primePlusReferral = "primePlusReferral";
    public static final String KEY_wallet = "wallet";
    public static final String KEY_cashback = "cashback";
    public static final String KEY_premiumBalance = "premiumBalance";
    public static final String KEY_referralCommission = "referralCommission";
    public static final String KEY_roleType = "roleType";
    public static final String KEY_userType = "userType";
    public static final String KEY_userTypeReferral = "user_type_referal";
    public static final String KEY_userCode = "userCode";
    public static final String KEY_userPin = "userPin";
    public static final String KEY_created_at = "created_at";


    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("user_type_referal")
    @Expose
    public String user_type_referal;
    @SerializedName("role_type")
    @Expose
    public String roleType;
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("user_code")
    @Expose
    public String userCode;
    @SerializedName("user_pin")
    @Expose
    public String userPin;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("profile_pic")
    @Expose
    public String profilePic;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("self_referral_code")
    @Expose
    public String selfReferralCode;
    @SerializedName("register_referral_code")
    @Expose
    public String registerReferralCode;
    @SerializedName("prime_referral")
    @Expose
    public String primeReferral;
    @SerializedName("prime_plus_referral")
    @Expose
    public String primePlusReferral;
    @SerializedName("wallet")
    @Expose
    public String wallet;
    @SerializedName("cashback")
    @Expose
    public String cashback;
    @SerializedName("premium_balance")
    @Expose
    public String premiumBalance;
    @SerializedName("referral_commission")
    @Expose
    public String referralCommission;
    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("profile_pic_referal")
    @Expose
    public String profilePicReferral;

    @SerializedName("user_code_referal")
    @Expose
    public String userCodeReferral;

    @SerializedName("name_referal")
    @Expose
    public String nameReferral;

    @SerializedName("mobile_referal")
    @Expose
    public String mobileReferral;

    public String fcmId;
    public String deviceType;


    /**
     * Create Table Query
     *
     * @param db SQLiteDatabase
     */
    public static void CreateTable(SQLiteDatabase db) {
        String CreateTableQuery = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID + " text," +
                Key_Token + " text," +
                KeyUserName + " text," +
                KeyUserEmail + " text," +
                KeyUserMobile + " text, " +
                KEY_USER_device_type + " text, " +
                KEY_USER_fcm_id + " text, " +
                KEY_selfReferralCode + " text, " +
                KEY_registerReferralCode + " text, " +
                KEY_primeReferral + " text, " +
                KEY_primePlusReferral + " text, " +
                KEY_wallet + " text, " +
                KEY_cashback + " text, " +
                KEY_premiumBalance + " text, " +
                KEY_referralCommission + " text, " +
                KEY_roleType + " text, " +
                KEY_userType + " text, " +
                KEY_userCode + " text, " +
                KEY_userPin + " text, " +
                KEY_created_at + " text, " +
                KEY_USER_Profile_PIC + " text " +
                ")";
        Utils.E("CreateTableQuery::" + CreateTableQuery);
        db.execSQL(CreateTableQuery);
    }

    /**
     * @param db SQLiteDatabase
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}