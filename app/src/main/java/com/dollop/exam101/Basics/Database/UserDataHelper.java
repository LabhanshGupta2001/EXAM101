package com.dollop.exam101.Basics.Database;

import static com.dollop.exam101.Basics.Database.SearchHistoryTable.KEY_ID;

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
    private final DataManager dm;
    Context cx;
    private SQLiteDatabase db;

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
        db.delete(UserData.TABLE_NAME, UserData.KEY_StudentId + " = '"
                + userData.studentId + "'", null);
        close();
    }


    public void deleteFirstRow() {
        open();
        Cursor cursor = db.rawQuery("select * from " + SearchHistoryTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String rowId = cursor.getString(cursor.getColumnIndex(KEY_ID));

            db.delete(SearchHistoryTable.TABLE_NAME, KEY_ID + "=?", new String[]{rowId});
        }
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
     * delete All Data From the Table
     */
    public void deleteSearchAll() {
        open();
        db.delete(SearchHistoryTable.TABLE_NAME, null, null);
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
        @SuppressLint("Recycle") Cursor cur = db.rawQuery("select * from " + UserData.TABLE_NAME + " where " + UserData.KEY_StudentId + "='"
                + userData.studentId + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }

    /**
     * is Exist in table
     *
     * @param searchHistoryTable //
     * @param values
     * @return //
     */
    private void isExistSearch(SearchHistoryTable searchHistoryTable, ContentValues values) {
        read();
        @SuppressLint("Recycle") Cursor cur = db.rawQuery("select * from " + SearchHistoryTable.TABLE_NAME + " where " +
                SearchHistoryTable.KEY_SEARCH_ITEM + " = ?", new String[]{searchHistoryTable.searchItem});
        if (cur.moveToFirst()) {
            @SuppressLint("Range") String Item = cur.getString(cur.getColumnIndex(KEY_ID));
            Utils.E("Item::"+Item);
            db.delete(SearchHistoryTable.TABLE_NAME, SearchHistoryTable.KEY_ID + "=" + Item, null);
            db.insert(SearchHistoryTable.TABLE_NAME, null, values);
            return;
        }
        db.insert(SearchHistoryTable.TABLE_NAME, null, values);
    }



    /**
     * insert Data in table
     *
     * @param userData //
     */
    public void insertData(UserData userData) {
        open();
        ContentValues values = new ContentValues();
        // values.put(UserData.KEY_ID, userData.userId);
        values.put(UserData.KEY_StudentId, userData.studentId);
        values.put(UserData.Key_StudentName, userData.studentName);
        values.put(UserData.Key_StudentEmail, userData.studentEmail);
        values.put(UserData.Key_StudentMobileNo, userData.studentMobileNo);
        values.put(UserData.Key_CountryCode, userData.countryCode);
        values.put(UserData.Key_CountryName, userData.countryName);
        values.put(UserData.Key_StateName, userData.stateName);
        values.put(UserData.Key_FcmId, userData.fcmId);
        values.put(UserData.Key_MobileVerified, userData.mobileVerified);
        values.put(UserData.Key_EmailVerified, userData.emailVerified);
        values.put(UserData.Key_RoleType, userData.roleType);
        values.put(UserData.KEY_profilePic, userData.profilePic);
        values.put(UserData.KEY_Token, userData.token);
        values.put(UserData.KEY_isPasswordGenerated, userData.isPasswordGenerated);


        if (!isExist(userData)) {
            Utils.E("insert successfully");
            Utils.E("Values::" + values);
            db.insert(UserData.TABLE_NAME, null, values);
        } else {
            Utils.E("update successfully");
            db.update(UserData.TABLE_NAME, values, UserData.KEY_StudentId + "=" + userData.studentId, null);
        }
        close();
    }

    /**
     * insert Data in table
     *
     * @param searchHistoryTable //
     */
    public void insertSearchHistoryData(SearchHistoryTable searchHistoryTable) {
        open();
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEY_SEARCH_ITEM, searchHistoryTable.searchItem);
        // values.put(SearchHistoryTable.KEY_ID, searchHistoryTable.KeyId);
        isExistSearch(searchHistoryTable,values);
    /*
        if (!) {
            Utils.E("insert successfully");
            Utils.E("Values::" + values);

        } else {
            Utils.E("update successfully");
            // db.update(SearchHistoryTable.TABLE_NAME, values, KEY_ID + "=" + searchHistoryTable.KeyId, null);
           // db.execSQL("DROP TABLE IF EXISTS " + SearchHistoryTable.TABLE_NAME);

        }*/
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
                // userData.userId = cursor.getString(cursor.getColumnIndex(UserData.KEY_ID));
                userData.studentId = cursor.getString(cursor.getColumnIndex(UserData.KEY_StudentId));
                userData.studentName = cursor.getString(cursor.getColumnIndex(UserData.Key_StudentName));
                userData.studentEmail = cursor.getString(cursor.getColumnIndex(UserData.Key_StudentEmail));
                userData.studentMobileNo = cursor.getString(cursor.getColumnIndex(UserData.Key_StudentMobileNo));
                userData.countryCode = cursor.getString(cursor.getColumnIndex(UserData.Key_CountryCode));
                userData.countryName = cursor.getString(cursor.getColumnIndex(UserData.Key_CountryName));
                userData.stateName = cursor.getString(cursor.getColumnIndex(UserData.Key_StateName));
                userData.fcmId = cursor.getString(cursor.getColumnIndex(UserData.Key_FcmId));
                userData.mobileVerified = cursor.getString(cursor.getColumnIndex(UserData.Key_MobileVerified));
                userData.emailVerified = cursor.getString(cursor.getColumnIndex(UserData.Key_EmailVerified));
                userData.roleType = cursor.getString(cursor.getColumnIndex(UserData.Key_RoleType));
                userData.profilePic = cursor.getString(cursor.getColumnIndex(UserData.KEY_profilePic));
                userData.token = cursor.getString(cursor.getColumnIndex(UserData.KEY_Token));
                userData.isPasswordGenerated = cursor.getString(cursor.getColumnIndex(UserData.KEY_isPasswordGenerated));

                userItem.add(userData);

            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }


    /**
     * Return Search Array List
     *
     * @return //
     */
    @SuppressLint("Range")
    public ArrayList<SearchHistoryTable> getSearchList() {
        ArrayList<SearchHistoryTable> searchItem = new ArrayList<SearchHistoryTable>();
        read();
        Cursor cursor = db.rawQuery("select * from " + SearchHistoryTable.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                SearchHistoryTable historyTable = new SearchHistoryTable();
                // historyTable.KeyId = cursor.getString(cursor.getColumnIndex(historyTable.KYE_ID));
                historyTable.searchItem = cursor.getString(cursor.getColumnIndex(SearchHistoryTable.KEY_SEARCH_ITEM));
                searchItem.add(historyTable);

            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return searchItem;
    }


}