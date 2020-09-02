package com.travelocity.utilities.exceptions;

public class WebDriverActionException extends Exception {
    public WebDriverActionException(String s) {
        super(s);
    }

    public WebDriverActionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
