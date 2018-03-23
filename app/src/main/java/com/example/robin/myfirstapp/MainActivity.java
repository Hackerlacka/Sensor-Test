package com.example.robin.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        // TODO Send optional parameters
        startActivity(intent);
    }

    public void startAccelerometers(View view) {
        Intent intent = new Intent(this, AccelerometersActivity.class);
        startActivity(intent);
    }
}
