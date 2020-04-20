package com.counting.breaktime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperSS extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Sign Selection";
    public static final String TABLE_CONTACTS_SS = "statistics_SS";

    public static final String COL1_SS = "ID";
    public static final String COL2_SS = "time";
    public static final String COL3_SS = "simile";
    public static final String COL4_SS = "range";
    public static final String COL5_SS = "level";

    public DBHelperSS(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS_SS + "(" + COL1_SS + " integer PRIMARY KEY," + COL2_SS + " TEXT," + COL3_SS + " TEXT," + COL4_SS + " TEXT," + COL5_SS + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_SS);
        onCreate(db);
    }

    public void addDataToSS(String col2, String col3, String col4, String col5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_SS, col2);
        contentValues.put(COL3_SS, col3);
        contentValues.put(COL4_SS, col4);
        contentValues.put(COL5_SS, col5);
        db.insert(TABLE_CONTACTS_SS, null, contentValues);
        System.out.println("add data " + col2 + "   " + col3 + "   " + col4 + "   " + col5);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS_SS;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS_SS, null, null);
    }
}
