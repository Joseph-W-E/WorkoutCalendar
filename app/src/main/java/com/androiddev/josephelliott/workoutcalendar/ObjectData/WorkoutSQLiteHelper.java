package com.androiddev.josephelliott.workoutcalendar.ObjectData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Joseph Elliott on 12/12/2015.
 */
public class WorkoutSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_WORKOUTS_ID = "_id";
    public static final String COLUMN_WORKOUTS_TITLE = "title";
    public static final String COLUMN_WORKOUTS_DESCRIPTION = "description";
    public static final String COLUMN_WORKOUTS_LOCATION = "location";
    public static final String COLUMN_WORKOUTS_DISTANCE = "distance";
    public static final String COLUMN_WORKOUTS_IMAGE = "image";
    public static final String COLUMN_WORKOUTS_DATE = "date";

    private static final String WORKOUT_DATABASE_NAME = "workouts.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String WORKOUT_DATABASE_CREATE = "create table "
            + TABLE_WORKOUTS + "(" + COLUMN_WORKOUTS_ID + " integer primary key autoincrement, "
            + COLUMN_WORKOUTS_TITLE + " text not null, "
            + COLUMN_WORKOUTS_DATE + " text not null, "
            + COLUMN_WORKOUTS_DESCRIPTION + ", "
            + COLUMN_WORKOUTS_LOCATION + ", "
            + COLUMN_WORKOUTS_DISTANCE + ", "
            + COLUMN_WORKOUTS_IMAGE + ");";

    public WorkoutSQLiteHelper(Context context) {
        super(context, WORKOUT_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(WORKOUT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WorkoutSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }
}
