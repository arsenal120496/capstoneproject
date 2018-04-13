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
    public static final String COLUMN_2 = "Des";
    public static final String COLUMN_3 = "time";
    public static final String COLUMN_4 = "isRepeat";
    public static final String COLUMN_5 = "repeatDes";
    public static final String COLUMN_6 = "isActive";

    public AlarmReminderDbHelper(Context context){
        super(context,DB_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID TEXT, " +
                "Des TEXT, time TEXT, isRepeat TEXT, repeatDes TEXT, isActive TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ID,String name, String time, String reapeat, String repeatype,String isActive){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,ID);
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,time);
        contentValues.put(COLUMN_4,reapeat);
        contentValues.put(COLUMN_5,repeatype);
        contentValues.put(COLUMN_6,isActive);
        int result = (int) db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        return true;
    }

    public boolean updateData(String ID,String name, String time, String reapeat, String repeatype,String isActive){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,time);
        contentValues.put(COLUMN_4,reapeat);
        contentValues.put(COLUMN_5,repeatype);
        contentValues.put(COLUMN_6,isActive);
        int result = (int) db.update(TABLE_NAME,contentValues,COLUMN_1+"=?",new String[]{String.valueOf(ID)});

        if(result == -1){
            return false;
        }
        return true;
    }

    public boolean deleteData(String ID){

        SQLiteDatabase db = this.getWritableDatabase();
        int result = (int) db.delete(TABLE_NAME,COLUMN_1+"=?",new String[]{String.valueOf(ID)});

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
            tmp.setID(res.getString(0));
            tmp.setDescription(res.getString(1));
            tmp.setTime(res.getString(2));
            tmp.setIsRepeat(res.getString(3));
            tmp.setRepeatDes(res.getString(4));
            tmp.setIsActive(res.getString(5));
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
