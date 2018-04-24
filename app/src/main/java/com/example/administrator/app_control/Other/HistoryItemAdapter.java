package com.example.administrator.app_control.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.app_control.Activity.R;

import java.util.List;

public class HistoryItemAdapter extends BaseAdapter {

    private List<HistoryItem> listItem;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView txtID;
    private Context ctx;

    public HistoryItemAdapter(List<HistoryItem> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public HistoryItem getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txtDes;
        TextView txtTime;
        TextView txtMap;
        TextView txtID;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.historyitem, viewGroup, false);
        }
        txtDes = (TextView) view.findViewById(R.id.history_recycle_repeat_info);
        txtTime = (TextView) view.findViewById(R.id.history_recycle_date_time);
        txtMap = (TextView) view.findViewById(R.id.history_recycle_title);
        txtID = (TextView) view.findViewById(R.id.history_recycle_id);


        final HistoryItem item = getItem(i);
        txtDes.setText(item.getArea());
        txtMap.setText(item.getName());
        txtTime.setText(item.getTime());
        return view;
    }
}
