package com.example.administrator.app_control.Activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.app_control.Other.AlarmReminderDbHelper;
import com.example.administrator.app_control.Other.Item;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText txtName;
    private TextView mTimeText, mRepeatTypeView;
    RelativeLayout relativeLayoutTime, relativeRepeat;
    private Calendar mCalendar;
    private int mHour, mMinute;
    private String time;
    private Switch mRepeatSwitch;
    private String mRepeatType;
    private Button btnOK;
    private Button btnCancle;
    private ArrayList<Item> arrayList;


    private AlarmReminderDbHelper myDB;
    private MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        mqttHelper = MainActivity.mqttHelper;

        mTimeText = (TextView) findViewById(R.id.set_time);
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mRepeatType = "1111111";
        txtName = (EditText) findViewById(R.id.schedule_title);
        relativeLayoutTime = (RelativeLayout) findViewById(R.id.time);
        relativeRepeat = (RelativeLayout) findViewById(R.id.RepeatType);
        mRepeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        mRepeatTypeView = (TextView) findViewById(R.id.set_repeat_type);


        relativeLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getBaseContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (minutes >= 10 && hourOfDay >= 10) {
                            mTimeText.setText(hourOfDay + ":" + minutes);
                            time = hourOfDay + "" + minutes + "";
                        } else if (minutes < 10 && hourOfDay >= 10) {
                            mTimeText.setText(hourOfDay + ":0" + minutes);
                            time = hourOfDay + "0" + minutes;
                        } else if (minutes >= 10 && hourOfDay < 10) {
                            mTimeText.setText(hourOfDay + ":" + minutes);
                            time = "0" + hourOfDay + "" + minutes;
                        } else  {
                            mTimeText.setText(hourOfDay + ":0" + minutes);
                            time = "0" + hourOfDay + "0" + minutes;
                        }

                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        relativeRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean on = mRepeatSwitch.isChecked();
                if (on) {
                    mRepeatType = "";
                    final String[] items = new String[7];
                    items[0] = "Everyday";
                    items[1] = "Monday";
                    items[2] = "Tuesday";
                    items[3] = "Wednesday";
                    items[4] = "Thursday";
                    items[5] = "Friday";
                    items[6] = "Saturday";
                    items[7] = "Sunday";

                    final List<String> itemList = Arrays.asList(items);
                    final boolean[] checkedItems = new boolean[items.length];
                    mRepeatType = (new StringBuilder()).append("").toString();
                    // Create List Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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
                            if(checkedItems[0]) {
                                mRepeatType = "1111111";
                            } else {
                                for (int i = 1; i < checkedItems.length; i++) {
                                    boolean checked = checkedItems[i];
                                    if (checked) {
                                        mRepeatType = mRepeatType + "1";
                                    } else {
                                        mRepeatType = mRepeatType + "0";
                                    }
                                }
                            }
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

                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean on = mRepeatSwitch.isChecked();
                if (!on) {
                    mRepeatType = "0000000";
                }
                myDB = new AlarmReminderDbHelper(getBaseContext());
                arrayList = myDB.getAllCotacts();
                boolean checked = mRepeatSwitch.isChecked();
                if (arrayList.size() > 1) {
                    boolean check = false;
                    for (int i = 1; i < arrayList.size(); i++) {
                        int pre_id = arrayList.get(i - 1).getID();
                        int current_id = arrayList.get(i).getID();
                        if (pre_id + 1 != current_id) {

                            Item tmp = new Item();
                            tmp.setID(i);
                            tmp.setDescription(txtName.getText().toString());
                            tmp.setTime(mTimeText.getText().toString());
                            tmp.setIsRepeat(checked ? 1 : 0);
                            tmp.setRepeatDes(mRepeatType);
                            tmp.setIsActive(1);

                            myDB.insertData(tmp);
                            try {
                                if (mqttHelper != null) {
                                    mqttHelper.publishMessage("1" + ((i) + "") + time + (checked ? "1" : "0") + mRepeatType + "1", "acc/schedule");
                                }
                            } catch (MqttException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            check = false;
                            break;
                        } else {
                            check = true;
                        }
                        if (check) {
                            Item tmp = new Item();
                            tmp.setID(arrayList.size());
                            tmp.setDescription(txtName.getText().toString());
                            tmp.setTime(mTimeText.getText().toString());
                            tmp.setIsRepeat(checked ? 1 : 0);
                            tmp.setRepeatDes(mRepeatType);
                            tmp.setIsActive(1);

                            myDB.insertData(tmp);
                            try {
                                if (mqttHelper != null) {
                                    mqttHelper.publishMessage("1" + arrayList.size() + "" + time + (checked ? "1" : "0") + mRepeatType + "1", "acc/schedule");
                                }
                            } catch (MqttException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (arrayList.size() == 0) {

                    Item tmp = new Item();
                    tmp.setID(0);
                    tmp.setDescription(txtName.getText().toString());
                    tmp.setTime(mTimeText.getText().toString());
                    tmp.setIsRepeat(checked ? 1 : 0);
                    tmp.setRepeatDes(mRepeatType);
                    tmp.setIsActive(1);

                    myDB.insertData(tmp);
                    try {
                        if (mqttHelper != null) {
                            mqttHelper.publishMessage("1" + "0" + time + (checked ? "1" : "0") + mRepeatType + "1", "acc/schedule");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (arrayList.size() == 1) {
                    int current_id = arrayList.get(0).getID();
                    if (current_id != 0) {
                        Item tmp = new Item();
                        tmp.setID(0);
                        tmp.setDescription(txtName.getText().toString());
                        tmp.setTime(mTimeText.getText().toString());
                        tmp.setIsRepeat(checked ? 1 : 0);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(1);

                        myDB.insertData(tmp);
                        try {
                            if (mqttHelper != null) {
                                mqttHelper.publishMessage("1" + "0" + time + (checked ? "1" : "0") + mRepeatType + "1", "acc/schedule");
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Item tmp = new Item();
                        tmp.setID(1);
                        tmp.setDescription(txtName.getText().toString());
                        tmp.setTime(mTimeText.getText().toString());
                        tmp.setIsRepeat(checked ? 1 : 0);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(1);

                        myDB.insertData(tmp);
                        try {
                            if (mqttHelper != null) {
                                mqttHelper.publishMessage("1" + "1" + time + (checked ? "1" : "0") + mRepeatType + "1", "acc/schedule");
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Intent intent1 = new Intent(AddScheduleActivity.this,ScheduleActivity.class);
                startActivity(intent1);
            }

        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddScheduleActivity.this,ScheduleActivity.class);
                startActivity(intent1);
            }
        });
    }
}
