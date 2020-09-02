package com.travelocity.pages.results.hotel;

import com.travelocity.actions.WebAction;
import com.travelocity.utilities.dto.HotelInfo;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HotelsResultsPage {

    @FindBy(xpath = "//*[contains(text(), 'Start by choosing your hotel')]")
    private WebElement hotelsResultsTitle;

    @FindBy(xpath = "//*[text() = 'Price']/..")
    private WebElement buttonSortByPrice;

    @FindBy(id = "resultsContainer")
    private WebElement resultsContainer;

    @FindAll(@FindBy(xpath = "//*[@id='resultsContainer']//article[contains(@class, 'hotel')]"))
    private List<WebElement> hotels;

    private WebAction webAction;
    public HotelsResultsPage(WebAction webAction) {
        this.webAction = webAction;
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean isHotelsResultsTitleShowing() {
        return webAction.isVisible(hotelsResultsTitle, 30);
    }

    public void waitForResults() {
        WebDriverWait wait = new WebDriverWait(webAction.getDriver(), 30);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[@id='resultsContainer']//article[contains(@class, 'hotel')]"), 0 ));
    }

    public boolean areResultsShowing() {
        return webAction.isVisible(resultsContainer, 20) && hotels.size() > 0;
    }

    public HotelsResultsPage sortByPrice() throws WebDriverActionException {
        webAction.click(buttonSortByPrice, 5);
        webAction.sleep(4);
        return new HotelsResultsPage(webAction);
    }

    public boolean verifyThatResultsAreSortedByPrice() {
        if (hotels.size() == 1) {
            return true;
        }
        for (int i = 0; i < hotels.size() - 1; i++) {
            if(!isHotel2PriceMoreExpensiveOrEqualThanHotel1(
                    hotels.get(i),
                    hotels.get(i+1)
            )) return false;
        }
        return true;
    }

    private boolean isHotel2PriceMoreExpensiveOrEqualThanHotel1(WebElement hotel1, WebElement hotel2) {
        int priceHotel1 = Integer.parseInt(hotel1.findElement(By.xpath(".//*[contains(@class, 'actualPrice')]"))
                .getText()
                .replace("$", "")
                .replace(",", "")
        );
        int priceHotel2 = Integer.parseInt(hotel2.findElement(By.xpath(".//*[contains(@class, 'actualPrice')]"))
                .getText()
                .replace("$", "")
                .replace(",", "")
        );

        return priceHotel2 >= priceHotel1;
    }

    public ChooseARoomPage findFirsHotelWithAtLeastThreeStars() throws WebDriverActionException {
        for(WebElement hotel: hotels) {
            double numberOfStars = Double.parseDouble(hotel.findElement(By.xpath(".//li[contains(@class, 'starRating')]//span[@aria-hidden]"))
                    .getAttribute("title"));
            if(numberOfStars >= 3.0) {
                HotelInfo hotelInfo = getHotelInfo(hotel, numberOfStars);
                hotel.findElement(By.xpath(".//a[@class='flex-link']")).click();
                webAction.sleep(5);
                return new ChooseARoomPage(webAction, hotelInfo);
            }
        }
        throw new WebDriverActionException("Could not found a hotel with at least three stars");
    }


    private HotelInfo getHotelInfo(WebElement hotel, double numberOfStars) {
        return new HotelInfo(
                hotel.findElement(By.xpath(".//*[contains(@class, 'actualPrice')]"))
                        .getText(),
                hotel.findElement(By.xpath(".//*[@data-automation='hotel-name']"))
                        .getText(),
                numberOfStars
        );
    }
}
