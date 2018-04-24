package com.example.administrator.app_control.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.app_control.Other.HistoryItem;
import com.example.administrator.app_control.Other.HistoryItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryActivity extends AppCompatActivity {

    private HistoryItemAdapter historyItemAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        listView = (ListView) findViewById(R.id.historylistview);

        List<HistoryItem> historyItemList = new ArrayList<HistoryItem>();
        HistoryItem item = new HistoryItem("Room A" , "19/04/2018 14:22","60%");
        historyItemList.add(item);

        historyItemAdapter = new HistoryItemAdapter(historyItemList, ViewHistoryActivity.this);
        listView.setAdapter(historyItemAdapter);
    }
}
