package com.androiddev.josephelliott.workoutcalendar.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joseph Elliott on 10/4/2015.
 */
public class CalendarFragment extends Fragment {
/*
    private long timeInMillis;
    private int firstDayOfMonth;
    private int numberOfDaysInMonth;


    public static CalendarFragment newInstance(Date date) {
        CalendarFragment cf = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("millis", date.getTime());
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            timeInMillis = getArguments() != null ? savedInstanceState.getLong("millis") : 1;
        } catch (NullPointerException e) {
            timeInMillis = CurrentCalendarData.getTodaysDate();
        }
        CurrentCalendarData calendarData = new CurrentCalendarData(timeInMillis);
        firstDayOfMonth = calendarData.getFirstDayOfMonth();
        numberOfDaysInMonth = calendarData.getNumberOfDaysInMonth();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        /*
        ArrayList<Button> arrayList = Utility.getCellsFromCalendarFragment(rootView);
        int counter = 0;
        for (int i = 0; i < 42; i++) {
            if (i < firstDayOfMonth - 1) {
                counter++;
                arrayList.get(i).setEnabled(false);
            } else if (i >= firstDayOfMonth - 1 && i < numberOfDaysInMonth + counter) {
                arrayList.get(i).setText(Integer.toString(i+1-counter));
            }
        }*/
        return rootView;
    }

}
