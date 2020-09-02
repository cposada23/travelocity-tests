package com.travelocity.actions.utils.browserimpl;

import com.travelocity.actions.utils.IBrowser;
import com.travelocity.utilities.exceptions.DriverException;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class ChromeBrowser implements IBrowser {

    @Override
    public WebDriver configureLocal() throws DriverException {
        try {
            ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
            return new ChromeDriver();
        }catch (Exception e) {
            throw new DriverException("There was an error configuring chrome for local testing", e);
        }
    }

    @Override
    public WebDriver configureRemote() throws DriverException {
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        } catch (Exception e ){
            throw new DriverException("Failed configuration for remote chrome", e);
        }
    }
}
