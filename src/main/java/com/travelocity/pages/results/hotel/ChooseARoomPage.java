package com.travelocity.pages.results.hotel;

import com.travelocity.actions.WebAction;
import com.travelocity.pages.results.FlightsResultsPage;
import com.travelocity.utilities.dto.HotelInfo;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ChooseARoomPage {

    private HotelInfo hotelInfo;
    private WebAction webAction;

    @FindBy(id = "mock-book-button")
    private WebElement chooseARoomButton;
    @FindBy(xpath = "//*[@class = 'price link-to-rooms']")
    private WebElement hotelPrice;
    @FindBy(xpath=  "//*[@id='hotel-name']/following-sibling::div[@class='star-rating-wrapper']//span[@aria-hidden]")
    private WebElement stars;
    @FindBy(id = "hotel-name")
    private WebElement hotelName;
    @FindAll(@FindBy(xpath = "//*[@id='rooms-and-rates']//article[contains(@class, 'room') and not(contains(@class, 'hidden'))]//a//span[text() = 'Select']/.."))
    private List<WebElement> rooms;


    //Modal not refundable
    @FindBy(xpath = "//*[text() =  'This reservation is non-refundable']")
    private WebElement modalNonRefundable;
    @FindBy(xpath = "//*[text() =  'This reservation is non-refundable']/ancestor::div[contains(@class, 'modal-inner')]//a[contains(@class, 'book-button')]")
    private WebElement buttonContinueNonRefundable;


    public ChooseARoomPage(WebAction webAction, HotelInfo hotelInfo) {
        this.webAction = webAction;
        this.hotelInfo = hotelInfo;
        String currentWindow = webAction.getDriver().getWindowHandle();
        String newWindow = webAction.getDriver().getWindowHandles().stream()
                .filter(handle -> !handle.equals(currentWindow)).collect(Collectors.joining());
        webAction.getDriver().switchTo().window(newWindow);
        PageFactory.initElements(webAction.getDriver(), this);
    }

    public boolean isChooseARoomPageLoaded() {
        return webAction.isVisible(chooseARoomButton, 30);
    }

    public boolean isNameCorrect() {
        return hotelName.getText().equalsIgnoreCase(hotelInfo.getName());
    }

    public boolean isNumberOfStarsCorrect() {
        double numberOfStarsInPage = Double.parseDouble(
                stars.getAttribute("title")
        );
        return numberOfStarsInPage == hotelInfo.getNumberOfStars();
    }

    public boolean isPriceCorrect() {
        return hotelPrice.getText().equals(hotelInfo.getPrice());
    }

    public FlightsResultsPage selectFirstRoom() throws WebDriverActionException {
        WebElement room = rooms.get(0);
        webAction.click(rooms.get(0), 1);
        if(isNonRefundable(room)) {
            webAction.isVisible(modalNonRefundable, 10);
            webAction.click(buttonContinueNonRefundable, 2);
        }
        webAction.sleep(2);
        return new FlightsResultsPage(webAction);
    }

    public boolean isNonRefundable(WebElement room) {
        try {
            room.findElement(By.xpath("./ancestor::div[@class='room-price-book-button-wrapper']//*[contains(@class, 'non-refundable')]"));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
