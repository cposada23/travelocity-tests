package com.travelocity.utilities.exceptions;

public class UserIsLoggedInException extends Exception {
    public UserIsLoggedInException(String s) {
        super(s);
    }

    public UserIsLoggedInException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
