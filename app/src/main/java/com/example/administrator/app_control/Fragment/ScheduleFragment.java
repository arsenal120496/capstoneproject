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

import com.example.administrator.app_control.Activity.MainActivity;
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
    private AlarmReminderDbHelper dbHelper;
    private TextView txtNoReminder, txtID;
    private ListView itemListView;
    private ArrayList<Item> arr;
    private ListItemAdapter listItemAdapter;
    private MqttHelper mqttHelper;
    private Switch aSwitch;
    private String idItem;
    private Context context;

    ISchedulerListener iSchedulerListener = new ISchedulerListener() {
        @Override
        public Context getContext() {
            return getActivity();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        View rootView = inflater.inflate(R.layout.fragment_schedule,parent,false);
        dbHelper = new AlarmReminderDbHelper(getActivity());
        itemListView = (ListView) rootView.findViewById(R.id.listview);
        txtNoReminder = (TextView) rootView.findViewById(R.id.no_reminder_text);

        mqttHelper = MainActivity.mqttHelper;

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditScheduleFragment fragment2 = new EditScheduleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name",arr.get((int)l).getDescription());
                bundle.putInt("repeat",arr.get((int)l).getIsRepeat());
                bundle.putString("time",arr.get((int)l).getTime());
                bundle.putInt("id",arr.get((int)l).getID());
                bundle.putInt("active",arr.get((int)l).getIsActive());
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
                        dbHelper.deleteData((int)l);
                        try {
                            if(mqttHelper!=null){
                                mqttHelper.publishMessage("2" + l,"acc/schedule");
                            }
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
        getListItem();
        listItemAdapter = new ListItemAdapter(arr,getActivity(), iSchedulerListener);
        if(listItemAdapter != null){
            itemListView.setAdapter(listItemAdapter);
        }
        return rootView;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnAddRemider = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        btnAddRemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScheduleFragment fragment2 = new AddScheduleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment2);
                fragmentTransaction.commit();
            }
        });


    }

    public void getListItem(){
        arr = dbHelper.getAllCotacts();
        if(arr.size() == 0){
            txtNoReminder.setVisibility(View.VISIBLE);
            return;
        } else {
            txtNoReminder.setVisibility(View.INVISIBLE);
            listItemAdapter = new ListItemAdapter(arr,getActivity(), iSchedulerListener);
            if(listItemAdapter != null){
                itemListView.setAdapter(listItemAdapter);
            }
        }

    }
    public interface ISchedulerListener{
        Context getContext();
    }
}


