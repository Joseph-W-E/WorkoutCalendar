package com.androiddev.josephelliott.workoutcalendar.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joseph Elliott on 12/12/2015.
 */
public class WorkoutDataSource {

    /*** The database and the database helper ***/
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    /*** The columns associated with the table **/
    private String[] allColumns = {
            SQLiteHelper.COLUMN_WORKOUTS_ID,          // long 0
            SQLiteHelper.COLUMN_WORKOUTS_DATE,        // long 1
            SQLiteHelper.COLUMN_WORKOUTS_DESCRIPTION, // string 2
            SQLiteHelper.COLUMN_WORKOUTS_DISTANCE,    // double 3
            SQLiteHelper.COLUMN_WORKOUTS_IMAGE,       // int 4
            SQLiteHelper.COLUMN_WORKOUTS_LOCATION,    // string 5
            SQLiteHelper.COLUMN_WORKOUTS_TITLE        // string 6
    };

    /**
     * Initialize a new WorkoutDataSource with the given context.
     * @param context The context this object is running in.
     */
    public WorkoutDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    /**
     * Opens the database for usage.
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the database.
     * Please call this every time you upen the database.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Creates a workout in the database based off the given workout.
     * @param workout The workout to insert into the database.
     */
    public void createWorkout(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_WORKOUTS_TITLE, workout.getTitle());
        values.put(SQLiteHelper.COLUMN_WORKOUTS_DATE, workout.getDate().getTime());
        values.put(SQLiteHelper.COLUMN_WORKOUTS_DESCRIPTION, workout.getDescription());
        values.put(SQLiteHelper.COLUMN_WORKOUTS_DISTANCE, workout.getDistance());
        values.put(SQLiteHelper.COLUMN_WORKOUTS_IMAGE, 0);
        values.put(SQLiteHelper.COLUMN_WORKOUTS_LOCATION, workout.getLocation());
        database.insert(SQLiteHelper.TABLE_WORKOUTS, null, values);
    }

    /**
     * Removes a workout from the database.
     * @param workout The workout to be deleted. (This is based off the ID.)
     */
    public void deleteWorkout(Workout workout) {
        database.delete(SQLiteHelper.TABLE_WORKOUTS,
                SQLiteHelper.COLUMN_WORKOUTS_ID + " = " + workout.getID(), null);
    }

    /**
     * Gets the workouts for the given date (year/month/day_of_month).
     * @param date The date in which to compare to the workouts.
     * @return A list of workouts for the given date.
     */
    public ArrayList<Workout> getWorkouts(Date date) {
        // Convert the date object to a Calendar object (for easy comparisons)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // A Calendar object used for comparisons of workout to date (now a Calendar object)
        Calendar rowCalendar = Calendar.getInstance();

        // List of workouts to return
        ArrayList<Workout> workouts = new ArrayList<>();

        // Cursor is what moves throughout the entire database
        Cursor cursor = database.query(SQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null,
                SQLiteHelper.COLUMN_WORKOUTS_DATE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            rowCalendar.setTime(workout.getDate());

            // if the dates are in the same day, month and year, add the workout
            if ((calendar.get(Calendar.YEAR) == rowCalendar.get(Calendar.YEAR))
                    && (calendar.get(Calendar.MONTH) == rowCalendar.get(Calendar.MONTH))
                    && (calendar.get(Calendar.DAY_OF_MONTH) == rowCalendar.get(Calendar.DAY_OF_MONTH))) {
                workouts.add(workout);
            }

            cursor.moveToNext();
        }
        cursor.close();

        return workouts;
    }

    /**
     * Converts the current cursor object to a workout.
     * @param cursor The cursor (what the database is pointing at).
     * @return A new workout from the given cursor.
     */
    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setID(cursor.getLong(0));
        workout.setDate(new Date(cursor.getLong(1)));
        workout.setDescription(cursor.getString(2));
        workout.setDistance(cursor.getDouble(3));
        workout.setImage(null); // 4
        workout.setLocation(cursor.getString(5));
        workout.setTitle(cursor.getString(6));
        return workout;
    }
}
