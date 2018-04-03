package com.reactive.spring.product.manager.e2e.steps;

import com.reactive.spring.product.manager.controller.webdriver.pages.*;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public abstract class CommonWebE2ESteps implements En {
    protected static int port =
            Integer.parseInt(System.getProperty("server.port", "8080"));
    protected static String url = "http://localhost:" + port;
    protected WebDriver webDriver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected NewPage newPage;
    protected AbstractPage redirectedPage;
    protected AbstractPage currentPage;
    protected AdminHomePage adminHomePage;

    public CommonWebE2ESteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();
        });
        Then("^The user is redirected to Home Page$", () -> {

            assertThat(redirectedPage).isInstanceOf(HomePage.class);
        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(currentPage.getBody()).contains(msg);
        });
        Given("^The user isn't logged in$", () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication == null)) {
                fail("User isn't logged out");
            }
        });
        When("^The user navigates on \"new\" page$", () -> {
            newPage = NewPage.to(webDriver);
            this.currentPage = newPage;
        });
    }

    protected void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);
        currentPage = loginPage;
    }

    protected void pressLogin() {
        redirectedPage = loginPage.pressLogin(HomePage.class);
    }

    protected void validLogin() {
        loginPage.submitForm("admin", "admin");
    }

    protected void setPort() {
        AbstractPage.port = port;
        webDriver = new ChromeDriver();
    }
}
