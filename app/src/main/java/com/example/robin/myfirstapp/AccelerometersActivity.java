package com.example.robin.myfirstapp;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AccelerometersActivity extends AppCompatActivity implements SensorEventListener {

    private TextView accelerometerX, accelerometerY, accelerometerZ, accelerometerDirection;
    private float x, y, z;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean haveSensor = false;

    private float[] lastAccelerometer = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometers);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerX = (TextView) findViewById(R.id.accelerometerX);
        accelerometerY = (TextView) findViewById(R.id.accelerometerY);
        accelerometerZ = (TextView) findViewById(R.id.accelerometerZ);
        accelerometerDirection = (TextView) findViewById(R.id.accelerometerDirection);

        // We don't want call to start() here since onResume() also have a call to start(). onResume() is run directly after onCreate().
        // Calling start() twice in a row creates an issue when closing the sensor listeners later
        //start();
    }

    public void start() {
        if((sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)) {
            noSensorsAlert();
        } else {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            haveSensor = sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, lastAccelerometer, 0, sensorEvent.values.length);

            x = Math.round(lastAccelerometer[0] * 1000f) / 1000f; // Round to two decimals
            y = Math.round(lastAccelerometer[1] * 1000f) / 1000f;
            z = Math.round(lastAccelerometer[2] * 1000f) / 1000f;

            accelerometerX.setText("X: " + x);
            accelerometerY.setText("Y: " + y);
            accelerometerZ.setText("Z: " + z);

            accelerometerDirection.setText("FLAT");

            boolean textChanged = false;

            if(x > SensorManager.GRAVITY_EARTH * 0.25f) {
                accelerometerDirection.setText("LEFT");
                textChanged = true;
            } else if(x < SensorManager.GRAVITY_EARTH * -0.25f) {
                accelerometerDirection.setText("RIGHT");
                textChanged = true;
            }

            if(y > SensorManager.GRAVITY_EARTH * 0.25f) {
                if(textChanged) {
                    accelerometerDirection.setText(accelerometerDirection.getText() + " & (FRONT) UP");
                } else {
                    accelerometerDirection.setText("(FRONT) UP");
                }
            } else if(y < SensorManager.GRAVITY_EARTH * -0.25f) {
                if(textChanged) {
                    accelerometerDirection.setText(accelerometerDirection.getText() + " & (FRONT) DOWN");
                } else {
                    accelerometerDirection.setText("(FRONT) DOWN");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not used
    }

    public void stop() {
        if(haveSensor){
            sensorManager.unregisterListener(this, accelerometer);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

}
