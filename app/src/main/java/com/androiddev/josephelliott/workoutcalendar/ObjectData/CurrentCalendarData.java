package com.androiddev.josephelliott.workoutcalendar.ObjectData;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joseph Elliott on 10/9/2015.
 */
public class CurrentCalendarData {

    Calendar calendar;

    /**
     * Creates the calendar.
     * Defaults to today's date.*/
    public CurrentCalendarData() {
        calendar = Calendar.getInstance();
    }

    public CurrentCalendarData(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        this.calendar = calendar;
    }

    public Date getDateObj() {
        return calendar.getTime();
    }

    public void setTodaysDate() {
        calendar = Calendar.getInstance();
    }

    public static long getTodaysDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public void addMonth() {
        calendar.add(Calendar.MONTH, 1);
    }

    public void subtractMonth() {
        calendar.add(Calendar.MONTH, -1);
    }

    public void setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public String getMonthText() {
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getFirstDayOfMonth() {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        temp.set(Calendar.DAY_OF_MONTH, 1);
        return temp.get(Calendar.DAY_OF_WEEK);
    }

    public int getNumberOfDaysInMonth() {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        return temp.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
