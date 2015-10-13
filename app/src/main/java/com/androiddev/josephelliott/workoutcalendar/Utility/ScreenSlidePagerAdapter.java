package com.androiddev.josephelliott.workoutcalendar.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.androiddev.josephelliott.workoutcalendar.Activities.CalendarFragment;

import java.util.HashMap;


/**
 * Custom ViewPagerAdapter.
 * Returns a CalendarFragment().*/
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_PAGES;
    private HashMap<Integer, CalendarFragment> map;

    public ScreenSlidePagerAdapter(FragmentManager fm, int NUM_PAGES) {
        super(fm);
        this.NUM_PAGES = NUM_PAGES;
        map = new HashMap<>();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        CalendarFragment cf = new CalendarFragment();
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
