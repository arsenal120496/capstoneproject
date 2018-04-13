package com.example.administrator.app_control.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.AlarmReminderDbHelper;
import com.example.administrator.app_control.Other.Item;
import com.example.administrator.app_control.Other.ListItemAdapter;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ScheduleFragment extends Fragment {
    private FloatingActionButton btnAddRemider;
    AlarmReminderDbHelper dbHelper;
    TextView txtNoReminder, txtID;
    ListView itemListView;
    ArrayList<Item> arr;
    ListItemAdapter listItemAdapter;
    MqttHelper mqttHelper;
    Switch aSwitch;
    String idItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_schedule, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        itemListView = (ListView) getActivity().findViewById(R.id.listview);
        txtNoReminder = (TextView) getActivity().findViewById(R.id.no_reminder_text);
        txtID = (TextView) getActivity().findViewById(R.id.recycle_id);
        aSwitch = (Switch) getActivity().findViewById(R.id.active_switch);

        dbHelper = new AlarmReminderDbHelper(getActivity());

        getListItem();

        btnAddRemider = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        btnAddRemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("listItem",arr);
                AddScheduleFragment fragment2 = new AddScheduleFragment();
                fragment2.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment2);
                fragmentTransaction.commit();
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditScheduleFragment fragment2 = new EditScheduleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name",arr.get((int)l).getDescription());
                bundle.putString("repeat",arr.get((int)l).getIsRepeat());
                bundle.putString("time",arr.get((int)l).getTime());
                bundle.putString("id",arr.get((int)l).getID());
                bundle.putString("active",arr.get((int)l).getIsActive());
                bundle.putString("repeatype",arr.get((int)l).getRepeatDes());
                fragment2.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment2);
                fragmentTransaction.commit();
            }
        });

        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Do you want to delete this schedule ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(String.valueOf(l));
                        try {
                            mqttHelper = new MqttHelper(getActivity(),"tcp://192.168.1.129:1883");
                            mqttHelper.publishMessage("1" + l,"acc/schedule");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(),"Delete successfull",Toast.LENGTH_LONG);
                        ScheduleFragment fragment2 = new ScheduleFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frame, fragment2);
                        fragmentTransaction.commit();
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
                return true;
            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        mqttHelper.publishMessage("3" + txtID.getText() + "1" ,"acc/schedule");
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    // The toggle is disabled
                    try {
                        mqttHelper.publishMessage("3" + txtID.getText() +"0" ,"acc/schedule");
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getListItem(){
        if(getActivity() == null){
            return;
        }

        arr = dbHelper.getAllCotacts();
        if(arr.size() == 0){
            txtNoReminder.setVisibility(View.VISIBLE);
            return;
        } else {
            listItemAdapter = new ListItemAdapter(arr,getActivity());
            itemListView.setAdapter(listItemAdapter);
            txtNoReminder.setVisibility(View.INVISIBLE);
        }

    }


}
