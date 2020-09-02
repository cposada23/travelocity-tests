package com.travelocity.utilities.exceptions;

public class DriverException extends Exception {
    public DriverException(String s) {
        super(s);
    }

    public DriverException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
