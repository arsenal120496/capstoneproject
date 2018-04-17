package com.example.administrator.app_control.Fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import com.example.administrator.app_control.Activity.MainActivity;
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


public class EditScheduleFragment extends Fragment {

    private EditText txtName;
    private TextView mTimeText;
    RelativeLayout relativeLayoutTime,relativeRepeat;
    private Calendar mCalendar;
    private int mHour, mMinute;
    private Switch mRepeatSwitch;
    private String mRepeatType;
    private Button btnEdit,btnEditCancle;
    private String timeSet;
    private StringBuilder m;


    private AlarmReminderDbHelper myDB;
    private MqttHelper mqttHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragmen

        return inflater.inflate(R.layout.edit_schedule, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTimeText = (TextView) getActivity().findViewById(R.id.edit_set_time);
        mqttHelper = MainActivity.mqttHelper;

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mRepeatType = "";
        txtName = (EditText) getActivity().findViewById(R.id.edit_schedule_title);
        relativeLayoutTime = (RelativeLayout) getActivity().findViewById(R.id.edit_time);
        relativeRepeat = (RelativeLayout) getActivity().findViewById(R.id.edit_RepeatType);
        mRepeatSwitch = (Switch) getActivity().findViewById(R.id.edit_repeat_switch);
        btnEdit = (Button) getActivity().findViewById(R.id.btnEdit);
        btnEditCancle = (Button) getActivity().findViewById(R.id.btnCancleEdit);

        String name = getArguments().getString("name");
        txtName.setText(name);
        txtName.setEnabled(false);
        String time = getArguments().getString("time");
        mTimeText.setText(time);

        final String[] arrayTime = time.split(":");
        timeSet = arrayTime[0]+""+arrayTime[1];
        relativeLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(minutes>=10 && hourOfDay>=10){
                            mTimeText.setText(hourOfDay +":"+minutes);
                            timeSet = hourOfDay+""+minutes+"";
                        } else if(minutes<10 && hourOfDay>=10){
                            mTimeText.setText(hourOfDay +":0"+minutes);
                            timeSet = hourOfDay+"0"+minutes;
                        } else if(minutes>=10 && hourOfDay<10){
                            mTimeText.setText(hourOfDay +":"+minutes);
                            timeSet = "0"+hourOfDay+""+minutes;
                        } else {
                            mTimeText.setText("0"+hourOfDay +":0"+minutes);
                            timeSet = "0"+hourOfDay+"0"+minutes;
                        }
                    }
                }, Integer.parseInt(arrayTime[0]), Integer.parseInt(arrayTime[1]), false);
                timePickerDialog.show();
            }
        });

        final int id = getArguments().getInt("id");
        final int active = getArguments().getInt("active");

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

        String repeat = getArguments().getString("repeat");
        if (repeat.equals("0")) {
            mRepeatSwitch.setChecked(false);
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
        } else if (repeat.equals("1")) {
            mRepeatSwitch.setChecked(true);
            mRepeatType = getArguments().getString("repeatype");
            relativeRepeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean on = mRepeatSwitch.isChecked();
                    if (on) {
                        String repeatype = getArguments().getString("repeatype");
                        mRepeatType=repeatype;
                        m = new StringBuilder(repeatype);
                        char b = '1';
                        if (repeatype.charAt(0) == b) {
                            checkedItems[0] = true;
                        } else {
                            checkedItems[0] = false;
                        }
                        if (repeatype.charAt(1) == b) {
                            checkedItems[1] = true;
                        } else {
                            checkedItems[1] = false;
                        }
                        if (repeatype.charAt(2) == b) {
                            checkedItems[2] = true;
                        } else {
                            checkedItems[2] = false;
                        }
                        if (repeatype.charAt(3) == b) {
                            checkedItems[3] = true;
                        } else {
                            checkedItems[3] = false;
                        }
                        if (repeatype.charAt(4) == b) {
                            checkedItems[4] = true;
                        } else {
                            checkedItems[4] = false;
                        }
                        if (repeatype.charAt(5) == b) {
                            checkedItems[5] = true;
                        } else {
                            checkedItems[5] = false;
                        }
                        if (repeatype.charAt(6) == b) {
                            checkedItems[6] = true;
                        } else {
                            checkedItems[6] = false;
                        }
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

                                for (int i = 0; i < checkedItems.length; i++) {
                                    boolean checked = checkedItems[i];
                                    if (checked) {
                                        m.setCharAt(i,'1');
                                        mRepeatType = m.toString();
                                    } else {
                                        m.setCharAt(i,'0');
                                        mRepeatType = m.toString();
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
                    }}});
        }



            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDB = new AlarmReminderDbHelper(getActivity());
                    boolean checked = mRepeatSwitch.isChecked();
                    if (checked) {
                        Item tmp = new Item();
                        tmp.setID(id);
                        tmp.setDescription(txtName.getText().toString());
                        tmp.setTime(mTimeText.getText().toString());
                        tmp.setIsRepeat(1);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(active);

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
                    } else {
                        Item tmp = new Item();
                        tmp.setID(id);
                        tmp.setDescription(txtName.getText().toString());
                        tmp.setTime(mTimeText.getText().toString());
                        tmp.setIsRepeat(0);
                        tmp.setRepeatDes(mRepeatType);
                        tmp.setIsActive(active);

                        myDB.updateData(tmp);
                        try {
                            if(mqttHelper !=null) {
                                mqttHelper.publishMessage("1" + id + timeSet + "0" + mRepeatType + "1", "acc/schedule");
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    ScheduleFragment fragment = new ScheduleFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                    System.out.println(mRepeatType);
                    System.out.println(timeSet);
                }
            });

        btnEditCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleFragment fragment = new ScheduleFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        }}


