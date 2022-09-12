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

    public void deletePdfVideo(PdfVideoTable pdfVideo) {
        open();
        db.delete(PdfVideoTable.TABLE_NAME, PdfVideoTable.ORDER_EXAM_UUID + " = '"
                + pdfVideo.orderExamUUID + "'", null);
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
        @SuppressLint("Recycle") Cursor cur = db.rawQuery("select * from " + UserData.TABLE_NAME + " where " + UserData.KEY_StudentId + "='"
                + userData.studentId + "'", null);
        return cur.moveToFirst();
    }

    private boolean isExistPDF(PdfVideoTable pdfVideo) {
        read();
        @SuppressLint("Recycle") Cursor cur = db.rawQuery("select * from " + PdfVideoTable.TABLE_NAME + " where " + PdfVideoTable.ORDER_EXAM_UUID + "='"
                + pdfVideo.orderExamUUID + "'", null);
        return cur.moveToFirst();
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

    public void insertPdfData(PdfVideoTable pdfVideoTable) {
        open();
        ContentValues values = new ContentValues();
        // values.put(UserData.KEY_ID, userData.userId);
        values.put(PdfVideoTable.ORDER_EXAM_UUID, pdfVideoTable.orderExamUUID);
        values.put(PdfVideoTable.Topic_UUID, pdfVideoTable.topicUUID);
        values.put(PdfVideoTable.PDF_PATH, pdfVideoTable.pdfPath);
        values.put(PdfVideoTable.Date, pdfVideoTable.date);
        values.put(PdfVideoTable.TOPIC_NAME, pdfVideoTable.topicName);
        values.put(PdfVideoTable.TOPIC_DESC, pdfVideoTable.topicDescription);
        values.put(PdfVideoTable.VIDEO_PATH, pdfVideoTable.videoPath);
        values.put(PdfVideoTable.VIDEO_NAME, pdfVideoTable.videoName);
        values.put(PdfVideoTable.Video_DESC, pdfVideoTable.videoDescription);

        db.insert(PdfVideoTable.TABLE_NAME, null, values);

     /*   if (!isExistPDF(pdfVideoTable)) {
            Utils.E("insert successfully");
            Utils.E("Values::" + values);
            db.insert(PdfVideoTable.TABLE_NAME, null, values);
        } else {
            Utils.E("update successfully");
            db.update(PdfVideoTable.TABLE_NAME, values, PdfVideoTable.KEY_ID + "=" + pdfVideoTable.id, null);
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

    @SuppressLint("Range")
    public ArrayList<PdfVideoTable> getPdfList() {
        ArrayList<PdfVideoTable> userItem = new ArrayList<>();
        read();
        Cursor cursor = db.rawQuery("select * from " + PdfVideoTable.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                PdfVideoTable pdfVideoTable = new PdfVideoTable();
                // pdfVideoTable.userId = cursor.getString(cursor.getColumnIndex(PdfVideoTable.KEY_ID));
                pdfVideoTable.orderExamUUID = cursor.getString(cursor.getColumnIndex(PdfVideoTable.ORDER_EXAM_UUID));
                pdfVideoTable.topicUUID = cursor.getString(cursor.getColumnIndex(PdfVideoTable.Topic_UUID));
                pdfVideoTable.topicName = cursor.getString(cursor.getColumnIndex(PdfVideoTable.TOPIC_NAME));
                pdfVideoTable.pdfPath = cursor.getString(cursor.getColumnIndex(PdfVideoTable.PDF_PATH));
                pdfVideoTable.date = cursor.getString(cursor.getColumnIndex(PdfVideoTable.Date));
                pdfVideoTable.topicDescription = cursor.getString(cursor.getColumnIndex(PdfVideoTable.TOPIC_DESC));
                pdfVideoTable.videoPath = cursor.getString(cursor.getColumnIndex(PdfVideoTable.VIDEO_PATH));
                pdfVideoTable.videoName = cursor.getString(cursor.getColumnIndex(PdfVideoTable.VIDEO_NAME));
                pdfVideoTable.videoDescription = cursor.getString(cursor.getColumnIndex(PdfVideoTable.Video_DESC));

                userItem.add(pdfVideoTable);

            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }


}