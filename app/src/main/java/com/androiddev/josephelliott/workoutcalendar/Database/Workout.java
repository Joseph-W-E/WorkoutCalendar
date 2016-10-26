package com.androiddev.josephelliott.workoutcalendar.Database;

import android.media.Image;

import java.util.Date;

/**
 * Created by Joseph Elliott on 10/12/2015.
 */
public class Workout {

    private long   ID;
    private String title;
    private String description;
    private String location;
    private Date   date;
    private Image  image;
    private double distance;

    public Workout() {
        title = "";
        description = "";
        location = "";
        date = new Date();
        image = null;
        distance = 0.0;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(long ms) {
        this.date = new Date(ms);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
