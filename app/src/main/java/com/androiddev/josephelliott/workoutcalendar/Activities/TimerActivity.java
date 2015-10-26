package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class TimerActivity extends Activity {

    private boolean isRunning;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        try {
            getActionBar().setElevation(0);
            getActionBar().setTitle("Add Your Workout");
        } catch (NullPointerException e) {
            // TODO
        }

        isRunning = false;

        final Chronometer chronometer = (Chronometer) findViewById(R.id.timer_chronometer);
        chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    time = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                    //chronometer.setTextColor(getResources().getColor(R.color.accent, null));
                    isRunning = false;
                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime() + time);
                    chronometer.start();
                    //chronometer.setTextColor(getResources().getColor(R.color.primary, null));
                    isRunning = true;
                }
            }
        });

        final Button resetTimer = (Button) findViewById(R.id.timer_reset_timer);
        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = 0;
                chronometer.stop();
                chronometer.setText("00:00");
                isRunning = false;
            }
        });

        final Button choosePicture = (Button) findViewById(R.id.timer_change_image);
        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Button chooseDate = (Button) findViewById(R.id.timer_change_date);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Button confirm = (Button) findViewById(R.id.timer_save);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.go_to_current_date) {
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
