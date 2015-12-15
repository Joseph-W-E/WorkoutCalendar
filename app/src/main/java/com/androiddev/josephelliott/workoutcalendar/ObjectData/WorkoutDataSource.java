package com.androiddev.josephelliott.workoutcalendar.ObjectData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joseph Elliott on 12/12/2015.
 */
public class WorkoutDataSource {

    // Database fields
    private SQLiteDatabase database;
    private WorkoutSQLiteHelper dbHelper;
    private String[] allColumns = {
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_ID, // long 0
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_DATE, // long 1
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_DESCRIPTION, // string 2
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_DISTANCE, // double 3
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_IMAGE, // int 4
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_LOCATION, // string 5
            WorkoutSQLiteHelper.COLUMN_WORKOUTS_TITLE // string 6
    };

    public WorkoutDataSource(Context context) {
        dbHelper = new WorkoutSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createWorkout(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_TITLE, workout.getTitle());
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_DATE, workout.getDate().getTime());
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_DESCRIPTION, workout.getDescription());
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_DISTANCE, workout.getDistance());
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_IMAGE, 0);
        values.put(WorkoutSQLiteHelper.COLUMN_WORKOUTS_LOCATION, workout.getLocation());
        database.insert(WorkoutSQLiteHelper.TABLE_WORKOUTS, null, values);
    }

    public void deleteWorkout(Workout workout) {
        database.delete(WorkoutSQLiteHelper.TABLE_WORKOUTS,
                WorkoutSQLiteHelper.COLUMN_WORKOUTS_ID + " = " + workout.getID(), null);
    }

    public ArrayList<Workout> getWorkouts(Date date) {
        // Calendar for the date given
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Calendar for the date from the table
        Calendar rowCalendar = Calendar.getInstance();

        // List of workouts to return
        ArrayList<Workout> workouts = new ArrayList<>();

        // Cursor is what moves throughout the entire database
        Cursor cursor = database.query(WorkoutSQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null, WorkoutSQLiteHelper.COLUMN_WORKOUTS_DATE + " DESC");

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

        return workouts;
    }

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
