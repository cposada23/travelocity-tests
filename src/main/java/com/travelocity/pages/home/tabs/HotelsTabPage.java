package com.travelocity.pages.home.tabs;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.components.DatePickerPageComponent;
import com.travelocity.pages.components.FlyingOptionsComponent;
import com.travelocity.pages.results.hotel.HotelListPage;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HotelsTabPage {
    @FindBy(id = "hotel-destination-hp-hotel")
    private WebElement hotelDesitnationInput;
    @FindBy(id = "hotel-checkin-wrapper-hp-hotel")
    private WebElement hotelCheckIn;
    @FindBy(id = "hotel-checkout-wrapper-hp-hotel")
    private WebElement hotelCheckOut;
    @FindBy(id = "hotel-1-adults-hp-hotel")
    private WebElement selectAdults;

    @FindBy(xpath = "//form[@id = 'gcw-hotel-form-hp-hotel']//button[@type='submit']")
    private WebElement buttonSubmit;

    private WebAction webAction;

    public HotelsTabPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void selectGoingTo(String goingTo) throws WebDriverActionException {
        webAction.sendText(hotelDesitnationInput, goingTo, 5);
        FlyingOptionsComponent.waitOptionsToSelect(webAction.getDriver());
        hotelDesitnationInput.sendKeys(Keys.ENTER);
    }

    public DatePickerPageComponent showCheckInDates() throws WebDriverActionException {
        webAction.click(hotelCheckIn, 5);
        return new DatePickerPageComponent(webAction);
    }

    public DatePickerPageComponent showCheckOutDates() throws WebDriverActionException {
        webAction.click(hotelCheckOut, 5);
        return new DatePickerPageComponent(webAction);
    }
    public void selectNumberOfAdults(String numberOfAdults) {
        Select select = new Select(selectAdults);
        select.selectByValue(numberOfAdults);
    }

    public HotelListPage clickSearch() throws WebDriverActionException {
        webAction.click(buttonSubmit, 5);
        webAction.sleep(4);
        return new HotelListPage(webAction);
    }


}
