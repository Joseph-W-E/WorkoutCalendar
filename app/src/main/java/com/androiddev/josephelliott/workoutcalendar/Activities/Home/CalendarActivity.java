package com.androiddev.josephelliott.workoutcalendar.Activities.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout.CustomWorkoutActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar.CalendarViewPager;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Presets.PresetsActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Timer.TimerActivity;
import com.androiddev.josephelliott.workoutcalendar.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class CalendarActivity extends FragmentActivity {

    /*** Classic, getting the context! ***/
    private Context context;

    /*** Components to be used in this activity ***/
    private CalendarViewPager calendarViewPager;
    private FloatingActionMenu fabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        getActionBar().setDisplayHomeAsUpEnabled(false);

        context = this;

        calendarViewPager = (CalendarViewPager) findViewById(R.id.calendar_activity_custom_view_pager);
        calendarViewPager.initializeAdapter(getSupportFragmentManager(), 1000);
        calendarViewPager.setForwardButton(findViewById(R.id.calendar_activity_image_buttom_go_right));
        calendarViewPager.setBackwardButton(findViewById(R.id.calendar_activity_image_buttom_go_left));
        calendarViewPager.setMonthTextField((TextView) findViewById(R.id.calendar_activity_text_view_month));
        calendarViewPager.setYearTextField((TextView) findViewById(R.id.calendar_activity_text_view_year));

        initializeFABs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        calendarViewPager.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fabMain != null) fabMain.close(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_calendar_go_to_current_date) {
            calendarViewPager.moveToCurrentDate();
            return true;
        } else if (id == R.id.menu_calendar_settings) {
            startActivity(new Intent(context, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_calendar_help) {
            startActivity(new Intent(context, HelpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the Floating Action Buttons.
     * This was taken out of the onCreate method to make things nicer to look at.
     * */
    private void initializeFABs() {
        // Get all the FABs we will be working with

        fabMain = (FloatingActionMenu) findViewById(R.id.calendar_activity_fab_menu);
        FloatingActionButton fabCust = (FloatingActionButton) findViewById(R.id.calendar_activity_fab_go_to_custom_workout_activity);
        FloatingActionButton fabPres = (FloatingActionButton) findViewById(R.id.calendar_activity_fab_go_to_presets_activity);
        FloatingActionButton fabTime = (FloatingActionButton) findViewById(R.id.calendar_activity_fab_go_to_timer_activity);

        // Make the MAIN FAB toggle the visibility of the other buttons.
        // Start the CustomWorkoutActivity
        fabCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CustomWorkoutActivity.class));
            }
        });
        // Start the PresetWorkoutActivity
        fabPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PresetsActivity.class));
            }
        });
        // Start the TimerActivity
        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TimerActivity.class));
            }
        });

    }

}
