package com.example.administrator.app_control.Activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText txtName;
    private TextView txtRepeatType;
    RelativeLayout relativeRepeat;
    private String timeSet;
    private String mRepeatType;
    private Button btnOK;
    private Button btnCancle;
    private ArrayList<Item> arrayList;
    private TimePicker timePicker;
    private int isRepeat;
    private String viewRepeatType,timeView;


    private AlarmReminderDbHelper myDB;
    private MqttHelper mqttHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);


        // Set initial variable
        mqttHelper = MainActivity.mqttHelper;
        myDB = new AlarmReminderDbHelper(getBaseContext());
        arrayList = myDB.getAllCotacts();

        // Set variable for each component
        timePicker = (TimePicker) findViewById(R.id.timepick);
        txtName = (EditText) findViewById(R.id.schedule_title);
        relativeRepeat = (RelativeLayout) findViewById(R.id.RepeatType);
        txtRepeatType = (TextView) findViewById(R.id.set_repeat_type);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancle = (Button) findViewById(R.id.btnCancle);


        // Set initial variable
        mRepeatType = "0000000";
        viewRepeatType = "Not Repeat";
        txtRepeatType.setText(viewRepeatType);
        timePicker.setIs24HourView(true);

        // Set time for TimePicker
        if (timePicker.getMinute() >= 10 && timePicker.getHour() >= 10) {
            timeSet = timePicker.getHour() + "" + timePicker.getMinute() + "";
            timeView = timePicker.getHour() + ":" + timePicker.getMinute();
        } else if (timePicker.getMinute() < 10 && timePicker.getHour() >= 10) {
            timeSet = timePicker.getHour() + "0" + timePicker.getMinute();
            timeView = timePicker.getHour() + ":0" + timePicker.getMinute();
        } else if (timePicker.getMinute() >= 10 && timePicker.getHour() < 10) {
            timeSet = "0" + timePicker.getHour() + "" + timePicker.getMinute();
            timeView = timePicker.getHour() + ":" + timePicker.getMinute();
        } else {
            timeSet = "0" + timePicker.getHour() + "0" + timePicker.getMinute();
            timeView = timePicker.getHour() + ":0" + timePicker.getMinute();
        }


        // Set action on change time
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minutes) {
                if (minutes >= 10 && hourOfDay >= 10) {
                    timeSet = hourOfDay + "" + minutes + "";
                    timeView = timePicker.getHour() + ":" + timePicker.getMinute();
                } else if (minutes < 10 && hourOfDay >= 10) {
                    timeSet = hourOfDay + "0" + minutes;
                    timeView = timePicker.getHour() + ":0" + timePicker.getMinute();
                } else if (minutes >= 10 && hourOfDay < 10) {
                    timeSet = "0" + hourOfDay + "" + minutes;
                    timeView = timePicker.getHour() + ":" + timePicker.getMinute();
                } else {
                    timeSet = "0" + hourOfDay + "0" + minutes;
                    timeView = timePicker.getHour() + ":0" + timePicker.getMinute() ;
                }
            }

        });

        relativeRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRepeatType = "";
                final String[] items = new String[3];
                items[0] = "Not Repeat";
                items[1] = "Everyday";
                items[2] = "More Options";

                final boolean[] checkedItems = new boolean[items.length];
                final List<String> itemList = Arrays.asList(items);
                mRepeatType = (new StringBuilder()).append("").toString();

                // Create List Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddScheduleActivity.this);

                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update the current focused item's checked status
                        switch (which){
                            case 0:
                                isRepeat = 0;
                                mRepeatType = "0000000";
                                viewRepeatType = "Not Repeat";
                                break;
                            case 1:
                                isRepeat = 1;
                                mRepeatType = "1111111";
                                viewRepeatType = "Mon Tue Wed Thu Fri Sat Sun";
                                break;
                            case 2:
                                isRepeat = 2;
                                mRepeatType = "";
                                final String[] itemOfDate = new String[7];
                                itemOfDate[0] = "Monday";
                                itemOfDate[1] = "Tuesday";
                                itemOfDate[2] = "Wednesday";
                                itemOfDate[3] = "Thursday";
                                itemOfDate[4] = "Friday";
                                itemOfDate[5] = "Saturday";
                                itemOfDate[6] = "Sunday";

                                final boolean[] checkedItemOfDate = new boolean[itemOfDate.length];

                                AlertDialog.Builder builder = new AlertDialog.Builder(AddScheduleActivity.this);
                                builder.setTitle("Select Date");
                                builder.setMultiChoiceItems(itemOfDate, checkedItemOfDate, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        // Update the current focused item's checked status
                                        checkedItemOfDate[which] = isChecked;
                                    }
                                });

                                // Specify the dialog is not cancelable
                                builder.setCancelable(false);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int ii) {
                                        for (int i = 0; i < checkedItemOfDate.length; i++) {
                                            boolean checked = checkedItemOfDate[i];
                                            if (checked) {
                                                mRepeatType = mRepeatType + "1";
                                            } else {
                                                mRepeatType = mRepeatType + "0";
                                            }
                                        }
                                        if (mRepeatType.charAt(0) == '1') {
                                            viewRepeatType += "Mon ";
                                        }
                                        if (mRepeatType.charAt(1) == '1') {
                                            viewRepeatType += "Tue ";
                                        }
                                        if (mRepeatType.charAt(2) == '1') {
                                            viewRepeatType += "Wed ";
                                        }
                                        if (mRepeatType.charAt(3) == '1') {
                                            viewRepeatType += "Thu ";
                                        }
                                        if (mRepeatType.charAt(4) == '1') {
                                            viewRepeatType += "Fri ";
                                        }
                                        if (mRepeatType.charAt(5) == '1') {
                                            viewRepeatType += "Sat ";
                                        }
                                        if (mRepeatType.charAt(6) == '1') {
                                            viewRepeatType += "Sun";
                                        }
                                    }
                                });

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

                                AlertDialog dialogDate = builder.create();
                                dialogDate.show();
                                break;
                                default:
                                    break;
                        }
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
                        txtRepeatType.setText(viewRepeatType);
                        viewRepeatType = "";
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
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.size() > 1) {
                    boolean check = false;
                    for (int i = 1; i < arrayList.size(); i++) {
                        int pre_id = arrayList.get(i - 1).getID();
                        int current_id = arrayList.get(i).getID();
                        if (pre_id + 1 != current_id) {

                            Item tmp = new Item();
                            tmp.setID(i);
                            tmp.setDescription(txtName.getText().toString());
                            tmp.setTime(timeView);
                            tmp.setRepeatDes(mRepeatType);
                            tmp.setIsActive(1);
                            tmp.setIsRepeat(isRepeat);

                            myDB.insertData(tmp);
                            try {
                                if (mqttHelper != null) {
                                    mqttHelper.publishMessage("1" + ((i) + "") + timeSet + mRepeatType + "1", "acc/schedule");
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
                            tmp.setTime(timeView);
                            tmp.setRepeatDes(mRepeatType);
                            tmp.setIsActive(1);
                            tmp.setIsRepeat(isRepeat);

                            myDB.insertData(tmp);
                            try {
                                if (mqttHelper != null) {
                                    mqttHelper.publishMessage("1" + arrayList.size() + "" + timeSet + mRepeatType + "1", "acc/schedule");
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
                    tmp.setTime(timeView);
                    tmp.setRepeatDes(mRepeatType);
                    tmp.setIsActive(1);
                    tmp.setIsRepeat(isRepeat);

                    myDB.insertData(tmp);
                    try {
                        if (mqttHelper != null) {
                            mqttHelper.publishMessage("1" + "0" + timeSet + mRepeatType + "1", "acc/schedule");
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
                        tmp.setTime(timeView);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(1);
                        tmp.setIsRepeat(isRepeat);

                        myDB.insertData(tmp);
                        try {
                            if (mqttHelper != null) {
                                mqttHelper.publishMessage("1" + "0" + timeSet + mRepeatType + "1", "acc/schedule");
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
                        tmp.setTime(timeView);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(1);
                        tmp.setIsRepeat(isRepeat);

                        myDB.insertData(tmp);
                        try {
                            if (mqttHelper != null) {
                                mqttHelper.publishMessage("1" + "1" + timeSet + mRepeatType + "1", "acc/schedule");
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (arrayList.size() >10){
                    Item tmp = new Item();
                    tmp.setID(9);
                    tmp.setDescription(txtName.getText().toString());
                    tmp.setTime(timeView);
                    tmp.setRepeatDes(mRepeatType);
                    tmp.setIsActive(1);
                    tmp.setIsRepeat(isRepeat);

                    // Update 9th schedule
                    myDB.updateData(tmp);
                }
                Intent intent = new Intent(AddScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }

        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}
