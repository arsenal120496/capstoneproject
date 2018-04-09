package com.example.administrator.app_control.Activity;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
                            DatePickerDialog.OnDateSetListener,LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_VEHICLE_LOADER = 0;

    private EditText txtName;
    private Calendar calendar;
    private int hour,minute,second,;
    private long repeat;
    private Switch mRepeatSwitch;
    private String name;
    private String time;
    private String t_repeat;
    private String active;

    private Uri currentReminderUri;
    private boolean vehicleHasChanged = false;

    public  static String KEY_NAME = "name_key";

    public  static String KEY_TIME = "time_key";

    public  static String KEY_REPEAT = "repeat_key";

    public  static String KEY_ACTIVE = "active_key";

    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            vehicleHasChanged = true;
            return false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);

        Intent intent = getIntent();
        currentReminderUri = intent.getData();

        if(currentReminderUri == null){
            setTitle("Add Reminder Details");

            invalidateOptionsMenu();
        } else {
            setTitle("Edit Reminder");

            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER,null,this);
        }


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
