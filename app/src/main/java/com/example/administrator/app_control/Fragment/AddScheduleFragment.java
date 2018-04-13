package com.example.administrator.app_control.Fragment;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.AlarmReminderDbHelper;
import com.example.administrator.app_control.Other.Item;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AddScheduleFragment extends Fragment {
    private EditText txtName;
    private TextView mTimeText,mRepeatTypeView;
    RelativeLayout relativeLayoutTime,relativeRepeat;
    private Calendar mCalendar;
    private int mHour, mMinute;
    private String time;
    private Switch mRepeatSwitch;
    private String mRepeatType;
    private Button btnOK;
    private ArrayList<Item> arrayList;


    private AlarmReminderDbHelper myDB;
    MqttHelper mqttHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.add_schedule, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTimeText = (TextView) getActivity().findViewById(R.id.set_time);
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mRepeatType = "";
        txtName = (EditText) getActivity().findViewById(R.id.schedule_title);
        relativeLayoutTime = (RelativeLayout) getActivity().findViewById(R.id.time);
        relativeRepeat = (RelativeLayout) getActivity().findViewById(R.id.RepeatType);
        mRepeatSwitch = (Switch) getActivity().findViewById(R.id.repeat_switch);
        btnOK = (Button) getActivity().findViewById(R.id.btnOK);
        mRepeatTypeView = (TextView) getActivity().findViewById(R.id.set_repeat_type);
        mqttHelper = new MqttHelper(getActivity(),"tcp://192.168.1.129:1883");

        relativeLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(minutes>=10 && hourOfDay>=10){
                            mTimeText.setText(hourOfDay +":"+minutes);
                            time = hourOfDay+minutes+"";
                        } else if(minutes<10 && hourOfDay>=10){
                            mTimeText.setText(hourOfDay +":0"+minutes);
                            time = hourOfDay+"0"+minutes;
                        } else if(minutes>=10 && hourOfDay<10){
                            mTimeText.setText(hourOfDay +":"+minutes);
                            time = "0"+hourOfDay+minutes;
                        } else {
                            mTimeText.setText("0"+hourOfDay +":0"+minutes);
                            time = "0"+hourOfDay+"0"+minutes;
                        }

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
                    for (int i = 0; i < checkedItems.length ; i++) {
                        checkedItems[i] = true;
                    }
                    mRepeatType = (new StringBuilder()).append("").toString();
                    // Create List Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    Toast.makeText(getActivity(),"Need to turn on repeat", Toast.LENGTH_LONG);
                    mRepeatType = "0000000";
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDB = new AlarmReminderDbHelper(getActivity());
                arrayList = myDB.getAllCotacts();
                boolean checked = mRepeatSwitch.isChecked();
                if (checked) {
                    if (arrayList.size() !=0){
                        for (int i = 0; i <arrayList.size() ; i++) {
                            if (arrayList.get(0).getID().equals(String.valueOf(i))){
                                myDB.insertData((i+1)+"",txtName.getText().toString(),mTimeText.getText().toString(),"1",mRepeatType,"1");
                                try {
                                    mqttHelper.publishMessage("1" + (i+1) + time +"1"+mRepeatType+"1","acc/schedule");
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    } else {
                        myDB.insertData("0",txtName.getText().toString(),mTimeText.getText().toString(),"1",mRepeatType,"1");
                        try {
                            mqttHelper.publishMessage("1" + "0" + time +"1"+mRepeatType+"1","acc/schedule");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    if (arrayList.size() !=0){
                        for (int i = 0; i <arrayList.size() ; i++) {
                            if (arrayList.get(i).getID().equals(String.valueOf(i))){
                                myDB.insertData(""+(i+1),txtName.getText().toString(),mTimeText.getText().toString(),"0",mRepeatType,"1");
                                try {
                                    mqttHelper.publishMessage("1" + (i+1) + time +"1"+mRepeatType+"1","acc/schedule");
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    } else {
                        myDB.insertData("0",txtName.getText().toString(),mTimeText.getText().toString(),"0",mRepeatType,"1");
                        try {
                            mqttHelper.publishMessage("1" + "0" + time +"1"+mRepeatType+"1","acc/schedule");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ScheduleFragment fragment = new ScheduleFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();


            }
        });

    }
}
