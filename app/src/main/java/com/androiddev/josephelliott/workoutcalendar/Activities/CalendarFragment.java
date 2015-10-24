package com.androiddev.josephelliott.workoutcalendar.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;
import com.androiddev.josephelliott.workoutcalendar.R;
import com.androiddev.josephelliott.workoutcalendar.Utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Joseph Elliott on 10/4/2015.
 */
public class CalendarFragment extends Fragment {

    private CurrentCalendarData ccd;

    public static CalendarFragment newInstance(long timeInMillis) {
        CalendarFragment myFragment = new CalendarFragment();

        Bundle args = new Bundle();
        args.putLong("timeInMillis", timeInMillis);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);

        updateFragment(rootView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ccd = new CurrentCalendarData(getArguments().getLong("timeInMillis"));
    }

    private void updateFragment(ViewGroup viewGroup) {
        int firstDayOfMonth = ccd.getFirstDayOfMonth();
        int numberOfDaysInMonth = ccd.getNumberOfDaysInMonth();

        ArrayList<Button> arrayList = Utility.getCellsFromCalendarFragment(viewGroup);
        int counter = 0;
        for (int i = 0; i < 42; i++) {
            if (i < firstDayOfMonth - 1) {
                // All the days BEFORE the month starts
                counter++;
                arrayList.get(i).setEnabled(false);
                arrayList.get(i).setVisibility(View.INVISIBLE);
            } else if (i >= firstDayOfMonth - 1 && i < numberOfDaysInMonth + counter) {
                // All the days DURING the month
                arrayList.get(i).setText(Integer.toString(i + 1 - counter));
                arrayList.get(i).setTextColor(Color.LTGRAY);
                arrayList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Set the intent to open up the AddWorkoutActivity with info
                        //goToAddWorkoutActivity(v);
                    }
                });
            } else {
                // All the days AFTER the month ends
                arrayList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

}
