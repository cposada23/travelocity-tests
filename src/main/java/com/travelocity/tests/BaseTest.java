package com.travelocity.tests;

import com.travelocity.actions.WebAction;
import com.travelocity.actions.utils.BrowserEnum;
import com.travelocity.pages.home.TravelocityHomePage;
import com.travelocity.utilities.exceptions.DriverException;
import com.travelocity.utilities.exceptions.UserIsLoggedInException;
import com.travelocity.utilities.exceptions.WebDriverActionException;
import org.junit.Assert;
import org.testng.annotations.*;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseTest {

    private WebAction webAction;

    private TravelocityHomePage travelocityHomePage;

    @BeforeMethod
    @Parameters({"browser"})
    public void verifyNotLoggedIn(@Optional("chrome") String browser) {
         try {
             webAction = new WebAction(BrowserEnum.of(browser));
             webAction.getDriver().manage().window().maximize();
             webAction.getDriver().navigate().to("https://www.travelocity.com/");
             travelocityHomePage = new TravelocityHomePage(this.webAction);
             if(travelocityHomePage.isLoggedIn()){
                // todo logic to logout
                throw new UserIsLoggedInException("The user is already logged in");
            }
        } catch (UserIsLoggedInException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Could not verify if the user is logged in or not");
        }
    }

    @AfterMethod
    public void resetDriver() {
        webAction.disposeDriver();
    }

    public TravelocityHomePage getTravelocityHomePage() {
        return travelocityHomePage;
    }
    protected WebAction getWebAction() {
        return webAction;
    }
}
