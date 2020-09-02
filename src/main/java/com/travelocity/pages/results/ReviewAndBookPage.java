package com.travelocity.pages.results;

import com.travelocity.actions.WebAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReviewAndBookPage {

    @FindBy(xpath = "//*[@id='trip-summary']/div[2]/div/a")
    private WebElement tripHotelName;

    @FindBy(xpath = "//*[@data-price-update='totalIncludedHotelChargedFees']")
    private WebElement tripHotelFees;

    @FindBy(xpath = "//*[@data-price-update='tripTotal']")
    private WebElement tripTotal;

    @FindBy(id = "firstname[0]")
    private WebElement firstNameInput;

    @FindBy(id = "lastname[0]")
    private WebElement lastNameInput;

    @FindBy(name = "tripPreferencesRequests[0].airTripPreferencesRequest.travelerPreferences[0].phoneNumber")
    private WebElement phoneNumberInput;



    private WebAction webAction;

    public ReviewAndBookPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean isFirstNameInputPresent() {
        return webAction.isVisible(firstNameInput, 5);
    }
    public boolean isLastNameInputPresent() {
        return webAction.isVisible(lastNameInput, 5);
    }
    public boolean isPhoneInputPresent() {
        return webAction.isVisible(phoneNumberInput, 5);
    }

    public boolean isPageLoaded() {
        return webAction.isVisible(tripTotal, 30);
    }

    public boolean isTotalPriceCorrect(String expectedTotalPrice) {
        return tripTotal.getText().equals(expectedTotalPrice);
    }

    public boolean isHotelFeesCorrect(String hotelFees) {
        return tripHotelFees.getText().equals(hotelFees);
    }

    public boolean isHotelNameCorrect(String hotelName) {
        return tripHotelName.getText().equals(hotelName);
    }



}
