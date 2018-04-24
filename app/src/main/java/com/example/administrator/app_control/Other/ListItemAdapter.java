package com.example.administrator.app_control.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.example.administrator.app_control.Activity.MainActivity;
import com.example.administrator.app_control.Activity.R;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ListItemAdapter extends BaseAdapter {


    private List<Item> listItem;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView txtID;
    private Switch aSwitch;
    private MqttHelper mqttHelper;
    private AlarmReminderDbHelper alarmReminderDbHelper;



    public ListItemAdapter(List<Item> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Item getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listItem.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mqttHelper = MainActivity.mqttHelper;

        TextView txtDes;
        TextView txtTime;
        TextView txtRepeatype;
        final TextView txtID;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
        }
        txtDes = (TextView) convertView.findViewById(R.id.recycle_title);
        txtTime = (TextView) convertView.findViewById(R.id.recycle_date_time);
        txtRepeatype = (TextView) convertView.findViewById(R.id.recycle_repeat_info);
        txtID = (TextView) convertView.findViewById(R.id.recycle_id);
        aSwitch = (Switch) convertView.findViewById(R.id.btnActive_switch);
        final Item item = getItem(position);
        txtDes.setText(item.getDescription());
        txtTime.setText(item.getTime());
        String repeatDes = "";
        String repeatype = item.getRepeatDes();
        txtID.setText(item.getID()+"");



        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                alarmReminderDbHelper = new AlarmReminderDbHelper(context);
                if (b) {
                    Item tmp = new Item();
                    tmp.setID(item.getID());
                    tmp.setDescription(item.getDescription());
                    tmp.setTime(item.getTime());
                    tmp.setRepeatDes(item.getRepeatDes());
                    tmp.setIsActive(1);

                    alarmReminderDbHelper.updateData(tmp);
                    try {
                        if (mqttHelper != null) {
                            mqttHelper.publishMessage("3" + item.getID() + "1", "acc/schedule");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Item tmp = new Item();
                    tmp.setID(item.getID());
                    tmp.setDescription(item.getDescription());
                    tmp.setTime(item.getTime());
                    tmp.setRepeatDes(item.getRepeatDes());
                    tmp.setIsActive(0);

                    alarmReminderDbHelper.updateData(tmp);
                    try {
                        if (mqttHelper != null) {
                            mqttHelper.publishMessage("3" + "0" + "0", "acc/schedule");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        char b = '1';
        if (repeatype.charAt(0) == b) {
            repeatDes += "Mon ";
        }
        if (repeatype.charAt(1) == b) {
            repeatDes += "Tue ";
        }
        if (repeatype.charAt(2) == b) {
            repeatDes += "Wed ";
        }
        if (repeatype.charAt(3) == b) {
            repeatDes += "Thu ";
        }
        if (repeatype.charAt(4) == b) {
            repeatDes += "Fri ";
        }
        if (repeatype.charAt(5) == b) {
            repeatDes += "Sat ";
        }
        if (repeatype.charAt(6) == b) {
            repeatDes += "Sun";
        }

        txtRepeatype.setText(repeatDes);

        return convertView;
    }
}