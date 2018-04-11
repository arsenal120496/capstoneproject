package com.example.administrator.app_control.Other;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmReminderContract {
    private AlarmReminderContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.administrator.app_control";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VEHICLE = "remider-path";

    public static final class AlarmRemiderEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_VEHICLE);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public final static String TABLE_NAME = "reminders";

        public final static String _ID = BaseColumns._ID;

        public  static String KEY_NAME = "name";

        public  static String KEY_TIME = "time";

        public  static String KEY_REPEAT = "repeat";

        public static final String KEY_REPEAT_TYPE = "repeat_type";

        public  static String KEY_ACTIVE = "active";

    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
