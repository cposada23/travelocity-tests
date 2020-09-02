package com.travelocity.actions.utils;

import com.travelocity.utilities.exceptions.DriverException;
import org.openqa.selenium.WebDriver;

public interface IBrowser {

    WebDriver configureLocal() throws DriverException;

    WebDriver configureRemote() throws DriverException;

}
