package com.example.administrator.app_control.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.MainActivity;
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
    private Button btnTurnOffFan;
    private Button btnTurnOnFan;

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
        btnTurnOnFan = (Button) getActivity().findViewById(R.id.btnTurnOnFan);
        btnTurnOffFan = (Button) getActivity().findViewById(R.id.btnTurnOffFan);


        mqttHelper = MainActivity.mqttHelper;

        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnForward.setBackgroundColor(Color.GREEN);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("1", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnForward.setBackgroundColor(Color.TRANSPARENT);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("0", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

        });

        btnBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnBackward.setBackgroundColor(Color.GREEN);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("3", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnBackward.setBackgroundColor(android.R.drawable.btn_default);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("0", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                }
                return true;
            }

        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnLeft.setBackgroundColor(Color.GREEN);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("4", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnLeft.setBackgroundColor(android.R.drawable.btn_default);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("0", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                }
                return true;
            }

        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnRight.setBackgroundColor(Color.GREEN);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("2", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnRight.setBackgroundColor(android.R.drawable.btn_default);
                    try {
                        if(mqttHelper != null) {
                            mqttHelper.publishMessage("0", "acc/control");
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                }
                return true;
            }

        });

        btnTurnOnFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("5", "acc/control");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        btnTurnOffFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mqttHelper != null) {
                        mqttHelper.publishMessage("6", "acc/control");
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
