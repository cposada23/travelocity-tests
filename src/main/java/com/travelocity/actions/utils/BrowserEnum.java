package com.travelocity.actions.utils;

import com.travelocity.actions.utils.browserimpl.ChromeBrowser;
import com.travelocity.actions.utils.browserimpl.FirefoxBrowser;

import java.util.function.Supplier;

/**
 * Enum for the supported browsers for the tests executions
 */
public enum BrowserEnum {
    CHROME("chrome", ChromeBrowser::new),
    FIREFOX("firefox", FirefoxBrowser::new);

    private String browser;
    private final Supplier<IBrowser> instantiator;

    BrowserEnum(String browser, Supplier<IBrowser> instatiator) {
        this.browser = browser;
        this.instantiator = instatiator;
    }

    public static BrowserEnum of(String value) {
        for (BrowserEnum browserEnum : values()) {
            if(browserEnum.browser.equalsIgnoreCase(value)) {
                return browserEnum;
            }
        }
        throw new IllegalArgumentException("There is no supported browser with the value ".concat(value));
    }

    public IBrowser getInstance() {
        return instantiator.get();
    }

}
