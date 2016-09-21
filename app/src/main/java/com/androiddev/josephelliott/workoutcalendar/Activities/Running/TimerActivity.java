package com.androiddev.josephelliott.workoutcalendar.Activities.Running;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class TimerActivity extends Activity {

    // Used to determine if the user is recording the distance
    private boolean isRecordingDistance; // TODO add recording distance feature
    // Holds the different locations they've been to for each ping
    private ArrayList<Location> locations;
    // Holds the amount of time elapsed in milliseconds
    private long timeElapsed;
    // Used to determine if the user has toggled the timer
    private boolean isRunning;
    // Holds the date that they are doing the workout
    private Calendar calendarDatePicked;
    // GUI Object variables
    private Chronometer chronometer;
    private CheckBox checkbox;
    // You never know when you need access to the context
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        try {
            getActionBar().setElevation(0);
            getActionBar().setTitle("Add Your Workout");
        } catch (NullPointerException e) {

        }

        // Initialize our layout objects
        chronometer = (Chronometer) findViewById(R.id.timer_chronometer);
        checkbox = (CheckBox) findViewById(R.id.timer_toggle_running);
        // Initialize our variables
        context = TimerActivity.this;
        isRunning = false;
        isRecordingDistance = false;

        // Get all the usable objects running
        initializeChronometer();
        initializeButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addworkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_addworkout_settings) {
            return true;
        } else if (id == R.id.menu_addworkout_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the on touch listener for the chronometer
     * // TODO Make chronometer also record milliseconds
     * */
    private void initializeChronometer() {
        chronometer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int version = Build.VERSION.SDK_INT;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color to the primary for the 'highlight' feel
                        if (version >= 23) {
                            chronometer.setTextColor(getResources().getColor(R.color.primary, null));
                        } else {
                            chronometer.setTextColor(getResources().getColor(R.color.primary));
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Change the color back to normal
                        if (version >= 23) {
                            chronometer.setTextColor(getResources().getColor(R.color.accent, null));
                        } else {
                            chronometer.setTextColor(getResources().getColor(R.color.accent));
                        }
                        // Toggle the chronometer
                        if (isRunning) {
                            timeElapsed = chronometer.getBase() - SystemClock.elapsedRealtime();
                            chronometer.stop();
                            isRunning = false;
                        } else {
                            chronometer.setBase(SystemClock.elapsedRealtime() + timeElapsed);
                            chronometer.start();
                            isRunning = true;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * Set up the buttons.
     * */
    private void initializeButtons() {
        final ImageButton resetTimer = (ImageButton) findViewById(R.id.timer_reset_timer);
        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeElapsed = 0;
                chronometer.stop();
                chronometer.setText("00:00");
                isRunning = false;
            }
        });

        final ImageButton choosePicture = (ImageButton) findViewById(R.id.timer_change_image);
        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO set up the choose picture functionality LAST
            }
        });

        final ImageButton chooseDate = (ImageButton) findViewById(R.id.timer_change_date);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // They picked a new date! Store it.
                        calendarDatePicked.set(year, monthOfYear, dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        final ImageButton confirm = (ImageButton) findViewById(R.id.timer_save);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO get the data gathered from the activity, send it to the database (maybe dialog confirm?)
                finish();
            }
        });
    }

    private void recordDistance() {

    }

}
