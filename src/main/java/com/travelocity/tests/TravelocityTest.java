package com.travelocity.tests;

import com.travelocity.pages.components.DatePickerPageComponent;
import com.travelocity.pages.home.tabs.CruisesTabPage;
import com.travelocity.pages.home.tabs.FlightsTabPage;
import com.travelocity.pages.home.TravelocityHomePage;
import com.travelocity.pages.home.tabs.HotelsTabPage;
import com.travelocity.pages.home.tabs.VacationPackagePage;
import com.travelocity.pages.results.*;
import com.travelocity.pages.results.cruises.ChooseCabinPage;
import com.travelocity.pages.results.cruises.CruisesResultPage;
import com.travelocity.pages.results.hotel.ChooseARoomPage;
import com.travelocity.pages.results.hotel.HotelListPage;
import com.travelocity.pages.results.hotel.HotelsResultsPage;
import com.travelocity.utilities.DatePickerHelper;
import com.travelocity.utilities.DateToSelect;
import com.travelocity.utilities.dto.CruiseInfo;
import com.travelocity.utilities.dto.TripDetails;
import com.travelocity.utilities.exceptions.DatePickerException;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TravelocityTest extends BaseTest {

    private static final String FROM = "LAS";
    private static final String TO = "LAX";
    private static final int MONTHS_FROM_NOW = 2;
    private static final String NUMBER_OF_ADULTS = "1";

    @Test(enabled = true)
    public void bookingAFlightTillTheCreditCardInformationPage() {
        try {
            TravelocityHomePage travelocityHomePage = getTravelocityHomePage();
            FlightsTabPage flightsTabPage = travelocityHomePage.enterFlightsTab();
            Assert.assertTrue(
                    "Could not verify that the user is in the flights tab to continue the test",
                    flightsTabPage.verifyFightsTabIsShowing()
            );
            flightsTabPage.clickRoundTrip();
            flightsTabPage.selectFlyingFrom(FROM);
            flightsTabPage.selectFlyingTo(TO);


            // Select departing date
            selectDate(
                    flightsTabPage.showFlightDepartingDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW)
            );

            //Select returning date
            selectDate(
                    flightsTabPage.showFlightReturningDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW + 1)
            );

            flightsTabPage.selectNumberOfAdults(NUMBER_OF_ADULTS);

            FlightsResultsPage flightsResultsPage = flightsTabPage.submitForm();
            flightsResultsPage.waitForResults();
            Assert.assertTrue(
                    "The results are no showing on the results page",
                    flightsResultsPage.areResultsShowing()
            );

            List<String> optionsToSortBy = flightsResultsPage.getSortByOptions();
            Assert.assertTrue(
                    "No options were found to order by",
                    optionsToSortBy.size() > 0
            );

            List<String> optionsToAssert = Arrays.asList("Price", "Departure", "Arrival", "Duration");
            optionsToAssert.stream().forEach(option -> {
                Assert.assertTrue(
                        "The option sort by " + option + " is not listed",
                        optionsToSortBy.contains(option.toLowerCase())
                );
            });

            Assert.assertTrue(
                    "Not every result has a select button",
                    flightsResultsPage.selectButtonIsPresentInEveryResult()
            );
            Assert.assertTrue(
                    "Not every result has its flight duration",
                    flightsResultsPage.durationIsPresentInEveryResult()
            );
            Assert.assertTrue(
                    "Not every result has its flight details present",
                    flightsResultsPage.flightDetailsArePresentInEveryResult()
            );

            FlightsResultsPage resultPageSortedByDuration = flightsResultsPage.sortByDurationShorter();
            resultPageSortedByDuration.waitForResults();
            Assert.assertTrue(
                    "There are no results showing after sorting by duration shorter",
                    resultPageSortedByDuration.areResultsShowing()
            );

            Assert.assertTrue(
                    "The flights where no sorted by duration shorter",
                    resultPageSortedByDuration.verifyThatResultsAreSortedByDurationShorter()
            );

            FlightsResultsPage resultsReturningFlights = resultPageSortedByDuration.selectFirstResult();
            flightsResultsPage.waitForResults();
            Assert.assertTrue(
                    "The results are no showing on the results page to select returning flight",
                    flightsResultsPage.areResultsShowing()
            );

            Assert.assertTrue(
                    "The correct title was not show",
                    resultsReturningFlights.verifyDepartureFrom("Select your return to Las Vegas")
            );

            resultsReturningFlights.selectSpecificResult(3);
            TripSummaryPage summaryPage = new TripSummaryPage(getWebAction());
            summaryPage.switchToTripSummaryPage();
            Assert.assertTrue(
                    "could not verify the correct entry to summary page",
                    summaryPage.isTripSummaryVisible()
            );
            Assert.assertTrue(
                    "could not verify the trip total is present",
                    summaryPage.tripTotalIsPresent()
            );
            Assert.assertTrue(
                    "could not verify the departure info is present",
                    summaryPage.isDepartureInfoVisible()
            );
            Assert.assertTrue(
                    "could not verify the return info is present",
                    summaryPage.isReturnInfoVisible()
            );
            Assert.assertTrue(
                    "could not verify that the price guarantee tag is visible",
                    summaryPage.isReturnPriceGuaranteeVisible()
            );
            WhoIsTravelingPage whoIsTravelingPage = summaryPage.pressContinueBooking();
//            whoIsTravelingPage.waitForWarnModal();
            Assert.assertTrue(
                    "could not verify the input for the firstName is present on the page",
                    whoIsTravelingPage.isFirstNameInputVisible()
            );
            Assert.assertTrue(
                    "could not verify the input for the lastName is present on the page",
                    whoIsTravelingPage.isLastNameInputVisible()
            );
            Assert.assertTrue(
                    "could not verify the input for the phone number is present on the page",
                    whoIsTravelingPage.isPhoneNumberInputInputVisible()
            );
            Assert.assertTrue(
                    "Could not verify that the complete booking button is visible",
                    whoIsTravelingPage.isCompleteBookingButtonVisible()
            );
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void bookingAFlightWithHotelAndCar() {
        try {
            TravelocityHomePage travelocityHomePage = getTravelocityHomePage();
            VacationPackagePage vacationPackagePage = travelocityHomePage.enterVacationPackagesTab();
            vacationPackagePage.clickButtonFlightPlusHotel();
            vacationPackagePage.selectFlyingFrom(FROM);
            vacationPackagePage.selectFlyingTo(TO);
            vacationPackagePage.selectNumberOfAdults(NUMBER_OF_ADULTS);
            // Select departing date
            selectDate(
                    vacationPackagePage.showFlightDepartingDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW)
            );
            //Select returning date
            selectDate(
                    vacationPackagePage.showFlightReturningDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW, 13)
            );

            HotelsResultsPage hotelsResultsPage = vacationPackagePage.clickSearchButton();
            Assert.assertTrue(
                    "it was not possible to verify that the hotel result page was loaded",
                    hotelsResultsPage.isHotelsResultsTitleShowing()
            );
            hotelsResultsPage.waitForResults();
            Assert.assertTrue(
                    "No hotel is shown in the search results",
                    hotelsResultsPage.areResultsShowing()
            );

            HotelsResultsPage hotelsSortedByPrice = hotelsResultsPage.sortByPrice();
            hotelsSortedByPrice.waitForResults();
            Assert.assertTrue(
                    "No hotel was shown after sorting by price",
                    hotelsSortedByPrice.areResultsShowing()
            );

            Assert.assertTrue(
                    "Hotels where not sorted by price",
                    hotelsSortedByPrice.verifyThatResultsAreSortedByPrice()
            );

            ChooseARoomPage chooseARoomPage = hotelsSortedByPrice.findFirsHotelWithAtLeastThreeStars();
            Assert.assertTrue(
                    "The choose a room page did not load",
                    chooseARoomPage.isChooseARoomPageLoaded()
            );
            Assert.assertTrue(
                    "The name of the hotel is not correct",
                    chooseARoomPage.isNameCorrect()
            );
            Assert.assertTrue(
                    "The number of stars does not match the number of spected stars",
                    chooseARoomPage.isNumberOfStarsCorrect()
            );
            Assert.assertTrue(
                    "The price is not the correct one",
                    chooseARoomPage.isPriceCorrect()
            );

            FlightsResultsPage departureFlights = chooseARoomPage.selectFirstRoom();

            departureFlights.waitForResults();
            Assert.assertTrue(
                    "There are not departing flights showing",
                    departureFlights.areResultsShowing()
            );

            FlightsResultsPage returnFlights = departureFlights.selectFirstResult();
            returnFlights.waitForResults();
            Assert.assertTrue(
                    "There are no returning Flights showing",
                    returnFlights.areResultsShowing()
            );

            returnFlights.selectSpecificResult(3);
            TripDetailsPage tripDetailsPage = new TripDetailsPage(getWebAction());
            Assert.assertTrue(
                    "Trip details are not showing",
                    tripDetailsPage.areFlightDetailsPresent()
            );
            tripDetailsPage.selectACar();
            Assert.assertTrue(
                    "Hotel name is not visible on the trip details page",
                    tripDetailsPage.isHotelNameVisible()
            );
            Assert.assertTrue(
                    "Total price is not present on the trip details page",
                    tripDetailsPage.isTotalPricePresent()
            );
            Assert.assertTrue(
                    "Date of the trip is not present on the trip details page",
                    tripDetailsPage.isTripDatePresent()
            );
            Assert.assertTrue(
                    "Hotel Fee is not present in the trip details page",
                    tripDetailsPage.isHotelFeesPresent()
            );

            TripDetails tripDetails = tripDetailsPage.getTripDetails();
            ReviewAndBookPage reviewAndBookPage = tripDetailsPage.clickNextFinalDetails();
            Assert.assertTrue(
                    "the review and book page din not load",
                    reviewAndBookPage.isPageLoaded()
            );
            Assert.assertTrue(
                    "The hotel name did not match in the review and book page",
                    reviewAndBookPage.isHotelNameCorrect(tripDetails.getHotelName())
            );
            Assert.assertTrue(
                    "The total price did not match in the review and book page",
                    reviewAndBookPage.isTotalPriceCorrect(tripDetails.getTotalPrice())
            );
            Assert.assertTrue(
                    "The Hotel fees did not match in the review and book page",
                    reviewAndBookPage.isHotelFeesCorrect(tripDetails.getPriceDueAtHotel())
            );
            Assert.assertTrue(
                    "The first name input is not present in the review page",
                    reviewAndBookPage.isFirstNameInputPresent()
            );
            Assert.assertTrue(
                    "The last name input is not present in the review page",
                    reviewAndBookPage.isLastNameInputPresent()
            );
            Assert.assertTrue(
                    "The phone number input is not present in the review page",
                    reviewAndBookPage.isPhoneInputPresent()
            );

        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private void selectDate(DatePickerPageComponent datePickerPageComponent, DateToSelect dateToSelect) throws DatePickerException {
        try {
            Assert.assertTrue(
                    "The date picker is nos visible to select departing date",
                    datePickerPageComponent.isDatePickerShowing()
            );
            datePickerPageComponent.lookForMonth(dateToSelect.getMonth());
            datePickerPageComponent.selectDayOfTheMonth(dateToSelect.getDay());
        } catch (Exception e) {
            throw new DatePickerException("there was an error selecting the date: ".concat(dateToSelect.toString()));
        }

    }

    @Test(enabled = true)
    public void searchByHotelName() {
        try {
            TravelocityHomePage travelocityHomePage = getTravelocityHomePage();
            HotelsTabPage hotelsTabPage = travelocityHomePage.enterHotelsTab();
            hotelsTabPage.selectGoingTo("Montevideo, Uruguay");
            selectDate(
                    hotelsTabPage.showCheckInDates(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW)
            );
            selectDate(
                    hotelsTabPage.showCheckOutDates(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW, 10)
            );
            hotelsTabPage.selectNumberOfAdults(NUMBER_OF_ADULTS);
            HotelListPage hotelListPage = hotelsTabPage.clickSearch();
            hotelListPage.waitForResults();
            Assert.assertTrue(
                    "The hotel results are not showing",
                    hotelListPage.areResultsShowing()
            );
            String selectedOption = hotelListPage.getOptionSelectedSelectSortBy();
            Assert.assertEquals(
                    "The hotels should be sorted by recommended",
                    "RECOMMENDED",
                    selectedOption
            );
            Assert.assertTrue(
                    "There should be a button to download de travelocity app",
                    hotelListPage.isGetTheAppButtonVisible()
            );
        }catch (Exception e ){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void errorMessageDisplayedWhenLookingForHotelInIncorrectDates() {
        try {
            TravelocityHomePage travelocityHomePage = getTravelocityHomePage();
            FlightsTabPage flightsTabPage = travelocityHomePage.enterFlightsTab();
            flightsTabPage.selectFlyingFrom(FROM);
            flightsTabPage.selectFlyingTo(TO);
            selectDate(
                    flightsTabPage.showFlightDepartingDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW)
            );
            selectDate(
                    flightsTabPage.showFlightReturningDatePicker(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW, 5)
            );

            flightsTabPage.checkAddAHotel();

            selectDate(
                    flightsTabPage.showCheckIn(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW + 1)
            );

            selectDate(
                    flightsTabPage.showCheckOut(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW + 1, 4)
            );

            flightsTabPage.submitForm();

            Assert.assertTrue(
                    "a error message should be displayed when looking for hotel in incorrect dates",
                    flightsTabPage.isAlertErrorPresent()
            );
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void cruisesDiscountIsDisplayed() {
        try {
//        Cruises discount is displayed
            TravelocityHomePage travelocityHomePage = getTravelocityHomePage();
            CruisesTabPage cruisesTabPage = travelocityHomePage.enterCruisesTab();
            cruisesTabPage.selectCruiseDestination("europe");
            selectDate(
                    cruisesTabPage.showDepartDate(),
                    DatePickerHelper.getDateToSelect(MONTHS_FROM_NOW)
            );
            CruisesResultPage cruisesResultPage = cruisesTabPage.submitForm();
            cruisesResultPage.waitForResults();
            cruisesResultPage.selectCruiseLength10To14Nights();
            cruisesResultPage.waitForResponse();
            cruisesResultPage.waitForResults();
            Assert.assertTrue(
                    "No cruise results are showing",
                    cruisesResultPage.areResultsShowing()
            );

            int numberOfCruisesWithDiscount = cruisesResultPage.numberOfCruiseWithDiscount();
            int numberOfCruisesWithoutDiscount = cruisesResultPage.numberOfCruisesWithoutDiscount();

            Assert.assertTrue(
                    "no discounted cruises were shown",
                    numberOfCruisesWithDiscount > 0
            );
            Assert.assertTrue(
                    "no nondiscounted cruises were shown",
                    numberOfCruisesWithoutDiscount > 0
            );

            CruiseInfo cruiseInfo = cruisesResultPage.selectCruiseWithMoreDiscount();

            ChooseCabinPage chooseCabinPage = new ChooseCabinPage(getWebAction());
            chooseCabinPage.changeToNewPage();
            String titleOfShip = chooseCabinPage.getTitleOfShip();

            Assert.assertTrue(
                    "Title of ship do not match the one that was choosen",
                    cruiseInfo.getTitleShip().toLowerCase().equals(titleOfShip.toLowerCase())
            );

        } catch (Exception e ) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
