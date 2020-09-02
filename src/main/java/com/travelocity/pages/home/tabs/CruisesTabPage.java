package com.travelocity.pages.home.tabs;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.components.DatePickerPageComponent;
import com.travelocity.pages.results.cruises.CruisesResultPage;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CruisesTabPage {

    @FindBy(id = "cruise-destination-hp-cruise")
    private WebElement selectCruiseDestination;

    @FindBy(id = "cruise-start-date-wrapper-hp-cruise")
    private WebElement departDate;


    @FindBy(xpath = "//form[@id = 'gcw-cruises-form-hp-cruise']//button[@type='submit']")
    private WebElement buttonSubmit;

    private WebAction webAction;

    public CruisesTabPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public DatePickerPageComponent showDepartDate() throws WebDriverActionException {
        webAction.click(departDate, 2);
        return new DatePickerPageComponent(webAction);
    }

    public void selectCruiseDestination(String destination) {
        Select select = new Select(selectCruiseDestination);
        select.selectByValue(destination);
    }

    public CruisesResultPage submitForm() throws WebDriverActionException {
        webAction.click(buttonSubmit, 4);
        return new CruisesResultPage(webAction);
    }
}
