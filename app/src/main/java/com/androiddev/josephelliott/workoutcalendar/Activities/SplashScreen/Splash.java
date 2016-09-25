package com.androiddev.josephelliott.workoutcalendar.Activities.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androiddev.josephelliott.workoutcalendar.Activities.Home.CalendarActivity;
import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/15/2015.
 */
public class Splash extends Activity {

    /**
     * Components*/
    private Button btnUseLocal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        btnUseLocal = (Button) findViewById(R.id.splash_btn_local_storage);

        try {
            getActionBar().setElevation(0);
        } catch (NullPointerException e) {
            // Do nothing
        }

        btnUseLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, CalendarActivity.class));
                finish();
            }
        });

    }
}
