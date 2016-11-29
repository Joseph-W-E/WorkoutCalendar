package com.androiddev.josephelliott.workoutcalendar.Activities.Timer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.Database.Workout;
import com.androiddev.josephelliott.workoutcalendar.Database.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class TimerActivity extends Activity {

    /*** Constants ***/
    private final int REQUEST_CODE_USER_LOCATION = 7;

    /*** Always needs the activity context ***/
    private Context context;

    /*** The date that the user selected for this workout ***/
    private Calendar calendarDatePicked;

    /*** The LocationRelay, used to gather the user's location ***/
    private LocationRelay locationRelay;
    private boolean isRecordingDistance;

    /*** Variables needed for recording the workout ***/
    private Workout workout;

    /*** Variables for views ***/
    private ImageButton btnTimer, btnImage, btnDate, btnConfirm;
    private CustomChronometer chronometer;
    private CheckBox checkbox;
    private EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        /*** Get the context ***/
        context = this;

        /*** Get the current time ***/
        calendarDatePicked = Calendar.getInstance();

        /*** Setup the LocationRelay ***/
        locationRelay = new LocationRelay(context);
        isRecordingDistance = false;

        /*** Get all the used views ***/
        btnTimer   = (ImageButton) findViewById(R.id.timer_btn_reset_timer);
        btnImage   = (ImageButton) findViewById(R.id.timer_btn_change_image);
        btnDate    = (ImageButton) findViewById(R.id.timer_btn_change_date);
        btnConfirm = (ImageButton) findViewById(R.id.timer_btn_save);
        chronometer = (CustomChronometer) findViewById(R.id.timer_chronometer);
        checkbox = (CheckBox) findViewById(R.id.timer_check_box_record_distance);
        etTitle  = (EditText) findViewById(R.id.timer_et_title);

        /*** Initialize needed variables ready ***/
        workout = new Workout();

        /*** Set the logic for the views ***/
        initializeViews();
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
     * Set up the buttons.
     * */
    private void initializeViews() {
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.reset();
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
                workout.setTitle(etTitle.getText().toString());
                workout.setLocation(locationRelay.estimateRunningLocation());
                workout.setDescription(String.format("%d seconds", chronometer.getTimeElapsed() / 1000));
                workout.setDate(calendarDatePicked.getTime());
                workout.setDistance(locationRelay.calculateDistance());
                workout.setImage(null);

                WorkoutDataSource dataSource = new WorkoutDataSource(context);
                dataSource.open();
                dataSource.createWorkout(workout);
                dataSource.close();

                finish();
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecordingDistance) {
                    requestPermission();
                    isRecordingDistance = true;
                } else {
                    removeChronometerUpdateListener();
                    isRecordingDistance = false;
                }
            }
        });

        chronometer.setUpdateInterval(5 * 1000);
        chronometer.setStartListener(new CustomChronometer.OnStartListener() {
            @Override
            public void onStart() {
                checkbox.setEnabled(false);
                etTitle.setEnabled(false);
                btnDate.setEnabled(false);
                btnConfirm.setEnabled(false);
                btnImage.setEnabled(false);
            }
        });
        chronometer.setPauseListener(new CustomChronometer.OnPauseListener() {
            @Override
            public void onPause() {
                checkbox.setEnabled(true);
                etTitle.setEnabled(true);
                btnDate.setEnabled(true);
                btnConfirm.setEnabled(true);
                btnImage.setEnabled(true);
            }
        });
    }

    private void setChronometerUpdateListener() {
        chronometer.setOnIntervalUpdate(new CustomChronometer.OnIntervalUpdateListener() {
            @Override
            public void onIntervalUpdate() {
                locationRelay.poll();
            }
        });
    }

    private void removeChronometerUpdateListener() {
        chronometer.setOnIntervalUpdate(new CustomChronometer.OnIntervalUpdateListener() {
            @Override
            public void onIntervalUpdate() {}
        });
    }



    /*** Handle user permissions ***/

    private void requestPermission() {
        int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {

            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionRational("You need to enable location permission to record where you run.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_USER_LOCATION);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_USER_LOCATION);
            return;
        }

        setChronometerUpdateListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_USER_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                } else {
                    // Permission denied
                    Toast.makeText(this, "ACCESS LOCATION permission denied :(", Toast.LENGTH_SHORT).show();
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showPermissionRational(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }

}
