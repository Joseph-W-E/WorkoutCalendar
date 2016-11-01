package com.androiddev.josephelliott.workoutcalendar.Activities.Timer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by jellio on 10/31/16.
 */

public class LocationRelay {

    /**
     * The context in which this LocationRelay is running.
     */
    private Context applicationContext;

    /**
     * The list of gathered locations.
     */
    private ArrayList<Location> locations;

    private LocationManager  locationManager;
    private LocationListener locationListener;

    /**
     * Constructs a new LocationRelay.
     * @param applicationContext The context in which this LocationRelay is running.
     */
    public LocationRelay(Context applicationContext) {
        this.applicationContext = applicationContext;
        locations = new ArrayList<>();

        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // called when a new location is found by the GPS
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Polls the GPS to get the user's location.
     * This poll is added to a list (in chronological order) of locations.
     * @return True if successfully retrieved a location, false otherwise.
     */
    public boolean poll() {
        return false;
    }

    /**
     * Returns the list of locations gathered by the location manager.
     * This list is in chronological order (following the properties of ArrayLists)
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * Estimates where the user was running based off the gathered GPS locations.
     * @return A string of the user's location.
     */
    public String estimateRunningLocation() {
        return null;
    }

    /**
     * Empties the location manager of all recorded locations.
     */
    public void reset() {
        locations = new ArrayList<>();
    }
}
