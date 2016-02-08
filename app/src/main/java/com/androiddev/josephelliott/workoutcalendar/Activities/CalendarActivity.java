package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;
import com.androiddev.josephelliott.workoutcalendar.R;
import com.androiddev.josephelliott.workoutcalendar.Utility.ScreenSlidePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends FragmentActivity {

    /**
     * Number of buffer pages for the view pager
     */
    private final int NUM_PAGES = 1000;
    /**
     * Components to be used in this activity.
     */
    private ViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ImageButton btnNextMonth;
    private ImageButton btnPrevMonth;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabCust;
    private FloatingActionButton fabPres;
    private FloatingActionButton fabTime;
    /**
     * Variables to be manipulated through the activity's lifetime.
     */
    private CurrentCalendarData calendarData;
    private int vpIndex = NUM_PAGES / 2;
    /**
     * Other variables
     */
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getActionBar().setElevation(0);
            getActionBar().setDisplayHomeAsUpEnabled(false);
            //getActionBar().setTitle("Workout Calendar");
        } catch (NullPointerException e) {
            // Do nothing
        }

        // Get the calendar data right away!! We need this for the pager adapter
        calendarData = new CurrentCalendarData();
        context = this.getApplicationContext();

        // Initialize all components in this activity
        btnNextMonth = (ImageButton) findViewById(R.id.image_button_right);
        btnPrevMonth = (ImageButton) findViewById(R.id.image_button_left);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), NUM_PAGES, calendarData.getDateObj().getTime());
        mViewPager.setAdapter(mPagerAdapter);

        // Set up the activity data
        moveViewPagerToMonth();
        setMonthAndYearTextFields(calendarData.getDateObj());

        // Add all component listeners
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // int position is the index of the new selected page
                if (position > vpIndex) {
                    // Then we scrolled to the right
                    calendarData.addMonth();
                    vpIndex++;
                    setMonthAndYearTextFields(calendarData.getDateObj());
                } else if (position < vpIndex) {
                    // Then we scrolled to the left
                    calendarData.subtractMonth();
                    vpIndex--;
                    setMonthAndYearTextFields(calendarData.getDateObj());
                }
            }
        });

        initializeImageButtons();
        initializeFABs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Refresh the views inside the fragments for left, center, and right
        if (mPagerAdapter.getFragment(vpIndex - 1) != null) {
            mPagerAdapter.getFragment(vpIndex - 1).refresh();
        }
        if (mPagerAdapter.getFragment(vpIndex) != null) {
            mPagerAdapter.getFragment(vpIndex).refresh();
        }
        if (mPagerAdapter.getFragment(vpIndex + 1) != null) {
            mPagerAdapter.getFragment(vpIndex + 1).refresh();
        }
        // TODO Make the FABs back to default

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
            calendarData.setTodaysDate();
            vpIndex = NUM_PAGES / 2;
            moveViewPagerToMonth();
            setMonthAndYearTextFields(calendarData.getDateObj());
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_help) {
            Intent intent = new Intent(context, HelpActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts the Add Workout Activity
     */
    public void goToAddWorkoutActivity(View view) {
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }

    /**
     * This method updates the Month and Year text fields.
     * Call this method when there is a change to currentMonth
     */
    private void setMonthAndYearTextFields(Date date) {
        // Set the current month
        TextView monthText = (TextView) findViewById(R.id.text_view_month);
        SimpleDateFormat monthTextSDF = new SimpleDateFormat("MMMM");
        monthText.setText(monthTextSDF.format(date));
        // Set the current year
        TextView yearText = (TextView) findViewById(R.id.text_view_year);
        SimpleDateFormat yearTextSDF = new SimpleDateFormat("yyyy");
        yearText.setText(yearTextSDF.format(date));
    }

    /**
     * Moves the viewpager to the currentViewPageIndex.
     * The viewpager performs a 'smooth scroll'.
     * Call this AFTER setPrevMonth/setNextMonth.
     */
    private void moveViewPagerToMonth() {
        // Go to the current view pager index
        mViewPager.setCurrentItem(vpIndex, true);
    }

    /**
     * Initializes the Image Buttons (the arrows).
     * this was taken out of the onCreate method ot make things nicer to look at.
     * */
    private void initializeImageButtons() {
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We are moving to the right
                calendarData.addMonth();
                vpIndex++;
                moveViewPagerToMonth();
                setMonthAndYearTextFields(calendarData.getDateObj());
            }
        });
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We are moving to the left
                calendarData.subtractMonth();
                vpIndex--;
                moveViewPagerToMonth();
                setMonthAndYearTextFields(calendarData.getDateObj());
            }
        });
    }

    /**
     * Initializes the Floating Action Buttons.
     * This was taken out of the onCreate method to make things nicer to look at.
     * // TODO Add animations to the FABs so that they slide in and out of visibility
     * */
    private void initializeFABs() {
        // Get all the FABs we will be working with
        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabCust = (FloatingActionButton) findViewById(R.id.fab_custom_workout);
        fabPres = (FloatingActionButton) findViewById(R.id.fab_preset_workout);
        fabTime = (FloatingActionButton) findViewById(R.id.fab_timer);
        // Set all but MAIN to invisible (we will do animations later)
        fabCust.setVisibility(View.INVISIBLE);
        fabPres.setVisibility(View.INVISIBLE);
        fabTime.setVisibility(View.INVISIBLE);

        // Make the MAIN FAB toggle the visibility of the other buttons.
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabCust.getVisibility() == View.VISIBLE) {
                    fabCust.setVisibility(View.INVISIBLE);
                } else {
                    fabCust.setVisibility(View.VISIBLE);
                }
                if (fabPres.getVisibility() == View.VISIBLE) {
                    fabPres.setVisibility(View.INVISIBLE);
                } else {
                    fabPres.setVisibility(View.VISIBLE);
                }
                if (fabTime.getVisibility() == View.VISIBLE) {
                    fabTime.setVisibility(View.INVISIBLE);
                } else {
                    fabTime.setVisibility(View.VISIBLE);
                }
                fabMain.setRotation(fabMain.getRotation() + 45);
            }
        });
        // Start the AddWorkoutActivity
        fabCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddWorkoutActivity.class);
                startActivity(intent);
            }
        });
        // Start the PresetWorkoutActivity
        fabPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Start the TimerActivity
        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TimerActivity.class);
                startActivity(intent);
            }
        });
    }

}
