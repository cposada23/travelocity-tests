package com.travelocity.pages.results.hotel;

import com.travelocity.actions.WebAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HotelListPage {

    @FindBy(id = "sort")
    private WebElement selectSortBy;
    @FindBy(id = "submitBtn")
    private WebElement getTheAppButton;
    @FindBy(xpath = "//section[@class='results']")
    private WebElement resultsContainer;
    @FindAll(@FindBy(xpath = "//section[@class='results']//li[contains(@class, 'listing')]"))
    private List<WebElement> hotels;

    private WebAction webAction;

    public HotelListPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void waitForResults() {
        WebDriverWait wait = new WebDriverWait(webAction.getDriver(), 30);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//section[@class='results']//li[contains(@class, 'listing')]"), 0 ));
    }

    public boolean areResultsShowing() {
        return webAction.isVisible(resultsContainer, 20) && hotels.size() > 0;
    }

    public String getOptionSelectedSelectSortBy() {
        Select select = new Select(selectSortBy);
        return select.getFirstSelectedOption().getAttribute("value");
    }

    public boolean isGetTheAppButtonVisible() {
        JavascriptExecutor js = (JavascriptExecutor)webAction.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getTheAppButton);
        return webAction.isVisible(getTheAppButton, 5);
    }

}
