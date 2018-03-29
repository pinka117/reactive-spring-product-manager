package com.reactive.spring.product.manager.e2e.steps.login;

import com.reactive.spring.product.manager.controller.webdriver.pages.*;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class LoginWebE2ESteps implements En {
    private static int port =
            Integer.parseInt(System.getProperty("server.port", "8080"));
    private static String url = "http://localhost:" + port;
    private WebDriver webDriver;
    private HomePage homePage;
    private AbstractPage currentPage;
    private LoginPage loginPage;
    private AbstractPage redirectedPage;
    private AdminHomePage adminHomePage;
    private NewPage newPage;

    public LoginWebE2ESteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
        When("^The user navigates to \"login\" page$", () -> {
            gotoLoginPage();

        });
        And("^The user insert a valid admin username and password$", () -> {
            validLogin();


        });
        Then("^The user is redirected to Home Page$", () -> {

            assertThat(redirectedPage).isInstanceOf(HomePage.class);
        });
        And("^The user insert a non present username and a password$", () -> {

            loginPage.submitForm("no", "no");

        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(currentPage.getBody()).contains(msg);
        });
        And("^The user insert a valid username and a wrong password$", () -> {
            loginPage.submitForm("admin", "no");
        });
        And("^Presses Log in$", () -> {
            pressLogin();
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();

        });
        Then("^The correct username is displayed$", () -> {
            assertThat(currentPage.getBody()).contains("admin");
        });
        When("^Presses \"Logout\"$", () -> {
            adminHomePage = AdminHomePage.to(webDriver);
            redirectedPage = adminHomePage.pressLogout(HomePage.class);
            this.currentPage = redirectedPage;

        });
        Given("^The user isn't logged in$", () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication == null)) {
                fail("User isn't logged out");
            }
        });
        Then("^There isn't a button \"([^\"]*)\"$", (String name) -> {
            assertThat(!(currentPage.getBody()).contains(name));
        });
        Then("^Presses a link \"Login\"$", () -> {
            redirectedPage = ((HomePage) currentPage).pressLogin(LoginPage.class);
            assertThat(redirectedPage).isInstanceOf(LoginPage.class);
            this.currentPage = redirectedPage;
        });
        And("^Presses link \"Back to Home Page\"$", () -> {
            redirectedPage = ((LoginPage) currentPage).pressBack(HomePage.class);
            assertThat(redirectedPage).isInstanceOf(HomePage.class);
            this.currentPage = redirectedPage;
        });
        When("^The user navigates on \"new\" page$", () -> {
            newPage = NewPage.to(webDriver);
            this.currentPage = newPage;
        });

    }

    private void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);
        this.currentPage = loginPage;
    }

    private void pressLogin() {
        redirectedPage = loginPage.pressLogin(HomePage.class);
        this.currentPage = redirectedPage;

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
