package com.travelocity.pages.home.tabs;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.components.DatePickerPageComponent;
import com.travelocity.pages.components.FlyingOptionsComponent;
import com.travelocity.pages.results.hotel.HotelsResultsPage;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class VacationPackagePage {

    @FindBy(xpath = "//input[@id='fh-fh-hp-package']/..")
    private WebElement buttonFlightPlusHotel;

    @FindBy(id = "package-origin-hp-package")
    private WebElement inputFlyingFrom;
    @FindBy(id = "package-destination-hp-package")
    private WebElement inputFlyingTo;

    @FindBy(id = "package-1-adults-hp-package")
    private WebElement selectAdults;

    @FindBy(id = "package-departing-wrapper-hp-package")
    private WebElement flightDepartingDatePicker;

    @FindBy(id = "package-returning-wrapper-hp-package")
    private WebElement flightReturningDatePicker;

    @FindBy(id = "search-button-hp-package")
    private WebElement searchButton;

    private WebAction webAction;

    public VacationPackagePage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void clickButtonFlightPlusHotel() throws WebDriverActionException {
        webAction.isVisible(buttonFlightPlusHotel, 10);
        webAction.clickJS(buttonFlightPlusHotel);
    }

    public void selectFlyingFrom(String from) throws WebDriverActionException {
        webAction.sendText(inputFlyingFrom, from, 5);
        FlyingOptionsComponent.waitOptionsToSelect(webAction.getDriver());
        inputFlyingFrom.sendKeys(Keys.ENTER);
    }

    public void selectFlyingTo(String to) throws WebDriverActionException {
        webAction.sendText(inputFlyingTo, to, 5);
        FlyingOptionsComponent.waitOptionsToSelect(webAction.getDriver());
        inputFlyingTo.sendKeys(Keys.ENTER);
    }

    public void selectNumberOfAdults(String numberOfAdults) {
        Select select = new Select(selectAdults);
        select.selectByValue(numberOfAdults);
    }

    public DatePickerPageComponent showFlightDepartingDatePicker() throws WebDriverActionException {
        webAction.click(flightDepartingDatePicker, 5);
        return new DatePickerPageComponent(webAction);
    }

    public DatePickerPageComponent showFlightReturningDatePicker() throws WebDriverActionException {
        webAction.click(flightReturningDatePicker, 5);
        webAction.sleep(2);
        return new DatePickerPageComponent(webAction);
    }

    public HotelsResultsPage clickSearchButton() throws WebDriverActionException {
        webAction.click(searchButton, 5);
        return new HotelsResultsPage(webAction);
    }


}
