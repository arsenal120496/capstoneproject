package com.example.administrator.app_control.Activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.administrator.app_control.Fragment.ScheduleFragment;
import com.example.administrator.app_control.Other.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddReminderActivity extends AppCompatActivity
                            {

    private static final int EXISTING_VEHICLE_LOADER = 0;

    private EditText txtName;
    private TextView mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    RelativeLayout relativeLayoutTime,relativeRepeat;
    private Calendar mCalendar;
    private int mHour, mMinute;
    private long repeat;
    private Switch mRepeatSwitch;
    private String name;
    private String mRepeatType;
    private String active;
    private Button btnOK;

    private TimePickerDialog timePickerDialog;

    private Uri currentReminderUri;
    private boolean vehicleHasChanged = false;

    public  static String KEY_NAME = "name_key";

    public  static String KEY_TIME = "time_key";

    public  static String KEY_REPEAT = "repeat_key";

    private static final String KEY_REPEAT_NO = "repeat_no_key";

    private static final String KEY_REPEAT_TYPE = "repeat_type_key";

    public  static String KEY_ACTIVE = "active_key";

    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    private ArrayList<Item> arr;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mRepeatType = "";
        arr = new ArrayList<>();
        txtName = (EditText) findViewById(R.id.schedule_title);
        relativeLayoutTime = (RelativeLayout) findViewById(R.id.time);
        relativeRepeat = (RelativeLayout) findViewById(R.id.RepeatType);
        mRepeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        btnOK = (Button) findViewById(R.id.btnOK);


        relativeLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mTimeText.setText(hourOfDay +":"+minutes);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        relativeRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean on = mRepeatSwitch.isChecked();
                if(on){
                    final String[] items = new String[7];

                    items[0] = "Monday";
                    items[1] = "Tuesday";
                    items[2] = "Wednesday";
                    items[3] = "Thursday";
                    items[4] = "Friday";
                    items[5] = "Saturday";
                    items[6] = "Sunday";

                    final List<String> itemList = Arrays.asList(items);
                    final boolean[] checkedItems = new boolean[items.length];
                    mRepeatType = (new StringBuilder()).append("").toString();
                    // Create List Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddReminderActivity.this);
                    builder.setTitle("Select Type");
                    builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            // Update the current focused item's checked status
                            checkedItems[which] = isChecked;

                        }
                    });

                    // Specify the dialog is not cancelable
                    builder.setCancelable(false);

                    // Set a title for alert dialog
                    builder.setTitle("Select repeat type");

                    // Set the positive/yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click positive button
                            for (int i = 0; i<checkedItems.length; i++){
                                boolean checked = checkedItems[i];
                                if (checked) {
                                    mRepeatType = mRepeatType + "1";
                                } else {
                                    mRepeatType = mRepeatType + "0";
                                }
                            }
                            Toast.makeText(getBaseContext(),mRepeatType,Toast.LENGTH_LONG).show();
                        }
                    });

                    // Set the negative/no button click listener
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click the negative button
                        }
                    });

                    // Set the neutral/cancel button click listener
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click the neutral button
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    mRepeatType = "0000000";
                    Toast.makeText(getBaseContext(),"NO REPEAT CHECKED",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item i = new Item();
                i.setName(txtName.getText().toString());
                i.setTime(mTimeText.getText().toString());
                i.setRepeat(mRepeatType);
                arr.add(i);

                Intent send = new Intent(getApplicationContext(),ScheduleFragment.class);
                send.putExtra("list",arr);


            }
        });

        getSupportActionBar().setTitle("Schedule");

    }

    }
