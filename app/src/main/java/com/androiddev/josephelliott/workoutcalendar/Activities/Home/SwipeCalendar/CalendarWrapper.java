package com.androiddev.josephelliott.workoutcalendar.Activities.Home.SwipeCalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joseph Elliott on 10/9/2015.
 */
public class CalendarWrapper {

    private Calendar calendar;

    /**
     * Creates the calendar.
     * Defaults to today's date.*/
    public CalendarWrapper() {
        calendar = Calendar.getInstance();
    }

    public CalendarWrapper(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        this.calendar = calendar;
    }

    /*** Date object related methods ***/
    public Date getDate() {
        return calendar.getTime();
    }

    public void setTodaysDate() {
        calendar = Calendar.getInstance();
    }

    public static long getTodaysDate() {
        return Calendar.getInstance().getTimeInMillis();
    }


    /*** Day related methods ***/
    public void setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /*** Month related methods ***/
    public void addMonth() {
        calendar.add(Calendar.MONTH, 1);
    }

    public void subtractMonth() {
        calendar.add(Calendar.MONTH, -1);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
    }

    public String getMonthText() {
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    }


    /*** Year related methods ***/
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }


    /*** Added functionality methods ***/
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
