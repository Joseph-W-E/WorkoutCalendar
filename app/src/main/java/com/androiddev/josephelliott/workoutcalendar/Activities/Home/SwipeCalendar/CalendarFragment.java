package com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.CurrentCalendarData;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.Workout;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.calendar_fragment, container, false);

        updateFragment(rootView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ccd = new CurrentCalendarData(getArguments().getLong("timeInMillis"));
    }

    private void updateFragment(ViewGroup viewGroup) {
        // Things we'll need to know when there are days in the month
        int firstDayOfMonth = ccd.getFirstDayOfMonth();
        int numberOfDaysInMonth = ccd.getNumberOfDaysInMonth();

        // List of the cells available. Should be 42 of them.
        ArrayList<Button> arrayList = Utility.getCellsFromCalendarFragment(viewGroup);

        // counter for offsetting the start days
        int counter = 0;

        for (int i = 0; i < 42; i++) {
            final Button currentButton = arrayList.get(i);

            if (i < firstDayOfMonth - 1) {
                // All the days BEFORE the month starts
                counter++;
                currentButton.setEnabled(false);
                currentButton.setVisibility(View.INVISIBLE);

            } else if (i >= firstDayOfMonth - 1 && i < numberOfDaysInMonth + counter) {

                // All the days DURING the month

                // Update the CurrentCalendarData to reflect the current day
                ccd.setDay(i + 1 - counter);

                // Get the workouts for the day
                WorkoutDataSource dataSource = new WorkoutDataSource(getContext());
                dataSource.open();
                final ArrayList<Workout> workouts = dataSource.getWorkouts(ccd.getDateObj());
                dataSource.close();

                // Make the cell "visible" to the reader
                currentButton.setText(Integer.toString(ccd.getDay()));
                currentButton.setTextColor(Color.LTGRAY);

                // If the day has workouts, let the user be able to view them
                if (!workouts.isEmpty()) {
                    // First, get the information that we are going to need. We already have the list of workouts
                    final int day = ccd.getDay();
                    currentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createDialog(workouts, day);
                        }
                    });
                    currentButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_light));
                }


            } else {

                // All the days AFTER the month ends
                currentButton.setEnabled(false);
                currentButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void createDialog(final ArrayList<Workout> workouts, int day) {
        // Get a 'final' instance of our button and day
        final int currentDay = day;

        // Create the dialog and add the outer view
        final Dialog dialog = new Dialog(getContext(), R.style.WorkoutDialog);

        // Set the title to the current date
        dialog.setTitle(ccd.getMonthText() + " " + Integer.toString(day));

        // Only show the dialog if we did something
        if (!workouts.isEmpty()) {
            dialog.show();
        }

        // Get the outer view
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup outerView = (ViewGroup) layoutInflater.inflate(R.layout.calendar_dialog_workouts_outer, null);

        dialog.setContentView(outerView);

        // Create the children and add it to the outer view
        for (int i = 0; i < workouts.size(); i++) {
            // for each workout, create a calendar_dialog_workouts_inner and add it to the outer view
            View tempView = layoutInflater.inflate(R.layout.calendar_dialog_workouts_inner, null);
            TextView txtTitle = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_title);
            TextView txtDescription = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_description);
            TextView txtLocation = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_location);
            ImageButton btnDelete = (ImageButton) tempView.findViewById(R.id.calendar_dialog_workouts_inner_delete);

            // The DELETE WORKOUT feature
            final Workout deleteWorkout = workouts.get(i);
            //final CalendarFragment calendarFragment = this;
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the workout from the database
                    WorkoutDataSource dataSource = new WorkoutDataSource(getContext());
                    dataSource.open();
                    dataSource.deleteWorkout(deleteWorkout);
                    ccd.setDay(currentDay);
                    ArrayList<Workout> newWorkouts = dataSource.getWorkouts(ccd.getDateObj());
                    dataSource.close();

                    // Close the dialog
                    dialog.cancel();
                    // Refresh the cell inside the fragment
                    refresh();
                }
            });
            // End DELETE WORKOUT feature

            // Update the text that the user will see
            txtTitle.setText(workouts.get(i).getTitle());
            txtDescription.setText(workouts.get(i).getDescription());
            txtLocation.setText(workouts.get(i).getLocation());

            // Add this view to the outer view
            ((ViewGroup) outerView.findViewById(R.id.calendar_dialog_workouts_outer_linear_layout)).addView(tempView);
        }

    }

    public void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

}
