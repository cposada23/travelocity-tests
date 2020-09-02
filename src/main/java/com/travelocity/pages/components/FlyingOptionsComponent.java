package com.travelocity.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class FlyingOptionsComponent {

    public static void waitOptionsToSelect(WebDriver driver) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("No options where displayed to select location");
        wait.until(webDriver -> webDriver.findElements(By.xpath("//*[contains(@id, 'aria-option')]")).size() > 0);
    }

}
