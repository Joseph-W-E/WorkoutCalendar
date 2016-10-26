package com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.Database.Preset;
import com.androiddev.josephelliott.workoutcalendar.Database.PresetsDataSource;
import com.androiddev.josephelliott.workoutcalendar.Database.Workout;
import com.androiddev.josephelliott.workoutcalendar.Database.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joseph Elliott on 10/12/2015.
 */
public class CustomWorkoutActivity extends Activity {

    /*** Context is always useful to have! ***/
    private Context context;

    /*** Variables needed for recording the workout ***/
    private Calendar calendarDatePicked;
    private Image image;
    private Workout workout;

    /*** Variables for views ***/
    private AutoCompleteTextView actvTitle, actvWorkout;
    private Button btnLoadFromPresets, btnSaveToPresets;
    private EditText etLbs, etSets, etReps, etLoc, etDesc;
    private ImageButton btnDate, btnImage, btnAddExercise, btnCancel, btnSave;

    /*** Needed AutoCompleteTextView variables
     * Note that this is a very hacked way of getting the first item from the
     * drop down list of suggestions.
     * ***/
    private Object actvWorkoutFirstItem;
    private Object actvTitleFirstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_workout_activity);

        /*** Get the context ***/
        context = CustomWorkoutActivity.this;
        /*** Get the current time ***/
        calendarDatePicked = Calendar.getInstance();
        /*** Get the to-be-created workout object ready ***/
        workout = new Workout();

        /*** Get all the used views ***/
        etLbs  = (EditText) findViewById(R.id.custom_workout_et_lbs);
        etSets = (EditText) findViewById(R.id.custom_workout_et_sets);
        etReps = (EditText) findViewById(R.id.custom_workout_et_reps);
        etLoc  = (EditText) findViewById(R.id.custom_workout_et_location);
        etDesc = (EditText) findViewById(R.id.custom_workout_et_description);
        actvWorkout = (AutoCompleteTextView) findViewById(R.id.custom_workout_actv_lift);
        actvTitle   = (AutoCompleteTextView) findViewById(R.id.custom_workout_actv_title);
        btnLoadFromPresets = (Button) findViewById(R.id.custom_workout_btn_load_from_presets);
        btnSaveToPresets   = (Button) findViewById(R.id.custom_workout_btn_save_to_presets);
        btnDate        = (ImageButton) findViewById(R.id.custom_workout_btn_date);
        btnImage       = (ImageButton) findViewById(R.id.custom_workout_btn_image);
        btnAddExercise = (ImageButton) findViewById(R.id.custom_workout_btn_add_lift_to_description);
        btnCancel      = (ImageButton) findViewById(R.id.custom_workout_btn_cancel);
        btnSave        = (ImageButton) findViewById(R.id.custom_workout_btn_save);

        /*** Set the logic for the views ***/
        initializeButtons();
        initializeSaveButton();
        initializeAutoCompleteTextFields();
        setInputTextTransitions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_default_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_default_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the buttons.
     */
    private void initializeButtons() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sorry, this button isn't working yet.", Toast.LENGTH_SHORT).show();
                image = null;
            }
        });

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

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonFunctionality();
            }
        });

        btnLoadFromPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Launch the preset-picker dialog ***/
                CustomWorkoutDialogLoadFromPresets dialog = new CustomWorkoutDialogLoadFromPresets();
                dialog.show(getFragmentManager(), "Load From Presets");
            }
        });

        btnSaveToPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Create a new preset ***/
                Preset preset = new Preset();
                preset.setTitle(actvTitle.getText().toString());
                preset.setLocation(etLoc.getText().toString());
                preset.setDescription(etDesc.getText().toString());
                preset.setFrequency("");
                preset.setExpires(new Date());

                /*** Add it to the database ***/
                PresetsDataSource dataSource = new PresetsDataSource(context);
                dataSource.open();
                dataSource.createPreset(preset);
                dataSource.close();

                /*** Notify the user ***/
                Toast.makeText(context, "Created a new preset", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Initializes the auto complete text fields.
     * This is for the workout list and the title list.
     * */
    private void initializeAutoCompleteTextFields() {
        String[] workoutTypes = {
                "Bench", "Squat", "Press", "DB Curl", "Pushups"
        };
        String[] titleTypes = {
                "Head", "Neck",
                "Arms", "Biceps", "Triceps", "Forearms",
                "Chest", "Pecs", "Abs", "Waist",
                "Back", "Back-Upper", "Back-Lower",
                "Legs", "Quads", "Calves", "Hamstrings"
        };

        final ArrayAdapter<String> adapterWorkouts = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, workoutTypes);
        adapterWorkouts.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int isDropDownAnchorDisplayed = actvWorkout.getDropDownAnchor();
                if (isDropDownAnchorDisplayed == 0) return;
                actvWorkoutFirstItem = adapterWorkouts.getItem(0);
            }
        });
        actvWorkout.setThreshold(1);
        actvWorkout.setAdapter(adapterWorkouts);

        final ArrayAdapter<String> adapterTitles = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, titleTypes);
        adapterTitles.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int isDropDownAnchorDisplayed = actvTitle.getDropDownAnchor();
                if (isDropDownAnchorDisplayed == 0) return;
                actvTitleFirstItem = adapterTitles.getItem(0);
            }
        });
        actvTitle.setThreshold(1);
        actvTitle.setAdapter(adapterTitles);
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
    private void setInputTextTransitions() {
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
                    if (actvTitleFirstItem != null) {
                        actvTitle.setText(actvTitleFirstItem.toString());
                        actvTitleFirstItem = null;
                    }
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
                    if (actvWorkoutFirstItem != null) {
                        actvWorkout.setText(actvWorkoutFirstItem.toString());
                        actvWorkoutFirstItem = null;
                    }
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

    /**
     * Loads a workout based on a preset.
     * @param preset The workout to be loaded.
     */
    public void loadFromPreset(Preset preset) {
        actvTitle.setText(preset.getTitle());
        etLoc.setText(preset.getLocation());
        etDesc.setText(preset.getDescription());
    }
}
