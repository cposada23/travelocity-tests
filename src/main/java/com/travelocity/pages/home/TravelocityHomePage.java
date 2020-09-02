package com.travelocity.pages.home;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.home.tabs.CruisesTabPage;
import com.travelocity.pages.home.tabs.FlightsTabPage;
import com.travelocity.pages.home.tabs.HotelsTabPage;
import com.travelocity.pages.home.tabs.VacationPackagePage;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TravelocityHomePage {

    @FindBy(id = "header-account-menu")
    private WebElement accountMenu;

    @FindBy(id = "account-signin")
    private WebElement signinButton;

    // general buttons
    @FindBy(id = "tab-flight-tab-hp")
    private WebElement flightsTabButon;

    @FindBy(id = "tab-package-tab-hp")
    private WebElement vacationPackageTabButton;

    @FindBy(id = "tab-hotel-tab-hp")
    private WebElement hotelTabButton;

    @FindBy(id = "tab-cruise-tab-hp")
    private WebElement cruisesTabButton;

    private WebAction webAction;
    public TravelocityHomePage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean isLoggedIn() throws WebDriverActionException {
        webAction.click(accountMenu, 10);
        return !webAction.isVisible(signinButton, 5);
    }

    public FlightsTabPage enterFlightsTab() throws WebDriverActionException {
        webAction.click(flightsTabButon, 20);
        return new FlightsTabPage(webAction);
    }

    public VacationPackagePage enterVacationPackagesTab() throws WebDriverActionException {
        webAction.click(vacationPackageTabButton, 10);
        return new VacationPackagePage(webAction);
    }
    public HotelsTabPage enterHotelsTab() throws WebDriverActionException {
        webAction.click(hotelTabButton, 10);
        return new HotelsTabPage(webAction);
    }
    public CruisesTabPage enterCruisesTab() throws WebDriverActionException {
        webAction.click(cruisesTabButton, 10);
        webAction.sleep(6);
        return new CruisesTabPage(webAction);
    }

}
