package com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


/**
 * Custom ViewPagerAdapter.
 * Returns a CalendarFragment().
 * This adapter handles all the logic for the calendar grid.
 * */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {

    /*** The maximum number of pages we can make ***/
    private int NUM_PAGES;

    /**
     * Initialize a new ScreenSlidePagerAdapater.
     * @param fm Used only for super.
     * @param NUM_PAGES The number of months allotted to the calendar.
     */
    public CalendarPagerAdapter(FragmentManager fm, int NUM_PAGES) {
        super(fm);
        this.NUM_PAGES = NUM_PAGES;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    /**
     * Return the CalendarFragment for the given position.
     * @param position The position in the calendar.
     * @return The CalendarFragment for the given position.
     */
    @Override
    public Fragment getItem(int position) {
        // The position refers to what month we are looking at. NUM_PAGES / 2 is today.
        CalendarWrapper calendar = new CalendarWrapper();

        // Set the new month for the fragment based off the position in the ViewPager
        if (position > NUM_PAGES / 2) {
            // We are traveling forward in time
            for (int i = NUM_PAGES / 2; i < position; i++) {
                calendar.addMonth();
            }
        } else if (position < NUM_PAGES / 2) {
            // We are traveling backward in time
            for (int i = position; i < NUM_PAGES / 2; i++) {
                calendar.subtractMonth();
            }
        }

        return CalendarFragment.newInstance(calendar.getDate().getTime());
    }

    /**
     * Pending.
     * @param container X
     * @param position X
     * @param object X
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
