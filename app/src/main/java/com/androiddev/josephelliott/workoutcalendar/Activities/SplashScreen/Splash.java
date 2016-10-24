package com.androiddev.josephelliott.workoutcalendar.Activities.SplashScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Home.CalendarActivity;
import com.androiddev.josephelliott.workoutcalendar.R;

/**
 * Created by Joseph Elliott on 10/15/2015.
 */
public class Splash extends Activity {

    /*** Context ***/
    private Context context;

    /*** Views ***/
    private Button btnLocalStorage;
    private Button btnLogIn;
    private Button btnSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        context = this;

        try {
            getActionBar().setElevation(0);
        } catch (NullPointerException e) {
            // Do nothing
        }

        initializeLocalStorageButton();
        initializeLogInButton();
        initializeSignUpButton();
    }

    private void initializeLocalStorageButton() {
        btnLocalStorage = (Button) findViewById(R.id.splash_btn_local_storage);

        btnLocalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this, CalendarActivity.class));
                finish();
            }
        });
    }

    private void initializeLogInButton() {
        btnLogIn = (Button) findViewById(R.id.splash_btn_log_in);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry, this button doesn't work yet :/", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeSignUpButton() {
        btnSignUp = (Button) findViewById(R.id.splash_btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry, this button doesn't work yet :/", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
