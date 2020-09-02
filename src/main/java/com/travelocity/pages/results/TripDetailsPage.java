package com.travelocity.pages.results;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.dto.TripDetails;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TripDetailsPage {

    @FindAll(@FindBy(xpath = "//*[@id = 'dxGroundTransportationModule']//*[@class = 'flex-card']"))
    private List<WebElement> carResults;

    @FindBy(xpath = "//*[contains(@class, 'flight-cards')]")
    private WebElement flightDetails;

    @FindBy(id = "departure-dates")
    private WebElement tripDate;
    @FindBy(id = "trip-summary-hotel-title")
    private WebElement tripHotelName;
    @FindBy(xpath = "//*[contains(@class, 'package-price-total')]//span[@class='visuallyhidden']")
    private WebElement tripTotalPrice;
    @FindBy(xpath = "//*[@id='FlightUDPBookNowButton1']/button")
    private WebElement btnNextFinalDetails;
    @FindBy(id = "hotelFeesTotal")
    private WebElement hotelFees;

    private WebAction webAction;

    public TripDetailsPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean areFlightDetailsPresent() {
        return webAction.isVisible(flightDetails, 40);
    }

    public boolean ableToSelectACar() {
        return carResults.size() > 0;
    }

    public void selectACar() throws WebDriverActionException {
        if(ableToSelectACar()) {
            WebElement car = carResults.get(0);
            webAction.click(car.findElement(By.xpath("//button[contains(@class, 'gt-add-btn')]")), 5);
        }
    }

    public boolean isHotelNameVisible() {
        return webAction.isVisible(tripHotelName, 2);
    }

    public boolean isTotalPricePresent() {
        try {
            String price = tripTotalPrice.getText();
            return ( price !=null && !price.isEmpty());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTripDatePresent() {
        return webAction.isVisible(tripDate, 5);
    }

    public boolean isHotelFeesPresent() {
        return webAction.isVisible(hotelFees, 5);
    }

    public ReviewAndBookPage clickNextFinalDetails() throws WebDriverActionException {
        webAction.click(btnNextFinalDetails, 2);
        webAction.sleep(2);
        return new ReviewAndBookPage(webAction);
    }

    public TripDetails getTripDetails() {
        return  new TripDetails(
                tripTotalPrice.getText(),
                tripHotelName.getText(),
                tripDate.getText(),
                hotelFees.getText()
        );
    }
}
