package com.example.administrator.app_control.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.MainActivity;
import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class HomeFragment extends Fragment {

    final static String topic = "acc/battery";

    private TextView textView;
    private ProgressBar progressBar;
    private MqttHelper mqttHelper;
    private Handler mHandler;
    private MqttAndroidClient mqttAndroidClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_home2,parent,false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        textView = (TextView) rootView.findViewById(R.id.txtMessage);
        progressBar.setScaleY(8);
        mqttHelper = MainActivity.mqttHelper;
        mqttAndroidClient = mqttHelper.getMqttAndroidClient();
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
        //MainActivity.mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if(reconnect){
                    mqttHelper.subscribeToTopic();
                }

            }
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
               textView.setText(new String(message.getPayload()) + "%");
               progressBar.setProgress(Integer.parseInt(new String(message.getPayload())));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        return rootView;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
