package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class TimerActivity extends Activity {

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
