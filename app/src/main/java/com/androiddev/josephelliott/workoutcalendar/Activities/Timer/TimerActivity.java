package com.androiddev.josephelliott.workoutcalendar.Activities.Timer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.Database.Workout;
import com.androiddev.josephelliott.workoutcalendar.Database.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class TimerActivity extends Activity {

    private Context context;

    private Calendar calendarDatePicked;

    /*** Variables needed for recording distance ***/
    private boolean isRecordingDistance;
    private ArrayList<Location> locations;

    /*** Variables needed for recording time ***/
    private boolean isRunning;
    private long timeElapsed;

    /*** Variables needed for recording the workout ***/
    private Image image;
    private Workout workout;

    /*** Variables for views ***/
    private ImageButton btnTimer, btnImage, btnDate, btnConfirm;
    private Chronometer chronometer;
    private CheckBox checkbox;
    private EditText etTitle, etDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        /*** Get the context ***/
        context = this;

        /*** Get the current time ***/
        calendarDatePicked = Calendar.getInstance();

        /*** Get all the used views ***/
        btnTimer   = (ImageButton) findViewById(R.id.timer_btn_reset_timer);
        btnImage   = (ImageButton) findViewById(R.id.timer_btn_change_image);
        btnDate    = (ImageButton) findViewById(R.id.timer_btn_change_date);
        btnConfirm = (ImageButton) findViewById(R.id.timer_btn_save);
        chronometer = (Chronometer) findViewById(R.id.timer_chronometer);
        checkbox = (CheckBox) findViewById(R.id.timer_check_box_record_distance);
        etTitle  = (EditText) findViewById(R.id.timer_et_title);
        etDesc   = (EditText) findViewById(R.id.timer_et_description);

        /*** Initialize needed variables ready ***/
        isRunning = false;
        isRecordingDistance = false;
        workout = new Workout();

        /*** Set the logic for the views ***/
        initializeChronometer();
        initializeButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_default_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_default_help) {
            startActivity(new Intent(this, HelpActivity.class));
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
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color to the primary for the 'highlight' feel
                        chronometer.setTextColor(getResources().getColor(R.color.primary, null));
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Change the color back to normal
                        chronometer.setTextColor(getResources().getColor(R.color.accent, null));
                        if (isRunning) {
                            // If we were running, record the elapsed time and calculate distance.
                            timeElapsed = chronometer.getBase() - SystemClock.elapsedRealtime();
                            chronometer.stop();
                            isRunning = false;
                        } else {
                            // If we start running, set the start time and start getting locations.
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
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeElapsed = 0;
                chronometer.stop();
                chronometer.setText("00:00");
                isRunning = false;
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO set up the choose picture functionality LAST
                Toast.makeText(context, "Sorry, this button doesn't work yet :/", Toast.LENGTH_SHORT).show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title
                workout.setTitle(etTitle.getText().toString());
                // Get the location
                workout.setLocation("");
                // Get the description
                workout.setDescription(etDesc.getText().toString());
                // Get the date
                workout.setDate(calendarDatePicked.getTime());
                // Get the distance
                workout.setDistance(0);
                // Get the image
                workout.setImage(image);

                // Now that we have a complete workout, save it to the database
                WorkoutDataSource dataSource = new WorkoutDataSource(context);
                dataSource.open();
                dataSource.createWorkout(workout);
                dataSource.close();

                // rip in pieces
                finish();
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecordingDistance) {
                    isRecordingDistance = true;
                    //startGatheringLocations();
                } else {
                    isRecordingDistance = false;
                    //stopGatheringLocations();
                }
            }
        });
    }

}
