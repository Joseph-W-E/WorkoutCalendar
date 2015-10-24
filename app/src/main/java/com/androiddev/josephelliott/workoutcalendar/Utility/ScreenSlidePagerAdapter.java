package com.androiddev.josephelliott.workoutcalendar.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.androiddev.josephelliott.workoutcalendar.Activities.CalendarFragment;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;

import java.util.HashMap;


/**
 * Custom ViewPagerAdapter.
 * Returns a CalendarFragment().*/
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_PAGES;
    private HashMap<Integer, CalendarFragment> map;
    long timeInMillis;

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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        map.remove(position);
    }

    public CalendarFragment getFragment(int key) {
        return map.get(key);
    }

}
