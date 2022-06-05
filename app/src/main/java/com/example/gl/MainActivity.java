package com.example.gl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private PointCloudView pointCloudView;
    //private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointCloudView = findViewById(R.id.pc);
    }


}