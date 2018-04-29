package com.example.administrator.app_control.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.app_control.Other.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class RobotActivity extends AppCompatActivity {

    private Button btnForward;
    private Button btnBackward;
    private Button btnLeft;
    private Button btnRight;

    public MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);


        // Set variable for each component
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBackward = (Button) findViewById(R.id.btnBackward);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);

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


    }

}

