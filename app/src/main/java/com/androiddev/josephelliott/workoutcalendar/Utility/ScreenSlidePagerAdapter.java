package com.androiddev.josephelliott.workoutcalendar.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.androiddev.josephelliott.workoutcalendar.Activities.HomePage.CalendarFragment;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;

import java.util.HashMap;


/**
 * Custom ViewPagerAdapter.
 * Returns a CalendarFragment().
 * This adapter handles all the logic for the calendar grid.
 * */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_PAGES;
    private HashMap<Integer, CalendarFragment> map;
    private long timeInMillis;

    /**
     * Initialize a new ScreenSlidePagerAdapater.
     * @param fm Used only for super.
     * @param NUM_PAGES The number of months allotted to the calendar.
     * @param timeInMillis The user's current time.
     */
    public ScreenSlidePagerAdapter(FragmentManager fm, int NUM_PAGES, long timeInMillis) {
        super(fm);
        this.NUM_PAGES = NUM_PAGES;
        map = new HashMap<>();
        this.timeInMillis = timeInMillis;
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
        // The position refers to what month we are looking at
        // For example, if the position is 500, then we are at today
        // If the position is NUM_PAGES / 2 + 2, we are looking at december.
        CurrentCalendarData ccd = new CurrentCalendarData(timeInMillis);

        // Set the new month for the fragment based off the position in the ViewPager
        if (position > NUM_PAGES / 2) {
            // We are traveling forward in time
            for (int i = NUM_PAGES / 2; i < position; i++) {
                ccd.addMonth();
            }
        } else if (position < NUM_PAGES / 2) {
            // We are traveling backward in time
            for (int i = position; i < NUM_PAGES / 2; i++) {
                ccd.subtractMonth();
            }
        }

        CalendarFragment cf = CalendarFragment.newInstance(ccd.getDateObj().getTime());
        map.put(position, cf);
        return cf;
    }

    /**
     * Removes the CalendarFragment from the hash map.
     * @param container Used only for super.
     * @param position Used only for super.
     * @param object Used only for super.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        map.remove(position);
    }

    /**
     * Returns the fragment given the hash map key.
     * @param key The integer value associated with the fragment in a hash map.
     * @return The CalendarFragment pointed to by the key.
     */
    public CalendarFragment getFragment(int key) {
        return map.get(key);
    }

}
