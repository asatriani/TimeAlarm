package com.italtel.iot.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by satriani on 26/02/2018.
 */
public class TimeAlarm {

    @Min(0)
    @Max(23)
    private int hours;
    @Min(0)
    @Max(59)
    private int minutes;

    public TimeAlarm() {
    }

    public TimeAlarm(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return new StringBuilder(hours).append(":").append(minutes).toString();
    }
}
