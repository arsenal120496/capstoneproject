package com.example.administrator.app_control.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.app_control.Other.CustomGridView;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName;
    private Toolbar toolbar;
    public static MqttHelper mqttHelper;
    private GridView androidGridView;

    // Set title item in gridview
    String[] gridViewString = {
            "View map",
            "Manual control",
            "Schedule",
            "Power management",
            "View History"

    } ;


    // Set image item in gridview
    int[] gridViewImageId = {
            R.drawable.map, R.drawable.xbox, R.drawable.calendar, R.drawable.battery, R.drawable.medical_history
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set MQTT connection
        mqttHelper = new MqttHelper(getBaseContext(),"tcp://192.168.1.130:1883");


        // Set variable for gridview menu
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);

        // Set adapterview
        CustomGridView adapterViewAndroid = new CustomGridView(MainActivity.this, gridViewString, gridViewImageId);
        androidGridView.setAdapter(adapterViewAndroid);


        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, RobotActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, ScheduleActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, PowerManageActivity.class);
                        startActivity(intent3);
                        break;
                    default: break;
                }
            }
        });



    }


}