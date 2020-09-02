package com.travelocity.utilities.exceptions;

public class DatePickerException extends Exception {
    public DatePickerException(String s) {
        super(s);
    }

    public DatePickerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
