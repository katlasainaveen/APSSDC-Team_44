package com.example.recipeapp2;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class MyDate {

    String date;
    String weekeday;
    String timestamp_time;
    long timestamp;

    public long timestamp() {
        long millis = System.currentTimeMillis();
        timestamp = millis / 1000;

        return timestamp;
    }

    public String getTimestamp_time(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("MMM dd, yyyy", cal).toString();

        timestamp_time = date;
        return timestamp_time;
    }

    public void setTimestamp_time(String timestamp_time) {
        this.timestamp_time = timestamp_time;
    }

    public String getWeekeday() {
        int val = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String[] day = {"Error", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        weekeday = day[val];
        return weekeday;
    }

    public void setWeekeday(String weekeday) {
        this.weekeday = weekeday;
    }

    public String getDate(int tag) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String[] month_array = {"Error", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        if (tag == 1) {
            date = String.valueOf(day) + " " + month_array[(month + 1)] + ", " + String.valueOf(year);
        } else if (tag == 2) {
            date = String.valueOf(day) + "-" + (month + 1) + "-" + String.valueOf(year);
        }

        return date;
    }

    public String getDateshort() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String[] month_array = {"Error", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        date = month_array[(month + 1)] + " " + String.valueOf(day) + ", " + String.valueOf(year);

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
