package com.stuin.Ten_Seconds_To_Symmetry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.Chronometer;

public class Game extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();
    }
}