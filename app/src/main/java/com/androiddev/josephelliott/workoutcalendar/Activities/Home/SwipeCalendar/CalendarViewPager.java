package com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by jellio on 10/27/16.
 */

public class CalendarViewPager extends ViewPager {

    /*** The information we need to keep track of the date ***/
    private CalendarWrapper calendar;
    private int index;

    /*** The views that the user may initialize ***/
    private View btnForward, btnBackward;
    private TextView txtMonth, txtYear;

    /*** Constructors ***/
    public CalendarViewPager(Context context) {
        super(context);
    }

    public CalendarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*** Initializes the adapter. This method must be called for the view pager to work. ***/
    public void initializeAdapter(FragmentManager fm, int NUM_PAGES) {
        setAdapter(new CalendarPagerAdapter(fm, NUM_PAGES));
        calendar = new CalendarWrapper();
        onPageChange();
        moveToCurrentDate();
    }




    /**
     * Methods that the user may add to tie views to the view pager.
     * For example, the user may add the "forward" and "backward" views.
     * *** When those buttons are pressed, the view pager will move forward and backward, respectively.
     * For example, the user may add the "month" and "year" Text Views.
     * *** When the view pager moves, those views will be updated to show what month and year we are at.
     */

    /*** When the given button is pressed, the calendar will move forward one month ***/
    public void setForwardButton(View button) {
        btnForward = button;
        btnForward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // We are moving to the right
                calendar.addMonth();
                index++;
                moveViewPagerToMonth();
            }
        });
    }

    /*** When the given button is pressed, the calendar will move backward one month ***/
    public void setBackwardButton(View button) {
        btnBackward = button;
        btnBackward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // We are moving to the left
                calendar.subtractMonth();
                index--;
                moveViewPagerToMonth();
            }
        });
    }

    /*** Adds text fields that will be updated when the calendar moves ***/
    public void setMonthTextField(TextView textView) {
        txtMonth = textView;
        updateTextFields();
    }

    public void setYearTextField(TextView textView) {
        txtYear = textView;
        updateTextFields();
    }




    /**
     * Methods used for quality of life for the user.
     */

    /*** Refreshes the viewpager ***/
    public void refresh() {
        setAdapter(getAdapter());
        moveViewPagerToMonth();
    }

    /*** Moves the calendar to the current date ***/
    public void moveToCurrentDate() {
        calendar.setTodaysDate();
        index = getAdapter().getCount() / 2;
        moveViewPagerToMonth();
    }




    /**
     * Helper methods.
     */

    /*** Updates the text fields to represent the current calendar's date ***/
    private void updateTextFields() {
        if (txtMonth != null) {
            SimpleDateFormat monthTextSDF = new SimpleDateFormat("MMMM");
            txtMonth.setText(monthTextSDF.format(calendar.getDate()));
        }
        if (txtYear != null) {
            SimpleDateFormat yearTextSDF = new SimpleDateFormat("yyyy");
            txtYear.setText(yearTextSDF.format(calendar.getDate()));
        }
    }

    /*** Moves the view pager to the current month ***/
    private void moveViewPagerToMonth() {
        setCurrentItem(index, true);
        updateTextFields();
    }

    /*** On a page change, update the calendar information ***/
    private void onPageChange() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // int position is the index of the new selected page
                if (position > index) {
                    // Then we scrolled to the right
                    calendar.addMonth();
                    index++;
                    updateTextFields();
                } else if (position < index) {
                    // Then we scrolled to the left
                    calendar.subtractMonth();
                    index--;
                    updateTextFields();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

}
