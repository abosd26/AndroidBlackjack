package com.example.tcumi_h505.androidblackjack;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tcumi_H505 on 2015/5/14.
 */
public class MyDBHelper extends SQLiteOpenHelper{

    static String name = "BlackjackDB";
    static SQLiteDatabase.CursorFactory factory = null;
    static int version = 1;
    public MyDBHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS  SystemUser (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Password TEXT) ";
        db.execSQL(SQL);
        String SQL2 = "CREATE TABLE IF NOT EXISTS  GameSetting (ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, DeckCount INTEGER, Amount INTEGER, Gamblable INTEGER) ";
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
