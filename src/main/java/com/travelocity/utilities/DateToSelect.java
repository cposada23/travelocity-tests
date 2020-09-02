package com.travelocity.utilities;

public class DateToSelect {
    private String month; // ex: May 2020
    private String day;

    public DateToSelect(String month, String day) {
        this.month = month;
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}
