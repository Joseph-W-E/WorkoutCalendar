package com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout.CustomWorkoutActivity;
import com.androiddev.josephelliott.workoutcalendar.Database.Workout;
import com.androiddev.josephelliott.workoutcalendar.Database.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;

/**
 * Created by Joseph Elliott on 10/4/2015.
 */
public class CalendarFragment extends Fragment {

    private CalendarWrapper calendar;

    private ArrayList<Workout> workouts;

    public static CalendarFragment newInstance(long timeInMillis) {
        CalendarFragment myFragment = new CalendarFragment();

        Bundle args = new Bundle();
        args.putLong("timeInMillis", timeInMillis);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.calendar_fragment, container, false);

        calendar = new CalendarWrapper(getArguments().getLong("timeInMillis"));

        WorkoutDataSource dataSource = new WorkoutDataSource(getContext());
        dataSource.open();
        workouts = dataSource.getWorkouts(calendar.getDate());
        dataSource.close();

        updateFragment(rootView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateFragment(ViewGroup viewGroup) {
        // Things we'll need to know when there are days in the month
        int firstDayOfMonth     = calendar.getFirstDayOfMonth();
        int numberOfDaysInMonth = calendar.getNumberOfDaysInMonth();

        // List of the cells available. Should be 42 of them.
        ArrayList<Button> arrayList = Utility.getCellsFromCalendarFragment(viewGroup);

        // counter for offsetting the start days
        int offset = 0;

        for (int i = 0; i < 42; i++) {
            final Button currentButton = arrayList.get(i);

            if (i < firstDayOfMonth - 1) {

                // All the days BEFORE the month starts
                offset++;
                setButtonToUnavailable(currentButton);

            } else if (i >= firstDayOfMonth - 1 && i < numberOfDaysInMonth + offset) {

                // Update the CalendarWrapper to reflect the current day
                calendar.setDay(i + 1 - offset);

                // Make the cell "visible" to the reader
                currentButton.setText(Integer.toString(calendar.getDay()));
                currentButton.setTextColor(Color.LTGRAY);

                // Get the workouts associated with today
                final ArrayList<Workout> thisDaysWorkouts = new ArrayList<>();
                for (Workout workout : workouts) {
                    CalendarWrapper cal = new CalendarWrapper(workout.getDate().getTime());
                    if (cal.getDay() == calendar.getDay()) {
                        // This workout belongs to today
                        thisDaysWorkouts.add(workout);
                    }
                }

                // If there were workouts, make the button open a dialog to view the workouts
                if (!thisDaysWorkouts.isEmpty()) {
                    currentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createDialog(thisDaysWorkouts, calendar.getDay());
                        }
                    });
                    currentButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_light));
                }

            } else {

                // All the days AFTER the month ends
                setButtonToUnavailable(currentButton);

            }
        }
    }

    private void createDialog(final ArrayList<Workout> workouts, final int day) {
        // Create the dialog and add the outer view
        final Dialog dialog = new Dialog(getContext(), R.style.WorkoutDialog);

        // Set the title to the current date
        dialog.setTitle(calendar.getMonthText() + " " + Integer.toString(day));

        dialog.show();

        // Get the outer view
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup      outerView      = (ViewGroup) layoutInflater.inflate(R.layout.calendar_dialog_workouts_outer, null);

        dialog.setContentView(outerView);

        // Create the children and add it to the outer view
        for (int i = 0; i < workouts.size(); i++) {
            // for each workout, create a calendar_dialog_workouts_inner and add it to the outer view
            View tempView           = layoutInflater.inflate(R.layout.calendar_dialog_workouts_inner, null);
            TextView txtTitle       = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_title);
            TextView txtDescription = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_description);
            TextView txtLocation    = (TextView) tempView.findViewById(R.id.calendar_dialog_workouts_inner_location);
            ImageButton btnEdit     = (ImageButton) tempView.findViewById(R.id.calendar_dialog_workouts_inner_edit);
            ImageButton btnDelete   = (ImageButton) tempView.findViewById(R.id.calendar_dialog_workouts_inner_delete);

            final Workout currentWorkout = workouts.get(i);

            // The EDIT WORKOUT feature
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getBaseContext(), CustomWorkoutActivity.class);
                    intent.putExtra("_id", currentWorkout.getID());
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            // End EDIT WORKOUT feature

            // The DELETE WORKOUT feature
            //final CalendarFragment calendarFragment = this;
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the workout from the database
                    WorkoutDataSource dataSource = new WorkoutDataSource(getContext());
                    dataSource.open();
                    dataSource.deleteWorkout(currentWorkout);
                    calendar.setDay(day);
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

    /**
     * Called when you want to completely recreate this process. It'll reupdate all the workouts.
     */
    public void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void setButtonToUnavailable(Button button) {
        button.setEnabled(false);
        button.setVisibility(View.INVISIBLE);
    }

}
