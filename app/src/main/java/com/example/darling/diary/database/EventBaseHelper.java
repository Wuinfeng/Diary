package com.example.darling.diary.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.darling.diary.database.EventDbSchema.EventTable;

/**
 * Created by Darling on 2017/4/8.
 */

public class EventBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION =1;
    private static final String DATABASE_NAME = "eventBase.db";

    public EventBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table mytable (_id integer primary key autoincrement, stext text)";
        db.execSQL("create table "+EventTable.NAME+" (_id integer primary key autoincrement,"+ EventTable.Cols.UUID+","
                +EventTable.Cols.TITLE+","
                +EventTable.Cols.DTAE+","
                +EventTable.Cols.SOLVED+","
                +EventTable.Cols.SUSPECT+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
