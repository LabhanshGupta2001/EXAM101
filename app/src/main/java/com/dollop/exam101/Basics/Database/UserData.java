package com.dollop.exam101.Basics.Database;

import android.database.sqlite.SQLiteDatabase;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    public static final String TABLE_NAME = "examoneOone";
    //    All Key
    public static final String KEY_ID = "_id";
    public static final String KEY_StudentId = "studentId";
    public static final String Key_StudentName = "studentName";
    public static final String Key_StudentEmail = "studentEmail";
    public static final String Key_StudentMobileNo = "studentMobileNo";
    public static final String Key_CountryCode = "countryCode";
    public static final String Key_CountryName = "countryName";
    public static final String Key_StateName = "stateName";
    public static final String Key_FcmId = "fcmId";
    public static final String Key_MobileVerified= "mobileVerified";
    public static final String Key_EmailVerified = "emailVerified";
    public static final String Key_RoleType = "roleType";
    public static final String KEY_userPin = "userPin";
    public static final String KEY_profilePic = "profilePic";
    public static final String KEY_CountryUuid = "countryUuid";
    public static final String KEY_Token = "token";
    public static final String KEY_isPasswordGenerated = "isPasswordGenerated";


    @SerializedName("user_id")
    @Expose
    public String userId;

    @SerializedName("studentId")
    @Expose
    public String studentId;
    @SerializedName("studentName")
    @Expose
    public String studentName;
    @SerializedName("studentEmail")
    @Expose
    public String studentEmail;
    @SerializedName("studentMobileNo")
    @Expose
    public String studentMobileNo;
    @SerializedName("countryCode")
    @Expose
    public String countryCode;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("fcmId")
    @Expose
    public String fcmId;
    @SerializedName("mobileVerified")
    @Expose
    public String mobileVerified;
    @SerializedName("emailVerified")
    @Expose
    public String emailVerified;
    @SerializedName("roleType")
    @Expose
    public String roleType;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("countryUuid")
    @Expose
    public String countryUuid;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("isPasswordGenerated")
    @Expose
    public String isPasswordGenerated;


    public static void CreateTable(SQLiteDatabase db) {
        String CreateTableQuery = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_StudentId + " text," +
                Key_StudentName + " text," +
                Key_StudentEmail + " text," +
                Key_StudentMobileNo + " text," +
                Key_CountryCode + " text," +
                Key_CountryName + " text," +
                Key_StateName + " text," +
                Key_FcmId + " text," +
                Key_MobileVerified + " text," +
                Key_EmailVerified + " text," +
                Key_RoleType + " text," +
                KEY_profilePic + " text," +
                KEY_CountryUuid + " text," +
                KEY_Token + " text,"+
                KEY_isPasswordGenerated + " text"+
                " )" ;
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