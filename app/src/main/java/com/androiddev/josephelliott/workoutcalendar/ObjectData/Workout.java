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
    private String location;
    private Date date;
    private Image image;

    public Workout() {
        title = "";
        description = "";
        location = "";
        date = new Date();
        image = null;
    }

    public Workout(String title, String description, String location, Date date, Image image) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.image = image;
    }



}
