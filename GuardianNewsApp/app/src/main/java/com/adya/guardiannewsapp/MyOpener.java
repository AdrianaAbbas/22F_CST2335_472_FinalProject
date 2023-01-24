package com.adya.guardiannewsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ArticlesDB";
    public final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "ARTICLE";
    public final static String COL_ID = "ID";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_SECTION = "SECTION";
    public final static String COL_PILLAR = "PILLAR";
    public final static String COL_PUBLISHED_DATE = "PUBLISHED_DATE";
    public final static String COL_URL = "URL";

    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " TEXT PRIMARY KEY, "
                        + COL_TITLE + " TEXT, "
                        + COL_PILLAR + " TEXT, "
                        + COL_SECTION + " TEXT, "
                        + COL_URL + " TEXT, "
                        + COL_PUBLISHED_DATE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
