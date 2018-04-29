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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity {

    private EditText txtName;
    RelativeLayout relativeLayoutTime,relativeRepeat;
    private String mRepeatType;
    private TimePicker timePicker;
    private Button btnEdit,btnEditCancle;
    private TextView txtRepeatType;
    private String timeSet,viewRepeatType;
    private int isRepeat1 = -1;
    private boolean checkTouch = false;

    private AlarmReminderDbHelper myDB;
    private MqttHelper mqttHelper;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        // Set variable for each component
        mqttHelper = MainActivity.mqttHelper;
        timePicker = (TimePicker) findViewById(R.id.edit_timepick);
        txtName = (EditText) findViewById(R.id.edit_schedule_title);
        relativeLayoutTime = (RelativeLayout) findViewById(R.id.edit_time);
        relativeRepeat = (RelativeLayout) findViewById(R.id.edit_RepeatType);
        txtRepeatType = (TextView) findViewById(R.id.edit_set_repeat_type);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEditCancle = (Button) findViewById(R.id.btnEditCancle);


        //Get intent contains information of an item
        final Intent intent = getIntent();
        final String time = intent.getStringExtra("time");
        final String name = intent.getStringExtra("name");
        final int id = intent.getIntExtra("id",0);
        final int isActive = intent.getIntExtra("isActive",0);
        final int isRepeat = intent.getIntExtra("isRepeat",0);
        final String repeatType = intent.getStringExtra("repeatType");


        // Set initial time for TimePicker
        final String[] arrayTime = time.split(":");
        timeSet = arrayTime[0]+""+arrayTime[1];
        timePicker.setIs24HourView(true);
        timePicker.setHour(Integer.parseInt(arrayTime[0]));
        timePicker.setMinute(Integer.parseInt(arrayTime[1]));
        txtName.setText(name);


        // Set action on time change
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(minute>=10 && hourOfDay>=10){
                    timeSet = hourOfDay+""+minute+"";
                } else if(minute<10 && hourOfDay>=10){
                    timeSet = hourOfDay+"0"+minute;
                } else if(minute>=10 && hourOfDay<10){
                    timeSet = "0"+hourOfDay+""+minute;
                } else {
                    timeSet = "0"+hourOfDay+"0"+minute;
                }
            }
        });

        // Set repeat type view
        String repeatDes = "";
        if(isRepeat == 0){
            txtRepeatType.setText("Not Repeat");
        } else if(isRepeat == 1){
            txtRepeatType.setText("Mon Tue Wed Thu Fri Sat Sun");
        } else if(isRepeat == 2){
            char b = '1';
            if (repeatType.charAt(0) == b) {
                repeatDes += "Mon ";
            }
            if (repeatType.charAt(1) == b) {
                repeatDes += "Tue ";
            }
            if (repeatType.charAt(2) == b) {
                repeatDes += "Wed ";
            }
            if (repeatType.charAt(3) == b) {
                repeatDes += "Thu ";
            }
            if (repeatType.charAt(4) == b) {
                repeatDes += "Fri ";
            }
            if (repeatType.charAt(5) == b) {
                repeatDes += "Sat ";
            }
            if (repeatType.charAt(6) == b) {
                repeatDes += "Sun";
            }
            txtRepeatType.setText(repeatDes);
        }


        relativeRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepeatType = "";

                final String[] items = new String[3];
                items[0] = "Not Repeat";
                items[1] = "Everyday";
                items[2] = "More Options";

                final boolean[] checkedItems = new boolean[items.length];
                final List<String> itemList = Arrays.asList(items);
                mRepeatType = (new StringBuilder()).append("").toString();
                // Create List Dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditScheduleActivity.this);

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update the current focused item's checked status
                        switch (which){
                            case 0:
                                isRepeat1 = 0;
                                mRepeatType = "0000000";
                                viewRepeatType = "Not Repeat";
                                break;
                            case 1:
                                isRepeat1 = 1;
                                mRepeatType = "1111111";
                                viewRepeatType = "Mon Tue Wed Thu Fri Sat Sun";
                                break;
                            case 2:
                                isRepeat1 = 2;
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
                                if(checkTouch == false){
                                    for (int i = 0; i < repeatType.length(); i++) {
                                        if(repeatType.charAt(i) == '1'){
                                            checkedItemOfDate[i] = true;
                                        } else if(repeatType.charAt(i) == '0'){
                                            checkedItemOfDate[i] = false;
                                        }
                                    }
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(EditScheduleActivity.this);
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
                                            checkTouch = true;
                                            if (checked) {
                                                mRepeatType = mRepeatType + "1";
                                            } else {
                                                mRepeatType = mRepeatType + "0";
                                            }
                                        }
                                        System.out.println("check repeat type:" + mRepeatType);
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




        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new AlarmReminderDbHelper(getBaseContext());
                Item tmp = new Item();
                tmp.setID(id);
                tmp.setDescription(txtName.getText().toString());
                tmp.setTime(timeSet);
                tmp.setIsRepeat(1);
                tmp.setRepeatDes(mRepeatType);
                tmp.setIsActive(isRepeat1);

                myDB.updateData(tmp);
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("1" + id + timeSet + "1" + mRepeatType + "1", "acc/schedule");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Intent intent1 = new Intent(EditScheduleActivity.this,ScheduleActivity.class);
                startActivity(intent1);
            }
        });

        btnEditCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditScheduleActivity.this,ScheduleActivity.class);
                startActivity(intent1);
            }
        });
    }

}
