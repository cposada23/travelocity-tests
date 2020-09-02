package com.travelocity.pages.results.cruises;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.dto.CruiseInfo;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CruisesResultPage {

    @FindAll
    (@FindBy(xpath = "//*[contains(@id, 'cruiseCard')]"))
    private List<WebElement> cruises;

    @FindAll(@FindBy(xpath = "//*[contains(@id, 'cruiseCard')]//div[@class='message-flag flex-flag']"))
    private List<WebElement> cruisesWithDiscount;
    @FindAll(@FindBy(xpath = "//*[contains(@id, 'cruiseCard')]//div[contains(@class,'sailing-pricing-container-strikeout-pricing-unavailable')]"))
    private List<WebElement> cruisesWithoutDiscount;

    @FindBy(xpath = "//div[@class='filter-options-container']//input[contains(@id, 'length-10-14')]")
    private WebElement cruiseLength10To14Nights;

    @FindBy(xpath = "//ul[contains(@class, 'nobullet')]//*[@data-opt-group='Departure Date' and @aria-pressed='true' and not(@disabled)]")
    private WebElement sortByDepartureNotDisabled;

    private WebAction webAction;

    public CruisesResultPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }


    public void waitForResults() {
        WebDriverWait wait = new WebDriverWait(webAction.getDriver(), 50);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[contains(@id, 'cruiseCard')]"), 0 ));
    }

    public boolean areResultsShowing() {
        return cruises.size() > 0;
    }

    public int numberOfCruiseWithDiscount() {
        return cruisesWithDiscount.size();
    }

    public int numberOfCruisesWithoutDiscount() {
        return cruisesWithoutDiscount.size();
    }
    public void selectCruiseLength10To14Nights() throws WebDriverActionException {
        webAction.clickJS(cruiseLength10To14Nights);
    }

    public void waitForResponse() {
        webAction.sleep(4);
        webAction.isVisible(sortByDepartureNotDisabled, 40);
    }

    public CruiseInfo selectCruiseWithMoreDiscount() {
        WebElement selectedCruise = cruisesWithDiscount.get(0);
        for (int i = 1; i < cruisesWithDiscount.size(); i++) {
            String discountCruise1 = selectedCruise.getText();
            WebElement cruiseToCompare = cruisesWithDiscount.get(i);
            String discountCruise2 = cruiseToCompare.getText();
            if(hasMoreDiscount(discountCruise2, discountCruise1)) {
                selectedCruise = cruiseToCompare;
            }
        }
        // HEre i have the cruise with more discount
        String name = selectedCruise.findElement(By.xpath("./ancestor::div[@class='flex-content']//div[@class='title-on-ship-image']")).getText();
        String price = selectedCruise.findElement(By.xpath("./following-sibling::div[contains(@class, 'sailing-pricing-container')]//span[@class='card-price']")).getText();

        selectedCruise.findElement(By.xpath("./following-sibling::div[contains(@class, 'sailing-pricing-container')]//div[@class='btn-container']//a")).click();
        webAction.sleep(4);
        return new CruiseInfo(
                name,
                price
        );
    }

    public boolean hasMoreDiscount(String discount1, String discount2) {
        int d1 = extractPercentage(discount1);
        int d2 = extractPercentage(discount2);
        return d1 >= d2;
    }

    public int extractPercentage(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

}
