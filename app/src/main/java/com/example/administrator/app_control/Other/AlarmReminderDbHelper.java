package com.example.administrator.app_control.Other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmReminderDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarmreminder.db";

    private static final int DATABASE_VERSION = 1;

    public AlarmReminderDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmReminderContract.AlarmRemiderEntry.TABLE_NAME + " {"
                    + AlarmReminderContract.AlarmRemiderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmReminderContract.AlarmRemiderEntry.KEY_NAME + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmRemiderEntry.KEY_TIME + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmRemiderEntry.KEY_ACTIVE + " TEXT NOT NULL " + "};";

        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
