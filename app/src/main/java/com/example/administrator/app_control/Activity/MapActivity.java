package com.example.administrator.app_control.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> listMapAdater;
    private FloatingActionButton mapfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        listView = (ListView) findViewById(R.id.maplistview);
        mapfab = (FloatingActionButton) findViewById(R.id.mapfab);

        ArrayList<String> listItems=new ArrayList<String>();
        listItems.add("Room A");
        listItems.add("Room B");
        listItems.add("Room C");
        listMapAdater = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(listMapAdater);

        mapfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this,RobotActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listMapAdater.notifyDataSetChanged();
    }
}
