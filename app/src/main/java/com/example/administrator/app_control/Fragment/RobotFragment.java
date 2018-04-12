package com.example.administrator.app_control.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;


public class RobotFragment extends Fragment {

    private Button btnForward;
    private Button btnBackward;
    private Button btnLeft;
    private Button btnRight;
    private Button btnConnect;
    private Button btnDisconnect;
    private EditText txtIP;

    public MqttHelper mqttHelper;
    MqttAndroidClient mqttAndroidClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_robot, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnForward = (Button) getActivity().findViewById(R.id.btnForward);
        btnBackward = (Button) getActivity().findViewById(R.id.btnBackward);
        btnLeft = (Button) getActivity().findViewById(R.id.btnLeft);
        btnRight = (Button) getActivity().findViewById(R.id.btnRight);

        mqttHelper = new MqttHelper(getActivity(),"tcp://192.168.43.89:1883");
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("1", "control");
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
                        mqttHelper.publishMessage("3", "control");
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
                        mqttHelper.publishMessage("4", "control");
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
                        mqttHelper.publishMessage("2", "control");
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
