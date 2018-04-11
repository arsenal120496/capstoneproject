package com.example.administrator.app_control.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmReminderDbHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "ListItemView.db";
    public static final String TABLE_NAME = "Schedule_Table";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "name";
    public static final String COLUMN_3 = "time";
    public static final String COLUMN_4 = "repeat";
    public static final String COLUMN_5 = "repeatype";

    public AlarmReminderDbHelper(Context context){
        super(context,DB_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, time TEXT, repeat TEXT, repeatype TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String time, String reapeat, String repeatype){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,time);
        contentValues.put(COLUMN_4,reapeat);
        contentValues.put(COLUMN_5,repeatype);
        int result = (int) db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        return true;
    }

    public ArrayList<Item> getAllCotacts() {
        ArrayList<Item> array_list = new ArrayList<Item>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Item tmp = new Item();

            tmp.setName(COLUMN_2);
            tmp.setTime(COLUMN_3);
            tmp.setRepeat(COLUMN_4);
            tmp.setRepeatype(COLUMN_5);
            array_list.add(tmp);
            res.moveToNext();
        }

        return array_list;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME);
        return numRows;
    }
}
