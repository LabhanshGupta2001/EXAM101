package com.dollop.exam101.Basics.Database;

import android.database.sqlite.SQLiteDatabase;

import com.dollop.exam101.Basics.UtilityTools.Utils;

public class SearchHistoryTable {

    public static final String TABLE_NAME = "searchHistory";
    // All KEy...
    public static final String KEY_ID = "id";
    public static final String KEY_SEARCH_ITEM = "searchItem";


    public String KeyId;
    public String searchItem;



    public static void CreateTable(SQLiteDatabase db){
        String CreateTableQuery = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_SEARCH_ITEM + " text" +
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
