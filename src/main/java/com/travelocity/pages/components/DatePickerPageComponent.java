package com.travelocity.pages.components;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DatePickerPageComponent {

    @FindBy(xpath = "//*[contains(@class, 'datepicker-dropdown') and not(@aria-hidden)]")
    private WebElement datePickerDropDown;

    @FindBy(xpath = "(//*[contains(@class, 'datepicker-dropdown') and not(@aria-hidden)]//*[@class = 'datepicker-cal-month'])[1]")
    private WebElement currentMonth;

    @FindBy(xpath = "(//*[contains(@class, 'datepicker-dropdown') and not(@aria-hidden)]//*[@class = 'datepicker-cal-month'])[1]//*[contains(@class, 'cal-month-header')]")
    private WebElement currentMonthHeader;

    @FindAll
    (@FindBy(xpath = "(//*[contains(@class, 'datepicker-dropdown') and not(@aria-hidden)]//*[@class = 'datepicker-cal-month'])[1]//*[contains(@class, 'datepicker-day-number')]/button[not(@disabled)]"))
    private List<WebElement> monthDayNumbers;

    @FindBy(xpath = "//*[contains(@class, 'datepicker-dropdown') and not(@aria-hidden)]//button[contains(@class, 'next')]")
    private WebElement nextMonthButton;

    private WebAction webAction;

    public DatePickerPageComponent(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean isDatePickerShowing() {
        return webAction.isVisible(datePickerDropDown, 5);
    }

    public void lookForMonth(String month) throws WebDriverActionException {

        int maxNumberOfCycles = 13;
        while (!currentMonthHeader.getText().equals(month) && maxNumberOfCycles > 0) {
            webAction.clickJS(nextMonthButton);
            maxNumberOfCycles--;
        }
        if(maxNumberOfCycles == 0) {
            throw new WebDriverActionException("Month " + month + "could no be selected from date picker");
        }
    }

    public void selectDayOfTheMonth(String day) throws WebDriverActionException {
        for (WebElement monthDay: monthDayNumbers) {
            if(monthDay.getAttribute("data-day").equals(day)) {
                webAction.clickJS(monthDay);
                return;
            }
        }
        throw new WebDriverActionException("Couldn't select the day of the month on the calendar");
    }

}
