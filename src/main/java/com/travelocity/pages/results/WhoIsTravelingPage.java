package com.travelocity.pages.results;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WhoIsTravelingPage {

    @FindBy(xpath = "//*[text() = 'Continue Booking' and @class='button-text']")
    private WebElement continueBookingButtonModal;

    @FindBy(id = "firstname[0]")
    private WebElement firstNameInput;

    @FindBy(id = "lastname[0]")
    private WebElement lastNameInput;

    @FindBy(id = "phone-number[0]")
    private WebElement phoneNumberInput;

    @FindBy(name = "cardholder_name")
    private WebElement nameOnCard;

    @FindBy(id = "complete-booking")
    private WebElement completeBookingButton;

    private WebAction webAction;
    public WhoIsTravelingPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void waitForWarnModal() throws WebDriverActionException {
        if(webAction.isVisible(continueBookingButtonModal, 20)) {
            webAction.click(continueBookingButtonModal, 5);
        }
    }

    public boolean isFirstNameInputVisible() {
        return webAction.isVisible(firstNameInput, 30);
    }

    public boolean isLastNameInputVisible() {
        return webAction.isVisible(lastNameInput, 5);
    }

    public boolean isPhoneNumberInputInputVisible() {
        return webAction.isVisible(phoneNumberInput, 5);
    }


    public boolean isCompleteBookingButtonVisible() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webAction.getDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", completeBookingButton);
        return webAction.isVisible(completeBookingButton, 5);
    }


}
