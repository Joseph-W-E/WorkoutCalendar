package com.androiddev.josephelliott.workoutcalendar.ObjectData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jellio on 9/23/16.
 */

public class PresetsDataSource {

    /*** The database and the database helper ***/
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    /*** The columns associated with the table **/
    private String[] allColumns = {
            SQLiteHelper.COLUMN_PRESETS_ID,          // long 0
            SQLiteHelper.COLUMN_PRESETS_TITLE,       // string 1
            SQLiteHelper.COLUMN_PRESETS_LOCATION,    // string 2
            SQLiteHelper.COLUMN_PRESETS_DESCRIPTION, // string 3
            SQLiteHelper.COLUMN_PRESETS_FREQUENCY,   // string 4
            SQLiteHelper.COLUMN_PRESETS_EXPIRES,     // long 5
    };

    /**
     * Initialize a new WorkoutDataSource with the given context.
     * @param context The context this object is running in.
     */
    public PresetsDataSource(Context context) {
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
     * @param preset The workout to insert into the database.
     */
    public void createPreset(Preset preset) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_PRESETS_TITLE, preset.getTitle());
        values.put(SQLiteHelper.COLUMN_PRESETS_LOCATION, preset.getLocation());
        values.put(SQLiteHelper.COLUMN_PRESETS_DESCRIPTION, preset.getDescription());
        values.put(SQLiteHelper.COLUMN_PRESETS_FREQUENCY, preset.getFrequency());
        values.put(SQLiteHelper.COLUMN_PRESETS_EXPIRES, preset.getExpires().getTime());
        database.insert(SQLiteHelper.TABLE_PRESETS, null, values);
    }

    /**
     * Removes a workout from the database.
     * @param preset The workout to be deleted. (This is based off the ID.)
     */
    public void deletePreset(Preset preset) {
        database.delete(SQLiteHelper.TABLE_PRESETS,
                SQLiteHelper.COLUMN_PRESETS_ID + " = " + preset.getID(), null);
    }

    /**
     * Gets the workouts for the given date (year/month/day_of_month).
     * @return A list of presets.
     */
    public ArrayList<Preset> getPresets() {
        // List of presets to return
        ArrayList<Preset> presets = new ArrayList<>();

        // Cursor is what moves throughout the entire database
        Cursor cursor = database.query(SQLiteHelper.TABLE_PRESETS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            presets.add(cursorToPreset(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return presets;
    }

    /**
     * Converts the current cursor object to a preset.
     * @param cursor The cursor (what the database is pointing at).
     * @return A new preset from the given cursor.
     */
    private Preset cursorToPreset(Cursor cursor) {
        Preset preset = new Preset();
        preset.setID(cursor.getLong(0));
        preset.setTitle(cursor.getString(1));
        preset.setLocation(cursor.getString(2));
        preset.setDescription(cursor.getString(3));
        preset.setFrequency(cursor.getString(4));
        preset.setExpires(new Date(cursor.getLong(5)));
        return preset;
    }

}
