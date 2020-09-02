package com.travelocity.actions;

import com.travelocity.actions.utils.*;
import com.travelocity.utilities.exceptions.DriverException;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * this class is a wrapper for the Selenium actions on the browser
 * here i handle the explicit waits in case that they are necessary
 * @Autor Camilo Posada Angel camilo.posada@globant.com
 */
public class WebAction {

    private WebDriver driver;
    private WebDriverWait wait;

    public WebAction(BrowserEnum browserEnum) throws DriverException {
        this(browserEnum, false);
    }

    public WebAction(BrowserEnum browserEnum, boolean isRemote) throws DriverException {
        BrowserFactory browserFactory = new BrowserFactory();
        IBrowser webBrowser = browserFactory.getBrowser(browserEnum);
        if (isRemote) this.driver = webBrowser.configureRemote();
        else this.driver = webBrowser.configureLocal();
    }

    private WebDriverWait getWait(int seconds) {
        return new WebDriverWait(this.driver, seconds);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void disposeDriver() {
        if(this.driver != null) {
            this.driver.quit();
        }
    }

    protected static <T> T validateInstance(T instance) {
        return Optional.ofNullable(instance)
                .orElseThrow(() -> new NullPointerException("you must instantiate the driver first before using it"));
    }

    public void click(WebElement element, int timeout) throws WebDriverActionException {
        try {
            getWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            throw new WebDriverActionException("The webElement could not be clicked", e);
        }
    }

    public void clickJS(WebElement element) throws WebDriverActionException {
        try {
            validateInstance(driver);
            JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
            jsExecutor.executeScript("arguments[0].click()", element);
        }catch (Exception e) {
            throw new WebDriverActionException("Element could not be clicked with js", e);
        }
    }

    public boolean isVisible(WebElement element, int timeout) {
        try {
            validateInstance(driver);
            getWait(timeout).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendText(WebElement element, String text, int timeout) throws WebDriverActionException {
        try {
            validateInstance(driver);
            getWait(timeout).until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(text);
        } catch (Exception e) {
            throw new WebDriverActionException("Could not send text to webElement", e);
        }
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e) {

        }
    }

    public void switchToNewWindow() {
        String parentHandler = driver.getWindowHandle();
        String windowHandler = driver.getWindowHandles().stream()
                .filter(i -> !i.equals(parentHandler))
                .collect(Collectors.joining());
        driver.switchTo().window(windowHandler);
    }
}
