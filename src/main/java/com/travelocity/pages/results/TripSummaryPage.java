package com.travelocity.pages.results;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.stream.Collectors;

public class TripSummaryPage {

    @FindBy(xpath = "//section[@class = 'tripSummaryContainer desktopView']//*[@class='tripSummaryHeader']")
    private WebElement tripSummaryHeader;

    @FindBy(xpath = "//section[@class = 'tripSummaryContainer desktopView']//*[@class='packagePriceTotal']")
    private WebElement tripTotal;

    @FindBy(xpath = "//section[@class = 'tripSummaryContainer desktopView']//*[@class='priceGuarantee']")
    private WebElement priceGuaranteeTag;

    @FindBy(xpath = "//*[text() = 'Departure']/..")
    private WebElement departureInfo;

    @FindBy(xpath = "//*[text() = 'Return']/..")
    private WebElement returnInfo;

    @FindBy(id = "bookButton")
    private WebElement continueBookingButton;

    private WebAction webAction;
    private String parentHandler;
    public TripSummaryPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void switchToTripSummaryPage() {
        webAction.switchToNewWindow();
    }

    public boolean isTripSummaryVisible() {
        return webAction.isVisible(tripSummaryHeader, 30);
    }

    public boolean tripTotalIsPresent( ) {
        return webAction.isVisible(tripTotal, 5);
    }

    public boolean isDepartureInfoVisible( ) {
        return webAction.isVisible(departureInfo, 5);
    }
    public boolean isReturnInfoVisible( ) {
        return webAction.isVisible(returnInfo, 5);
    }
    public boolean isReturnPriceGuaranteeVisible() {
        return webAction.isVisible(priceGuaranteeTag, 5);
    }

    public WhoIsTravelingPage pressContinueBooking() throws WebDriverActionException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webAction.getDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", continueBookingButton);
        webAction.click(continueBookingButton, 5);
        webAction.sleep(5);
        return new WhoIsTravelingPage(webAction);
    }
}
