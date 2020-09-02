package com.travelocity.pages.home.tabs;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.components.DatePickerPageComponent;
import com.travelocity.pages.components.FlyingOptionsComponent;
import com.travelocity.pages.results.FlightsResultsPage;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class FlightsTabPage {

    @FindBy(id = "flight-type-roundtrip-hp-flight")
    private WebElement roundTripButton;

    @FindBy(id = "flight-origin-hp-flight")
    private WebElement flyingFromInput;

    @FindBy(id = "flight-destination-hp-flight")
    private WebElement flyingToInput;

    @FindBy(id = "flight-departing-wrapper-hp-flight")
    private WebElement flightDepartingDatePicker;

    @FindBy(id = "flight-returning-wrapper-hp-flight")
    private WebElement flightReturningDatePicker;

    @FindBy(id = "flight-adults-hp-flight")
    private WebElement selectAdults;

    @FindBy(xpath = "//form[@id = 'gcw-flights-form-hp-flight']//button[@type='submit']")
    private WebElement buttonSubmit;

    @FindBy(id = "flight-add-hotel-checkbox-hp-flight")
    private WebElement checkAddAHotel;

    @FindBy(id = "flight-hotel-checkin-wrapper-hp-flight")
    private WebElement checkInDate;

    @FindBy(id = "flight-hotel-checkout-wrapper-hp-flight")
    private WebElement checkOutDate;

    @FindBy(xpath = "//*[contains(@class, 'alert alert-error')]//*[contains(text(), 'Dates must be between')]")
    private WebElement alertErrorDates;


    private WebAction webAction;
    public FlightsTabPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean verifyFightsTabIsShowing() {
        return webAction.isVisible(roundTripButton, 5);
    }

    public void clickRoundTrip() throws WebDriverActionException {
        webAction.clickJS(roundTripButton);
    }

    public void selectFlyingFrom(String from) throws WebDriverActionException {
        webAction.sendText(flyingFromInput, from, 5);
        FlyingOptionsComponent.waitOptionsToSelect(webAction.getDriver());
        flyingFromInput.sendKeys(Keys.ENTER);
    }

    public void selectFlyingTo(String to) throws WebDriverActionException {
        webAction.sendText(flyingToInput, to, 5);
        FlyingOptionsComponent.waitOptionsToSelect(webAction.getDriver());
        flyingToInput.sendKeys(Keys.ENTER);
    }



    public DatePickerPageComponent showFlightDepartingDatePicker() throws WebDriverActionException {
        webAction.click(flightDepartingDatePicker, 5);
        return new DatePickerPageComponent(webAction);
    }

    public DatePickerPageComponent showFlightReturningDatePicker() throws WebDriverActionException {
        webAction.click(flightReturningDatePicker, 5);
        return new DatePickerPageComponent(webAction);
    }

    public DatePickerPageComponent showCheckIn() throws WebDriverActionException {
        webAction.click(checkInDate, 5);
        return new DatePickerPageComponent(webAction);
    }

    public DatePickerPageComponent showCheckOut() throws WebDriverActionException {
        webAction.click(checkOutDate, 5);
        return new DatePickerPageComponent(webAction);
    }

    public void selectNumberOfAdults(String numberOfAdults) {
        Select select = new Select(selectAdults);
        select.selectByValue(numberOfAdults);
    }

    public FlightsResultsPage submitForm() throws WebDriverActionException {
        webAction.click(buttonSubmit, 5);
        return new FlightsResultsPage(webAction);
    }

    public void checkAddAHotel() throws WebDriverActionException {
        webAction.clickJS(checkAddAHotel);
    }

    public boolean isAlertErrorPresent() {
        return webAction.isVisible(alertErrorDates, 4);
    }

}
