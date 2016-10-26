package com.androiddev.josephelliott.workoutcalendar.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Joseph Elliott on 12/12/2015.
 */
class SQLiteHelper extends SQLiteOpenHelper {

    /*** The database file name ***/
    private static final String DATABASE_NAME = "workout_data.db";
    /*** The database version ***/
    private static final int DATABASE_VERSION = 1;

    /*** Constants for the "workouts" table ***/
    static final String TABLE_WORKOUTS              = "workouts";
    static final String COLUMN_WORKOUTS_ID          = "_id";
    static final String COLUMN_WORKOUTS_TITLE       = "title";
    static final String COLUMN_WORKOUTS_DESCRIPTION = "description";
    static final String COLUMN_WORKOUTS_LOCATION    = "location";
    static final String COLUMN_WORKOUTS_DISTANCE    = "distance";
    static final String COLUMN_WORKOUTS_IMAGE       = "image";
    static final String COLUMN_WORKOUTS_DATE        = "date";

    /*** Statement to create a table for "workouts" ***/
    private static final String WORKOUT_DATABASE_CREATE = "create table "
            + TABLE_WORKOUTS + "(" + COLUMN_WORKOUTS_ID + " integer primary key autoincrement, "
            + COLUMN_WORKOUTS_TITLE + " text not null, "
            + COLUMN_WORKOUTS_DATE + " text not null, "
            + COLUMN_WORKOUTS_DESCRIPTION + " text, "
            + COLUMN_WORKOUTS_LOCATION + " text, "
            + COLUMN_WORKOUTS_DISTANCE + " real, "
            + COLUMN_WORKOUTS_IMAGE + " blob);";

    /*** Constants for the "presets" table ***/
    static final String TABLE_PRESETS              = "presets";
    static final String COLUMN_PRESETS_ID          = "_id";
    static final String COLUMN_PRESETS_TITLE       = "title";
    static final String COLUMN_PRESETS_LOCATION    = "location";
    static final String COLUMN_PRESETS_DESCRIPTION = "description";
    static final String COLUMN_PRESETS_FREQUENCY   = "frequency";
    static final String COLUMN_PRESETS_EXPIRES     = "expires";

    /*** Statement to create a table for "presets" ***/
    private static final String PRESETS_DATABASE_CREATE = "create table "
            + TABLE_PRESETS + "(" + COLUMN_PRESETS_ID + " integer primary key autoincrement,"
            + COLUMN_PRESETS_TITLE + " text not null,"
            + COLUMN_PRESETS_LOCATION + " text,"
            + COLUMN_PRESETS_DESCRIPTION + " text,"
            + COLUMN_PRESETS_FREQUENCY + " text,"
            + COLUMN_PRESETS_EXPIRES + " text);";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(WORKOUT_DATABASE_CREATE);
        database.execSQL(PRESETS_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESETS);
        onCreate(db);
    }
}
