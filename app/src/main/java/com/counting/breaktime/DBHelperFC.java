package com.counting.breaktime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperFC extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Fast Count";
    public static final String TABLE_CONTACTS_FC = "statistics_FC";
    public static final String TABLE_CONTACTS_SS = "statistics_SS";
    public static final String COL1_FC = "ID";
    public static final String COL2_FC = "time";
    public static final String COL3_FC = "speed";
    public static final String COL4_FC = "range";
    public static final String COL5_FC = "result";

    public DBHelperFC(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS_FC + "(" + COL1_FC + " integer PRIMARY KEY," + COL2_FC + " TEXT," + COL3_FC + " TEXT," + COL4_FC + " TEXT," + COL5_FC + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_FC);
        onCreate(db);
    }

    public void addDataToFC(String col2, String col3, String col4, String col5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_FC, col2);
        contentValues.put(COL3_FC, col3);
        contentValues.put(COL4_FC, col4);
        contentValues.put(COL5_FC, col5);
        db.insert(DBHelperFC.TABLE_CONTACTS_FC, null, contentValues);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS_FC;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS_FC, null, null);
    }
}
