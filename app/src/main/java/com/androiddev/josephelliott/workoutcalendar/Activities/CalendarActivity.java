package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;
import com.androiddev.josephelliott.workoutcalendar.R;
import com.androiddev.josephelliott.workoutcalendar.Utility.ScreenSlidePagerAdapter;
import com.androiddev.josephelliott.workoutcalendar.Utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends FragmentActivity {

    /**
    * Number of buffer pages for the view pager*/
    private final int NUM_PAGES = 1000;
    /**
    * Components to be used in this activity.*/
    private ViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ImageButton btnNextMonth;
    private ImageButton btnPrevMonth;
    /**
    * Variables to be manipulated through the activity's lifetime.*/
    private CurrentCalendarData calendarData;
    private CalendarFragment primaryFragment;
    private int vpIndex = NUM_PAGES / 2;
    /**
     * Other variables*/

    /**
    * * Activity Startup:
    * * Get today's date
    * * Initialize all components in this activity
    * * Move the ViewPager to today's month
    * * Set listeners*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Splash.class);
        this.startActivity(intent);

        // Get the calendar data right away!! We need this for the pager adapter
        calendarData = new CurrentCalendarData();

        // Initialize all components in this activity
        btnNextMonth = (ImageButton) findViewById(R.id.image_button_right);
        btnPrevMonth = (ImageButton) findViewById(R.id.image_button_left);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), NUM_PAGES);
        mViewPager.setAdapter(mPagerAdapter);

        // Set up the activity data
        moveViewPagerToMonth();
        setMonthAndYearTextFields(calendarData.getDateObj());

        // This is required to get the first month to update!!!
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                primaryFragment = mPagerAdapter.getFragment(vpIndex);
                updateFragment();
            }
        });

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
                    primaryFragment = mPagerAdapter.getFragment(vpIndex);
                    updateFragment();
                    // Update the fragment that is off the screen to the right

                } else if (position < vpIndex) {
                    // Then we scrolled to the left
                    calendarData.subtractMonth();
                    vpIndex--;
                    setMonthAndYearTextFields(calendarData.getDateObj());
                    primaryFragment = mPagerAdapter.getFragment(vpIndex);
                    updateFragment();
                }
            }
        });
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We are moving to the right
                calendarData.addMonth();
                vpIndex++;
                moveViewPagerToMonth();
                setMonthAndYearTextFields(calendarData.getDateObj());
                primaryFragment = mPagerAdapter.getFragment(vpIndex);
                updateFragment();
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
                primaryFragment = mPagerAdapter.getFragment(vpIndex);
                updateFragment();
            }
        });
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
            primaryFragment = mPagerAdapter.getFragment(vpIndex);
            updateFragment();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts the Add Workout Activity*/
    public void goToAddWorkoutActivity(View view) {
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }

    /**
     * This method updates the Month and Year textfields.
     * Call this method when there is a change to currentMonth*/
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
     * Call this AFTER setPrevMonth/setNextMonth.*/
    private void moveViewPagerToMonth() {
        // Go to the current view pager index
        mViewPager.setCurrentItem(vpIndex, true);
    }

    /**
     * This method updates the fragment.
     * Adds:
     * * days of the month
     * * on touch listeners to buttons
     * * disables unused buttons
     * * allows access to workouts*/
    private void updateFragment() {
        int firstDayOfMonth = calendarData.getFirstDayOfMonth();
        int numberOfDaysInMonth = calendarData.getNumberOfDaysInMonth();
        ArrayList<Button> arrayList = Utility.getCellsFromCalendarFragment(primaryFragment.getView());
        int counter = 0;
        for (int i = 0; i < 42; i++) {
            if (i < firstDayOfMonth - 1) {
                // All the days BEFORE the month starts
                counter++;
                arrayList.get(i).setEnabled(false);
                arrayList.get(i).setVisibility(View.INVISIBLE);
            } else if (i >= firstDayOfMonth - 1 && i < numberOfDaysInMonth + counter) {
                // All the days DURING the month
                arrayList.get(i).setText(Integer.toString(i+1-counter));
                arrayList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Set the intent to open up the AddWorkoutActivity with info
                        goToAddWorkoutActivity(v);
                    }
                });
            } else {
                // All the days AFTER the month ends
                arrayList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

}
