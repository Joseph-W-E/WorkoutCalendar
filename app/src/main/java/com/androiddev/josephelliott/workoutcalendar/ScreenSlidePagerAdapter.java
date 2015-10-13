package com.androiddev.josephelliott.workoutcalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

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



    /*
    @Override
    public Fragment getItem(int position) {
        CalendarFragment fragment = new CalendarFragment();
        map.put(position, fragment);
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        map.remove(position);
    }

    public CalendarFragment getFragment(int position) {
        return map.get(position);
    }*/


}
