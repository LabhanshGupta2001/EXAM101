package com.dollop.exam101.Basics.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dollop.exam101.Basics.UtilityTools.Utils;

import java.util.ArrayList;

/**
 * Created by Anil on 9/4/2021.
 */

public class UserDataHelper {
    @SuppressLint("StaticFieldLeak")
    private static UserDataHelper instance;
    Context cx;
    private SQLiteDatabase db;
    private final DataManager dm;

    /**
     * UserDataHelper constructor
     *
     * @param cx //
     */
    public UserDataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    /**
     * UserDataHelper instance
     *
     * @return //
     */
    public static UserDataHelper getInstance() {
        return instance;
    }

    /**
     * open db
     */
    public void open() {
        db = dm.getWritableDatabase();
    }

    /**
     * close db
     */
    public void close() {
        //  db.close();
    }

    /**
     * read db
     */
    public void read() {
        db = dm.getReadableDatabase();
    }

    /**
     * delete by user id from the table
     *
     * @param userData //
     */
    public void delete(UserData userData) {
        open();
        db.delete(UserData.TABLE_NAME, UserData.KEY_USER_ID + " = '"
                + userData.userId + "'", null);
        close();
    }

    /**
     * delete All Data From the Table
     */
    public void deleteAll() {
        open();
        db.delete(UserData.TABLE_NAME, null, null);
        close();
    }

    /**
     * is Exist in table
     *
     * @param userData //
     * @return //
     */
    private boolean isExist(UserData userData) {
        read();
        @SuppressLint("Recycle") Cursor cur = db.rawQuery("select * from " + UserData.TABLE_NAME + " where " + UserData.KEY_USER_ID + "='"
                + userData.userId + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }

    /**
     * insert Data in table
     *
     * @param userData //
     */
    public void insertData(UserData userData) {
        open();
        ContentValues values = new ContentValues();
        values.put(UserData.KEY_USER_ID, userData.userId);
        values.put(UserData.KeyUserName, userData.name);
        values.put(UserData.KeyUserEmail, userData.email);
        values.put(UserData.KeyUserMobile, userData.mobile);
        values.put(UserData.KEY_USER_Profile_PIC, userData.profilePic);
        values.put(UserData.Key_Token, userData.token);
        values.put(UserData.KEY_USER_fcm_id, userData.fcmId);
        values.put(UserData.KEY_USER_device_type, userData.deviceType);
        values.put(UserData.KEY_selfReferralCode, userData.selfReferralCode);
        values.put(UserData.KEY_registerReferralCode, userData.registerReferralCode);
        values.put(UserData.KEY_primeReferral, userData.primeReferral);
        values.put(UserData.KEY_primePlusReferral, userData.primePlusReferral);
        values.put(UserData.KEY_wallet, userData.wallet);
        values.put(UserData.KEY_cashback, userData.cashback);
        values.put(UserData.KEY_premiumBalance, userData.premiumBalance);
        values.put(UserData.KEY_referralCommission, userData.referralCommission);
        values.put(UserData.KEY_roleType, userData.roleType);
        values.put(UserData.KEY_userType, userData.userType);
        values.put(UserData.KEY_userCode, userData.userCode);
        values.put(UserData.KEY_userPin, userData.userPin);
        values.put(UserData.KEY_userPin, userData.userPin);
        values.put(UserData.KEY_created_at, userData.created_at);


        if (!isExist(userData)) {
            Utils.E("insert successfully");
            Utils.E("Values::" + values);
            db.insert(UserData.TABLE_NAME, null, values);
        } else {
            Utils.E("update successfully");
            db.update(UserData.TABLE_NAME, values, UserData.KEY_USER_ID + "=" + userData.userId, null);
        }
        close();
    }


    /**
     * Return User Array List
     *
     * @return //
     */
    @SuppressLint("Range")
    public ArrayList<UserData> getList() {
        ArrayList<UserData> userItem = new ArrayList<UserData>();
        read();
        Cursor cursor = db.rawQuery("select * from " + UserData.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                UserData userData = new UserData();
                userData.userId = cursor.getString(cursor.getColumnIndex(UserData.KEY_USER_ID));
                userData.name = cursor.getString(cursor.getColumnIndex(UserData.KeyUserName));
                userData.email = cursor.getString(cursor.getColumnIndex(UserData.KeyUserEmail));
                userData.mobile = cursor.getString(cursor.getColumnIndex(UserData.KeyUserMobile));
                userData.profilePic = cursor.getString(cursor.getColumnIndex(UserData.KEY_USER_Profile_PIC));
                userData.token = cursor.getString(cursor.getColumnIndex(UserData.Key_Token));
                userData.fcmId = cursor.getString(cursor.getColumnIndex(UserData.KEY_USER_fcm_id));
                userData.deviceType = cursor.getString(cursor.getColumnIndex(UserData.KEY_USER_device_type));
                userData.selfReferralCode = cursor.getString(cursor.getColumnIndex(UserData.KEY_selfReferralCode));
                userData.registerReferralCode = cursor.getString(cursor.getColumnIndex(UserData.KEY_registerReferralCode));
                userData.primeReferral = cursor.getString(cursor.getColumnIndex(UserData.KEY_primeReferral));
                userData.primePlusReferral = cursor.getString(cursor.getColumnIndex(UserData.KEY_primePlusReferral));
                userData.wallet = cursor.getString(cursor.getColumnIndex(UserData.KEY_wallet));
                userData.cashback = cursor.getString(cursor.getColumnIndex(UserData.KEY_cashback));
                userData.premiumBalance = cursor.getString(cursor.getColumnIndex(UserData.KEY_premiumBalance));
                userData.referralCommission = cursor.getString(cursor.getColumnIndex(UserData.KEY_referralCommission));
                userData.roleType = cursor.getString(cursor.getColumnIndex(UserData.KEY_roleType));
                userData.userType = cursor.getString(cursor.getColumnIndex(UserData.KEY_userType));
                userData.userCode = cursor.getString(cursor.getColumnIndex(UserData.KEY_userCode));
                userData.userPin = cursor.getString(cursor.getColumnIndex(UserData.KEY_userPin));
                userData.created_at = cursor.getString(cursor.getColumnIndex(UserData.KEY_created_at));
                userItem.add(userData);

            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }


}