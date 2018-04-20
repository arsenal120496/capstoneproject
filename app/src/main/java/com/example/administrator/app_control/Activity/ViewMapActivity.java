package com.example.administrator.app_control.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ViewMapActivity extends AppCompatActivity {

    private MqttHelper mqttHelper;
    private MqttAndroidClient mqttAndroidClient;
    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        myImage = (ImageView) findViewById(R.id.imageViewMap);

        mqttHelper = MainActivity.mqttHelper;
        mqttAndroidClient = mqttHelper.getMqttAndroidClient();
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            //MainActivity.mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if(reconnect){
                    //mqttHelper.subscribeToBatteryTopic();
                    mqttHelper.subscribeToMapTopic();
                }

            }
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
//               textView.setText(new String(message.getPayload()) + "%");
//               progressBar.setProgress(Integer.parseInt(new String(message.getPayload())));

                Bitmap bitmap = BitmapFactory.decodeByteArray(message.getPayload() , 0, message.getPayload().length);
                myImage.setImageBitmap(bitmap);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
