package com.travelocity.pages.results.cruises;

import com.travelocity.actions.WebAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChooseCabinPage {

    @FindBy(xpath = "//*[@class= 'title-on-ship-image']")
    private WebElement titleOfShip;

    private WebAction webAction;

    public ChooseCabinPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public void changeToNewPage() {
        webAction.switchToNewWindow();
    }

    public String getTitleOfShip() {
        webAction.isVisible(titleOfShip, 40);
        return titleOfShip.getText();
    }

}
