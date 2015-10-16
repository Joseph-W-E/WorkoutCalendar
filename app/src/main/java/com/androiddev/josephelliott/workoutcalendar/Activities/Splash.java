package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/15/2015.
 */
public class Splash extends Activity {

    /**
     * Duration of the wait!*/
    private final int SPLASH_SCREEN_DISPLAY_LENGTH = 2000;
    protected boolean active = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        try {
            getActionBar().setElevation(0);
        } catch (NullPointerException e) {
            // Do nothing
        }

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < SPLASH_SCREEN_DISPLAY_LENGTH)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {
                    // Do nothing
                } finally {
                    startActivity(new Intent(Splash.this, CalendarActivity.class));
                    finish();
                }
            }
        };
        splashTread.start();
    }
}
