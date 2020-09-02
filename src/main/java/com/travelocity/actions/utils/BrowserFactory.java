package com.travelocity.actions.utils;

public class BrowserFactory {

    public IBrowser getBrowser(BrowserEnum browserEnum) {
        return browserEnum.getInstance();
    }

}
