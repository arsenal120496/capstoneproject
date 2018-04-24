package com.example.administrator.app_control.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PowerManageActivity extends AppCompatActivity {

//    private TextView textView , txtTimeRemaining;
//    private ProgressBar progressBar;
//    private MqttHelper mqttHelper;
//    private MqttAndroidClient mqttAndroidClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_manage);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        textView = (TextView) findViewById(R.id.txtMessage);
//        txtTimeRemaining = (TextView) findViewById(R.id.txtTimeRemaining);

//        mqttHelper = MainActivity.mqttHelper;
//        mqttAndroidClient = mqttHelper.getMqttAndroidClient();
//        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
//            //MainActivity.mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectComplete(boolean reconnect, String serverURI) {
//                if(reconnect){
//                    mqttHelper.subscribeToBatteryTopic();
//
//                }
//
//            }
//            @Override
//            public void connectionLost(Throwable cause) {
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//
//                textView.setText(new String(message.getPayload()) + "%");
//                progressBar.setProgress(Integer.parseInt(new String(message.getPayload())));
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//            }
//        });
    }
}
