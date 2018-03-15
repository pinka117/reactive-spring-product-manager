package com.reactive.spring.productManager.controller.steps;

import com.reactive.spring.productManager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.productManager.controller.webdriver.pages.AdminHomePage;
import com.reactive.spring.productManager.controller.webdriver.pages.HomePage;
import com.reactive.spring.productManager.controller.webdriver.pages.LoginPage;
import com.reactive.spring.productManager.service.ItemService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginSteps implements En {

    @Autowired
    private ItemService itemService;
    @Autowired
    private WebDriver webDriver;
    @LocalServerPort
    private int port;
    private HomePage homePage;
    private LoginPage loginPage;
    private AbstractPage redirectedPage;
    private AbstractPage currentPage;
    private AdminHomePage adminHomePage;
    private boolean loggedIn = false;


    public LoginSteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
        When("^The user navigates to \"login\" page$", () -> {
            gotoLoginPage();
        });
        And("^The user insert a valid admin username and password$", () -> {
            validLogin();
            loggedIn = true;

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
            loggedIn = true;
        });
        Then("^The correct username is displayed$", () -> {
            assertThat(currentPage.getBody()).contains("admin");
        });
        When("^Presses \"Logout\"$", () -> {
            adminHomePage = AdminHomePage.to(webDriver);
            redirectedPage = adminHomePage.pressLogout(HomePage.class);
            this.currentPage = redirectedPage;
            loggedIn = false;
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
        loginPage.submitForm("admin", "password");
    }

    @After
    public void tearDown() {
        if (loggedIn == true) {
            adminHomePage = AdminHomePage.to(webDriver);
            this.currentPage = adminHomePage;
            adminHomePage.pressLogout(AdminHomePage.class);
            loggedIn = false;
        }
    }

    @Before
    public void setup() {
        AbstractPage.port = port;
    }

    @TestConfiguration
    public static class WebDriverConfiguration {
        @Bean
        public WebDriver getWebDriver() {
            return new HtmlUnitDriver();
        }
    }
}