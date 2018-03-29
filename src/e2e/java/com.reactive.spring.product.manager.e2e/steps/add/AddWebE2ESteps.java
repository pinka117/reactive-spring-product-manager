package com.reactive.spring.product.manager.e2e.steps.add;

import com.reactive.spring.product.manager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.LoginPage;
import com.reactive.spring.product.manager.controller.webdriver.pages.NewPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class AddWebE2ESteps implements En {
    private static int port =
            Integer.parseInt(System.getProperty("server.port", "8080"));
    private static String url = "http://localhost:" + port;
    private WebDriver webDriver;
    private HomePage homePage;
    private LoginPage loginPage;
    private NewPage newPage;
    private AbstractPage redirectedPage;

    public AddWebE2ESteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();

        });
        When("^The user navigates to \"new\" page$", () -> {
            newPage = NewPage.to(webDriver);
        });
        And("^Enters item name \"([^\"]*)\"$", (String name) -> {
            newPage.submitFormName(name);
        });
        And("^Enters location \"([^\"]*)\"$", (String location) -> {
            newPage.submitFormLocation(location);
        });
        And("^Presses \"Add\"$", () -> {
            redirectedPage = newPage.pressAdd(HomePage.class);
        });
        Then("^The user is redirected to Home Page$", () -> {

            assertThat(redirectedPage).isInstanceOf(HomePage.class);
        });
        And("^A table must show an item with name \"([^\"]*)\", location \"([^\"]*)\" and id is positive$", (String name, String location) -> {
            homePage = HomePage.to(webDriver);
            System.out.println(homePage.getBody());
            assertTrue(homePage.getBody().contains(name));
            assertTrue(homePage.getBody().contains(location));

        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(newPage.getBody()).contains(msg);
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
