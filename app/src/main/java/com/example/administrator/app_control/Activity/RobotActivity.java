package com.example.administrator.app_control.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class RobotActivity extends AppCompatActivity {

    private Button btnForward;
    private Button btnBackward;
    private Button btnLeft;
    private Button btnRight;
    private Button btnConnect;
    private Button btnDisconnect;
    private EditText txtIP;

    private MqttHelper mqttHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBackward = (Button) findViewById(R.id.btnBackward);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        txtIP = (EditText) findViewById(R.id.txtIP);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper = new MqttHelper(getApplicationContext(),txtIP.getText().toString());
                btnDisconnect.setVisibility(View.VISIBLE);
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper!=null && mqttHelper.getMqttAndroidClient() != null ) {
                        btnDisconnect.setVisibility(View.INVISIBLE);
                        mqttHelper.getMqttAndroidClient().disconnect();
                        mqttHelper = null;
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("forward", "control");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("backward", "control");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("left", "control");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("right", "control");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
