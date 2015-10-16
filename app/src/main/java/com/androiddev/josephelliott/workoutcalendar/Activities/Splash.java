package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/15/2015.
 */
public class Splash extends Activity {

    /**
     * Duration of the wait!*/
    private final int SPLASH_SCREEN_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash.this.finish();
            }
        }, SPLASH_SCREEN_DISPLAY_LENGTH);
    }
}
