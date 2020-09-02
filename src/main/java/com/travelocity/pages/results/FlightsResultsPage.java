package com.travelocity.pages.results;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.dto.FlightDuration;
import com.travelocity.utilities.TimeHelper;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class FlightsResultsPage {

    @FindBy(xpath = "//*[@id='titleBar']//*[contains(@class, 'title-city-text')]")
    private WebElement titleCityText;

    @FindBy(id = "sortDropdown")
    private WebElement selectSortBy;

    @FindBy(id = "flightModuleList")
    private WebElement resultsContainer;

    @FindAll(@FindBy(xpath = "//*[@id='flightModuleList']/li[@data-test-id='offer-listing']"))
    private List<WebElement> results;

    private WebAction webAction;
    public FlightsResultsPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean areResultsShowing() {
        return webAction.isVisible(resultsContainer, 20) && results.size() > 0;
    }

    public void waitForResults() {
        WebDriverWait wait = new WebDriverWait(webAction.getDriver(), 30);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[@id='flightModuleList']/li[@data-test-id='offer-listing']"), 0 ));
    }

    public List<String> getSortByOptions() {
        Select select = new Select(selectSortBy);
        List<String> options = new ArrayList<>();
        select.getOptions().forEach(option -> {
            options.add(option.getText().split(" ")[0].toLowerCase());
        });
        return options;
    }

    public boolean selectButtonIsPresentInEveryResult() {
        boolean buttonIsPresentInAllResults = true;
        for(WebElement result: results) {
            try {
                result.findElement(By.xpath(".//button[@data-test-id='select-button']"));
            }catch (Exception e) {
                buttonIsPresentInAllResults = false;
            }
        }
        return buttonIsPresentInAllResults;
    }

    public boolean durationIsPresentInEveryResult() {
        boolean durationIsPresentInAllResults = true;
        for(WebElement result: results) {
            try {
                result.findElement(By.xpath(".//*[@data-test-id='duration']"));
            }catch (Exception e) {
                durationIsPresentInAllResults = false;
            }
        }
        return durationIsPresentInAllResults;
    }
    
    public boolean flightDetailsArePresentInEveryResult() {
        boolean flightDetailsArePresentInAllResults = true;
        for(WebElement result: results) {
            try {
                result.findElement(By.xpath(".//*[@class='show-flight-details']"));
            }catch (Exception e) {
                flightDetailsArePresentInAllResults = false;
            }
        }
        return flightDetailsArePresentInAllResults;
    }

    public FlightsResultsPage sortByDurationShorter() {
        Select sortBySelect = new Select(selectSortBy);
        sortBySelect.selectByValue("duration:asc");
        webAction.sleep(5);
        return new FlightsResultsPage(webAction);
    }

    public boolean verifyThatResultsAreSortedByDurationShorter() {
        if (results.size() == 1) {
            return true;
        }
        for (int i = 0; i < results.size() - 1; i++) {
            FlightDuration flightDurationOne = getFlightDuration(
                    results.get(i).findElement(By.xpath(".//*[@data-test-id='duration']")).getText()
            );
            FlightDuration flightDurationTwo = getFlightDuration(
                    results.get(i+1).findElement(By.xpath(".//*[@data-test-id='duration']")).getText()
            );

            if(TimeHelper.compareFlightsDurations(flightDurationOne, flightDurationTwo) > 0) {
                return false;
            }
        }
        return true;
    }

    private FlightDuration getFlightDuration(String info) {
        info  = info.replace("h", "").replace("m", "");
        return  new FlightDuration(
                Integer.parseInt(info.split(" ")[0]),
                Integer.parseInt(info.split(" ")[1])
        );
    }

    public FlightsResultsPage selectFirstResult() throws WebDriverActionException {
        WebElement option = results.get(0);
        if(rulesAndRestrictionApply(option)) {
            option.findElement(By.xpath(".//button[@data-test-id='select-button']")).click();
            WebElement selectThisFareButton = option.findElement(By.xpath(".//button[@data-test-id='select-button-1']"));
            webAction.click(selectThisFareButton, 4);
        } else {
            option.findElement(By.xpath(".//button[@data-test-id='select-button']")).click();
        }
        webAction.sleep(3);
        return new FlightsResultsPage(webAction);
    }

    public boolean rulesAndRestrictionApply(WebElement option) {
        try {
            option.findElement(By.xpath(".//span[contains(text(), 'Rules and restrictions apply')]"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyDepartureFrom(String place) {
        webAction.isVisible(titleCityText, 20);
        return titleCityText.getText().toLowerCase().contains(place.toLowerCase());
    }

    public void selectSpecificResult(int numberOfResult) throws WebDriverActionException {
        numberOfResult = numberOfResult - 1;
        if(results.size() < numberOfResult + 1)
            numberOfResult = results.size() - 1;
//            throw new WebDriverActionException("The page is not showing " + numberOfResult + " numbers of results");
        WebElement option = results.get(numberOfResult);
        webAction.sleep(4);
        if(rulesAndRestrictionApply(option)) {
            option.findElement(By.xpath(".//button[@data-test-id='select-button']")).click();
            WebElement selectThisFareButton = option.findElement(By.xpath(".//button[@data-test-id='select-button-1']"));
            webAction.click(selectThisFareButton, 4);
        } else {
            option.findElement(By.xpath(".//button[@data-test-id='select-button']")).click();
        }
        webAction.sleep(3);

    }

}
