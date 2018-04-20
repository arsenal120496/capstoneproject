package com.example.administrator.app_control.Other;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * Created by Khanh Le Tran on 3/29/2018.
 */

public final class MqttHelper{
    public  MqttAndroidClient mqttAndroidClient;

    final int qos = 0;

    final String batteryTopic = "acc/battery";
    final String mapTopic = "acc/map";

    final String clientId = "ExampleAndroidClient";

    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }

    public MqttHelper(Context context, String url){
        mqttAndroidClient = new MqttAndroidClient(context, url, clientId);
        connect();
    }


    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToBatteryTopic();
                    subscribeToMapTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    public void subscribeToBatteryTopic() {
        try {
            mqttAndroidClient.subscribe(batteryTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

    public void subscribeToMapTopic() {
        try {
            mqttAndroidClient.subscribe(mapTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

    public void publishMessage(
                               @NonNull String msg, @NonNull String topic)
            throws MqttException, UnsupportedEncodingException {
        byte[] encodedPayload = new byte[0];
        encodedPayload = msg.getBytes("UTF-8");
        MqttMessage message = new MqttMessage(encodedPayload);
        message.setRetained(false);
        message.setQos(qos);
        mqttAndroidClient.publish(topic, message);
    }



}
