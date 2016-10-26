package com.androiddev.josephelliott.workoutcalendar.Activities.SplashScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Home.CalendarActivity;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.Random;

/**
 * Created by Joseph Elliott on 10/15/2015.
 */
public class Splash extends Activity {

    /*** Context ***/
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Get the activity context
        context = this;

        // Update the display (put the buttons on the screen)
        updateDisplay();

        // Load the background image
        loadBackgroundImage();
    }


    /*** Loads the background image ***/
    private void loadBackgroundImage() {
        ImageView ivBackground = (ImageView) findViewById(R.id.splash_iv_background_image);
        SplashBackgroundImageLoader splashBackgroundImageLoader = new SplashBackgroundImageLoader(ivBackground, getResources());

        int[] resIDs = {R.drawable.running_img, R.drawable.weight_lifting_img, R.drawable.yoga_img};
        Random rand = new Random();

        splashBackgroundImageLoader.execute(resIDs[rand.nextInt(resIDs.length)]);
    }


    /*** Update the display ***/
    private void updateDisplay() {
        getActionBar().hide();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.splash_fl_main);
                View child = getLayoutInflater().inflate(R.layout.splash_content, frameLayout);
                initializeButtons(child);
            }
        });
        t.start();
    }

    /*** Initialize Views For Child Layout ***/
    private void initializeButtons(View view) {
        // Local Storage
        Button btnLocalStorage = (Button) view.findViewById(R.id.splash_btn_local_storage);
        btnLocalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this, CalendarActivity.class));
                finish();
            }
        });

        // Server Log In
        Button btnLogIn = (Button) view.findViewById(R.id.splash_btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry, this button doesn't work yet :/", Toast.LENGTH_SHORT).show();
            }
        });

        // Server Sign Up
        Button btnSignUp = (Button) view.findViewById(R.id.splash_btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry, this button doesn't work yet :/", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
