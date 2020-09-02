package com.travelocity.utilities.dto;

public class FlightDuration {
    private int hours;
    private int minutes;

    public FlightDuration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
}
