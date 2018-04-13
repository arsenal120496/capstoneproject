package com.example.administrator.app_control.Other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Fragment.ScheduleFragment;

import java.util.List;

public class ListItemAdapter extends BaseAdapter {


    private List<Item> listItem;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView txtID;
    private Switch aSwitch;
    ScheduleFragment.ISchedulerListener iSchedulerListener;

    public ListItemAdapter(List<Item> listItem, Context context, ScheduleFragment.ISchedulerListener iSchedulerListener) {
        this.listItem = listItem;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.iSchedulerListener = iSchedulerListener;
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
        return Integer.parseInt(listItem.get(position).getID());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtDes;
        TextView txtTime;
        TextView txtRepeatype;
        Switch switchohpeat;
        TextView txtID;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
        }
        txtDes = (TextView) convertView.findViewById(R.id.recycle_title);
        txtTime = (TextView) convertView.findViewById(R.id.recycle_date_time);
        txtRepeatype = (TextView) convertView.findViewById(R.id.recycle_repeat_info);
        txtID = (TextView) convertView.findViewById(R.id.recycle_id);
        aSwitch = (Switch) convertView.findViewById(R.id.btnActive_switch);
        Item item = getItem(position);
        if(item.getIsActive().equals("1")){
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                iSchedulerListener.onToggle();
            }
        });

        txtDes.setText(item.getDescription());
        txtTime.setText(item.getTime());
        String repeatDes = "";
        String repeatype = item.getRepeatDes();
        txtID.setText(item.getID());
        if(item.getIsRepeat().equals("0")){
            repeatDes +="No repeat";
        } else if(item.getIsRepeat().equals("1")) {
            char b = '1';
            if(repeatype.charAt(0) == b){
                repeatDes += "T2 ";
            }
            if(repeatype.charAt(1) == b){
                repeatDes += "T3 ";
            }
            if(repeatype.charAt(2) == b){
                repeatDes += "T5 ";
            }
            if(repeatype.charAt(3) == b){
                repeatDes += "T5 ";
            }
            if(repeatype.charAt(4) == b){
                repeatDes += "T6 ";
            }
            if(repeatype.charAt(5) == b){
                repeatDes += "T7 ";
            }
            if(repeatype.charAt(6) == b){
                repeatDes += "CN ";
            }
        }
        txtRepeatype.setText(repeatDes);

        return convertView;
    }

}

