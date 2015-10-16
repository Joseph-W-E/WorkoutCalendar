package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.ViewGroup;

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
        try {
            getActionBar().setElevation(0);
            getActionBar().setTitle("");
        } catch (NullPointerException e) {
            // TODO
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.main_rl_splash), new Fade());
                Splash.this.finish();
            }
        }, SPLASH_SCREEN_DISPLAY_LENGTH);
    }
}
