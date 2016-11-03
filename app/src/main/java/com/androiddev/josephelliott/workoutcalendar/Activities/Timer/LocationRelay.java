package com.androiddev.josephelliott.workoutcalendar.Activities.Timer;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

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
    private Criteria criteria;
    private Looper looper;

    /**
     * Constructs a new LocationRelay.
     * @param applicationContext The context in which this LocationRelay is running.
     */
    public LocationRelay(Context applicationContext) {
        this.applicationContext = applicationContext;
        locations = new ArrayList<>();

        setupGPS();
    }

    /**
     * Sets up the GPS information.
     * 1. Get the LocationManager from the system service.
     * 2. Setup the LocationListener.
     * 3. Setup the Criteria
     * 4. Setup the Looper
     */
    private void setupGPS() {
        // Get the LocationManager
        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);

        // Setup the LocationListener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locations.add(location);
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
        };

        // Setup the GPS Criteria
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        // Setup a null looper
        looper = null;
    }

    /**
     * Polls the GPS to get the user's location.
     * This poll is added to a list (in chronological order) of locations.
     * @return True if successfully retrieved a location, false otherwise.
     */
    public boolean poll() {
        try {
            locationManager.requestSingleUpdate(criteria, locationListener, looper);
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the list of locations gathered by the location manager.
     * This list is in chronological order (following the properties of ArrayLists)
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * // TODO
     * Calculates the distance ran based off of the gathered locations.
     * @return The total distance between the starting point and the end point, and every point in between.
     */
    public double calculateDistance() {
        return 0.0;
    }

    /**
     * // TODO
     * Estimates where the user was running based off the gathered GPS locations.
     * @return A string of the user's location.
     */
    public String estimateRunningLocation() {
        return "default_location";
    }

    /**
     * Empties the location manager of all recorded locations.
     */
    public void reset() {
        locations = new ArrayList<>();
    }
}
