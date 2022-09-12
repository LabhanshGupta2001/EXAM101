package com.dollop.exam101.Basics.Database;

import android.database.sqlite.SQLiteDatabase;

import com.dollop.exam101.Basics.UtilityTools.Utils;

public class PdfVideoTable {
    public static final String TABLE_NAME = "pdVideoTable";
    //    All Key
    public static final String KEY_ID = "id";
    public static final String ORDER_EXAM_UUID = "OrderExamUUID";
    public static final String Topic_UUID = "TopicUUID";
    public static final String PDF_PATH = "PdfPath";
    public static final String Date = "date";
    public static final String TOPIC_NAME = "TopicName";
    public static final String TOPIC_DESC = "TopicDescription";
    public static final String VIDEO_PATH = "VideoPath";
    public static final String VIDEO_NAME = "VideoName";
    public static final String Video_DESC = "VideoDescription";


        public String id;
        public String orderExamUUID;
        public String topicUUID;
        public String pdfPath;
        public String topicName;
        public String topicDescription;
        public String date;
        public String videoPath;
        public String videoName;
        public String videoDescription;


        /**
         * Create Table Query
     *
     * @param db SQLiteDatabase
     */
    public static void CreateTable(SQLiteDatabase db) {
        String CreateTableQuery = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ORDER_EXAM_UUID + " text," +
                Topic_UUID + " text," +
                PDF_PATH + " text," +
                TOPIC_NAME + " text," +
                TOPIC_DESC + " text," +
                Date + " text," +
                VIDEO_PATH + " text," +
                VIDEO_NAME + " text," +
                Video_DESC + " text" +
                " )";
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
