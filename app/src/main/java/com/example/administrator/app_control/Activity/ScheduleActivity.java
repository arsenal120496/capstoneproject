package com.example.administrator.app_control.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.app_control.Other.AlarmReminderDbHelper;
import com.example.administrator.app_control.Other.Item;
import com.example.administrator.app_control.Other.ListItemAdapter;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dbHelper = new AlarmReminderDbHelper(getBaseContext());
        itemListView = (ListView) findViewById(R.id.listview);
        txtNoReminder = (TextView) findViewById(R.id.no_reminder_text);
        btnAddRemider = (FloatingActionButton) findViewById(R.id.fab);

        mqttHelper = MainActivity.mqttHelper;

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ScheduleActivity.this,EditScheduleActivity.class);
                intent.putExtra("name",arr.get((int)l).getDescription());
                intent.putExtra("isRepeat",arr.get((int)l).getIsRepeat());
                intent.putExtra("time",arr.get((int)l).getTime());
                intent.putExtra("id",arr.get((int)l).getID());
                intent.putExtra("isActive",arr.get((int)l).getIsActive());
                intent.putExtra("repeatType",arr.get((int)l).getRepeatDes());
                startActivity(intent);
            }
        });

        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
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
                        Intent i = new Intent(ScheduleActivity.this,ScheduleActivity.class);
                        startActivity(i);
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
        listItemAdapter = new ListItemAdapter(arr,ScheduleActivity.this);
        if(listItemAdapter != null){
            itemListView.setAdapter(listItemAdapter);
        }


        btnAddRemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this,AddScheduleActivity.class);
                startActivity(i);
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
            listItemAdapter = new ListItemAdapter(arr,ScheduleActivity.this);
            if(listItemAdapter != null){
                itemListView.setAdapter(listItemAdapter);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        listItemAdapter.notifyDataSetChanged();
    }
}
