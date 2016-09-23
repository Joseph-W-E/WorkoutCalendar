package com.androiddev.josephelliott.workoutcalendar.Activities.Help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/25/2015.
 */
public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initializeTextViews();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeTextViews() {
        final TextView txtMain = (TextView) findViewById(R.id.help_sub_main);
        txtMain.setText(
                "This grid is at your disposal! This is where all of your information is stored.\n" +
                        "To see a previous workout, just tap on the highlighted cells.\n" +
                        "This will open a pop-up where you can either edit or delete the workout.\n" +
                        "Easy peasy."
        );
        final TextView txtCust = (TextView) findViewById(R.id.help_sub_custom_workout);
        txtCust.setText(
                "In this area, you can create your own custom workout!\n" +
                        "You can either type it yourself, or use the available text boxes to help " +
                        "you out. Try one way or the other to see which one you like most.\n" +
                        "Once you have a workout, just hit the check mark in the bottom right corner."
        );
        final TextView txtPres = (TextView) findViewById(R.id.help_sub_preset_workout);
        txtPres.setText(
                "This is for those wonderful favorite workouts. Here you can record workouts for " +
                        "later use."
        );
        final TextView txtTime = (TextView) findViewById(R.id.help_sub_timed_workout);
        txtTime.setText(
                "This timer is a simple way to keep track of timed activities.\n" +
                        "Going for a run? No problem. Hit that record distance button, " +
                        "start the timer, and take off. It's as easy as that to get started.\n" +
                        "As a word of caution: the app must remain open, and to save the workout, " +
                        "you must hit that check mark at the bottom of the screen!"
        );
    }

}
