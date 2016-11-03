package com.androiddev.josephelliott.workoutcalendar.Activities.Timer;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;

import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.Calendar;

/**
 * Created by jellio on 10/31/16.
 */

class CustomChronometer extends Chronometer {

    /**
     * Interface used for adding an "On Interval, do this" functionality.
     */
    public interface OnIntervalUpdateListener {
        void onIntervalUpdate();
    }

    public interface OnStartListener {
        void onStart();
    }

    public interface OnPauseListener {
        void onPause();
    }

    /*** The function to execute every update. ***/
    private OnIntervalUpdateListener updateListener;
    private OnStartListener startListener;
    private OnPauseListener pauseListener;

    /*** How we know if the chronometer is running ***/
    boolean running = false;

    /*** How often the updateListener will trigger. ***/
    private long interval;

    /*** The start time ***/
    private long timeBase, timeElapsed;
    private int counter;

    /*** Constructors ***/
    public CustomChronometer(Context context) {
        super(context);
        setIntervalFunctionality();
        setTouchFunctionality();
        reset();
    }

    public CustomChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setIntervalFunctionality();
        setTouchFunctionality();
        reset();
    }

    /**
     * Starts the chronometer.
     */
    @Override
    public void start() {
        timeBase = SystemClock.elapsedRealtime() + timeElapsed;
        setBase(timeBase);

        running = true;

        startListener.onStart();

        super.start();
    }

    /**
     * Pauses the chronometer.
     */
    public void pause() {
        timeElapsed = getBase() - SystemClock.elapsedRealtime();

        running = false;

        pauseListener.onPause();

        stop();
    }

    /**
     * Resets the chronometer back to it's base time.
     * This will NOT remove the OnIntervalUpdate listener.
     */
    public void reset() {
        stop();
        running = false;
        timeBase  = 0;
        timeElapsed = 0;
        counter = 0;
        setText("00:00");
    }

    /**
     * Returns the total amount of time elapsed while the chronometer was running.
     * @return
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Sets an update interval. Every X milliseconds that pass, the Chronometer will notify
     * the object.
     * @param milliseconds The time in between each update.
     */
    public void setUpdateInterval(long milliseconds) {
        this.interval = milliseconds;
    }

    /**
     * Sets an update listener. This will not execute unless setUpdateInterval is called with an
     * interval time greater than 0 milliseconds.
     * @param updateListener The updateListener to override.
     */
    public void setOnIntervalUpdate(OnIntervalUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * Sets a pause listener. Whenever pause() is called, this listener will be called.
     * @param pauseListener The pauseListener to override.
     */
    public void setPauseListener(OnPauseListener pauseListener) {
        this.pauseListener = pauseListener;
    }

    /**
     * Sets a start listener. Whenever start() is called, this listener will be called.
     * @param startListener The startListener to override.
     */
    public void setStartListener(OnStartListener startListener) {
        this.startListener = startListener;
    }

    /**
     * The meat behind the "OnIntervalUpdate". Whenever the counter reaches the right number of
     * seconds, the updateListener is called.
     */
    private void setIntervalFunctionality() {
        setOnChronometerTickListener(new OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                counter++;
                if (counter == interval / 1000) {
                    updateListener.onIntervalUpdate();
                    counter = 0;
                    Log.d("UPDATE", "launched an update after " + interval + " milliseconds");
                }
            }
        });
    }

    /**
     * Adds the touch functionality to the chronometer. Two basic things are to happen.
     * 1. On action down and up, highlight the chronometer.
     * 2. On action up, either start or pause the chronometer.
     */
    private void setTouchFunctionality() {
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color to the primary for the 'highlight' feel
                        setTextColor(getResources().getColor(R.color.primary, null));
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Change the color back to normal
                        setTextColor(getResources().getColor(R.color.accent, null));

                        if (running)
                            pause();
                        else
                            start();

                        return true;

                }
                return false;
            }
        });
    }

}
