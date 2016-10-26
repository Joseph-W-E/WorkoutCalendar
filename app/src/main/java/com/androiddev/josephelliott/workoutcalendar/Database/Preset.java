package com.androiddev.josephelliott.workoutcalendar.Database;


import java.util.Date;

/**
 * Created by jellio on 9/23/16.
 */

public class Preset {

    private long   ID;
    private String title;
    private String location;
    private String description;
    private String frequency;
    private Date expires;

    public Preset() {
        title = "";
        location = "";
        description = "";
        frequency = "";
        expires = new Date();
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

}
