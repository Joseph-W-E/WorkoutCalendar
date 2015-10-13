package com.androiddev.josephelliott.workoutcalendar.ObjectData;

import android.media.Image;

import java.util.Date;

/**
 * Created by Joseph Elliott on 10/12/2015.
 */
public class Workout {

    private long ID;
    private String title;
    private String description;
    private Date date;
    private Image image;

    public Workout() {
        title = "";
        description = "";
        date = new Date();
        image = null;
    }

    public Workout(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }



}
