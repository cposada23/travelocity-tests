package com.travelocity.actions.utils.browserimpl;

import com.travelocity.actions.utils.IBrowser;
import com.travelocity.utilities.exceptions.DriverException;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class FirefoxBrowser implements IBrowser {

    @Override
    public WebDriver configureLocal() throws DriverException {
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        return new FirefoxDriver();
    }

    @Override
    public WebDriver configureRemote() throws DriverException {
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
        } catch (Exception e ){
            throw new DriverException("Failed configuration for remote firefox", e);
        }
    }
}
