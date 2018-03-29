package com.reactive.spring.product.manager.e2e.steps.view;

import com.reactive.spring.product.manager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.product.manager.controller.webdriver.pages.AdminHomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

public class ViewWebE2ESteps implements En {
    private static int port =
            Integer.parseInt(System.getProperty("server.port", "8080"));
    private static String url = "http://localhost:" + port;
    private WebDriver webDriver;
    private HomePage homePage;
    private LoginPage loginPage;
    private AdminHomePage adminHomePage;

    public ViewWebE2ESteps() {
        Given("^The user is on Home Page$", () -> {

            homePage = HomePage.to(webDriver);


        });
        And("^The db is not empty$", () -> {


        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();

        });
        Then("^Items are displayed$", () -> {
            assertTrue(homePage.getBody().contains("name1"));
            assertTrue(homePage.getBody().contains("id1"));
            assertTrue(homePage.getBody().contains("location1"));
        });
        And("^There is a button \"Modify\"$", () -> {


            assertTrue(homePage.modifyIsEnabled());


        });
        And("^There is a button \"Remove\"$", () -> {


            assertTrue(homePage.removeIsEnabled());


        });
        And("^There is a button \"Add\"$", () -> {


            assertTrue(homePage.addIsEnabled());


        });
        Given("^The user isn't logged in$", () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication == null)) {
                fail("User isn't logged out");
            }
            homePage = HomePage.to(webDriver);

        });
        And("^There isn't a button \"Modify\"$", () -> {

            try {
                homePage.modifyIsEnabled();
                fail("Button enabled");
            } catch (Exception e) {

            }

        });
        And("^There isn't a button \"Remove\"$", () -> {
            try {
                homePage.removeIsEnabled();
                fail("Button enabled");
            } catch (Exception e) {

            }

        });
        And("^There isn't a button \"Add\"$", () -> {
            try {
                homePage.addIsEnabled();
                fail("Button enabled");
            } catch (Exception e) {

            }

        });
    }

    private void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);

    }

    private void pressLogin() {
        loginPage.pressLogin(HomePage.class);


    }

    private void validLogin() {
        loginPage.submitForm("admin", "admin");
    }

    @Before
    public void setup() {
        AbstractPage.port = port;
        webDriver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
