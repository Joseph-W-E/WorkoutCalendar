package com.androiddev.josephelliott.workoutcalendar.Activities.AddingWorkouts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.Workout;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Joseph Elliott on 10/12/2015.
 */
public class AddWorkoutActivity extends Activity {

    private Context context;

    private Calendar calendarDatePicked;

    private ArrayList<String> workoutList, titleList;

    private AutoCompleteTextView actvWorkout, actvTitle;
    private EditText etLbs, etSets, etReps, etLoc, etDesc;
    private ImageButton btnDate, btnImage, btnAddExercise, btnCancel, btnSave;

    private Image image;

    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        /*** Get the context ***/
        context = AddWorkoutActivity.this;
        /*** Get the current time ***/
        calendarDatePicked = Calendar.getInstance();

        /*** Get all the used views ***/
        etLbs  = (EditText) findViewById(R.id.custom_workout_lbs_edit_text);
        etSets = (EditText) findViewById(R.id.custom_workout_sets_edit_text);
        etReps = (EditText) findViewById(R.id.custom_workout_reps_edit_text);
        etLoc  = (EditText) findViewById(R.id.workout_location_txt);
        etDesc = (EditText) findViewById(R.id.workout_description_txt);
        actvWorkout = (AutoCompleteTextView) findViewById(R.id.custom_workout_exercise_actv);
        actvTitle   = (AutoCompleteTextView) findViewById(R.id.workout_title_txt);
        btnDate        = (ImageButton) findViewById(R.id.custom_workout_change_date);
        btnImage       = (ImageButton) findViewById(R.id.custom_workout_change_image);
        btnAddExercise = (ImageButton) findViewById(R.id.custom_workout_add_exercise_btn);
        btnCancel      = (ImageButton) findViewById(R.id.custom_workout_cancel);
        btnSave        = (ImageButton) findViewById(R.id.custom_workout_save);

        /*** Get the to-be-created workout object ready ***/
        workout = new Workout();

        /*** Perform layout operations ***/
        try {
            getActionBar().setElevation(0);
            getActionBar().setTitle("Add Your Workout");
        } catch (NullPointerException e) {
            // TODO
        }

        /*** Set the logic for the views ***/
        initializeCancelButton();
        initializeImageButton();
        initializeDateButton();
        initializeAddButton();
        initializeSaveButton();
        initializeAutoCompleteTextFields();
        initializeEditTexts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addworkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_addworkout_settings) {
            return true;
        } else if (id == R.id.menu_addworkout_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the cancel button.
     * */
    private void initializeCancelButton() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Initializes the image button.
     * // TODO
     * */
    private void initializeImageButton() {
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open an image picker dialog

                // Select an image

                // store the image for later use
                image = null;
            }
        });
    }

    /**
     * Initializes the date picker dialog. Basically sets an on click listener for the Date Button.
     * */
    private void initializeDateButton() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // They picked a new date! Store it.
                        calendarDatePicked.set(year, monthOfYear, dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
    }

    /**
     * Initializes the add button. Basically, it rips the text from the edit texts and stuffs it into the description box.
     * */
    private void initializeAddButton() {
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonFunctionality();
            }
        });
    }

    /**
     * Initializes the auto complete text fields.
     * This is for the workout list and the title list.
     * */
    private void initializeAutoCompleteTextFields() {
        // workoutList is the list of workouts for the drop down
        workoutList = new ArrayList<>();
        // Generate the list of workouts to be AutoCompleted
        workoutList.add("Bench");
        workoutList.add("Squat");
        workoutList.add("Press");
        workoutList.add("DB Curl");
        workoutList.add("Pushups");
        // Set up the adapter
        ArrayAdapter<String> adapterWorkouts = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, workoutList);
        // Set the threshold (number of characters the user must type before results are shown)
        actvWorkout.setThreshold(1);
        // Set the adapter
        actvWorkout.setAdapter(adapterWorkouts);
        // Profit $$$

        // titleList is the list of titles for the drop down
        titleList = new ArrayList<>();
        // Generate the list of workouts to be AutoCompleted
        titleList.add("Arms");
        titleList.add("Legs");
        // Set up the adapter
        ArrayAdapter<String> adapterTitles = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, titleList);
        // Set the threshold (number of characters the user must type before results are shown)
        actvTitle.setThreshold(1);
        // Set the adapter
        actvTitle.setAdapter(adapterTitles);
        // Profit $$$
    }

    /**
     * Initializes the save button.
     * Builds the complete Workout object and saves it to the database.
     * */
    private void initializeSaveButton() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title
                workout.setTitle(actvTitle.getText().toString());
                // Get the location
                workout.setLocation(etLoc.getText().toString());
                // Get the description
                workout.setDescription(etDesc.getText().toString());
                // Get the date
                workout.setDate(calendarDatePicked.getTime());
                // Get the distance (shouldn't be one)
                workout.setDistance(0);
                // Get the image
                workout.setImage(image);

                // Now that we have a complete workout, save it to the database
                WorkoutDataSource dataSource = new WorkoutDataSource(context);
                dataSource.open();
                dataSource.createWorkout(workout);
                dataSource.close();

                // rip in pieces
                finish();
            }
        });
    }

    /**
     * Initializes the edit texts.
     * Lets one edit text 'enter press' go to the next edit text.
     * */
    private void initializeEditTexts() {
        //private EditText etLbs, etSets, etReps, etLoc, etDesc;
        // Title -> Location -> Workout -> Lbs -> Sets -> Reps -> Workout
        actvTitle.setFocusable(true);
        etLoc.setFocusable(true);
        actvWorkout.setFocusable(true);
        etLbs.setFocusable(true);
        etSets.setFocusable(true);
        etReps.setFocusable(true);
        actvTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    actvTitle.clearFocus();
                    etLoc.requestFocus();
                    return true;
                }
                return false;
            }
        });
        etLoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    etLoc.clearFocus();
                    actvWorkout.requestFocus();
                    return true;
                }
                return false;
            }
        });
        actvWorkout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    actvWorkout.clearFocus();
                    etLbs.requestFocus();
                    return true;
                }
                return false;
            }
        });
        etLbs.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    etLbs.clearFocus();
                    etSets.requestFocus();
                    return true;
                }
                return false;
            }
        });
        etSets.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    etSets.clearFocus();
                    etReps.requestFocus();
                    return true;
                }
                return false;
            }
        });
        etReps.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    etReps.clearFocus();
                    actvWorkout.requestFocus();

                    // Here is where we will mimic the + button.
                    addButtonFunctionality();

                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Add button functionality
     * */
    private void addButtonFunctionality() {
        // We want to get the workout and add it to the description
        String result = "";
        // Only add text if it isn't empty
        if (!actvWorkout.getText().toString().isEmpty()) {
            result += actvWorkout.getText().toString();
        }
        if (!etLbs.getText().toString().isEmpty()) {
            result += " " + etLbs.getText().toString() + "lbs";
        }
        if (!etSets.getText().toString().isEmpty()) {
            result += ", " + etSets.getText().toString();
        }
        if (!etReps.getText().toString().isEmpty()) {
            result += " x " + etReps.getText().toString();
        }
        // If result isn't empty, then we can add it to the description
        if (result.compareTo("") != 0) {
            // If description is empty, don't add a new line character
            if (etDesc.getText().toString().isEmpty()) {
                etDesc.setText(etDesc.getText().toString() + result);
            } else {
                etDesc.setText(etDesc.getText().toString() + "\n" + result);
            }
        }
        // Reset everything
        etLbs.setText("");
        etSets.setText("");
        etReps.setText("");
        actvWorkout.setText("");
    }
}
