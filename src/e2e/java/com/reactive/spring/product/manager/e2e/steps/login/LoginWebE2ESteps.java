package com.reactive.spring.product.manager.e2e.steps.login;

import com.reactive.spring.product.manager.controller.webdriver.pages.AdminHomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.LoginPage;
import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginWebE2ESteps extends CommonWebE2ESteps {
    public LoginWebE2ESteps() {
        When("^The user navigates to \"login\" page$", () -> {
            gotoLoginPage();
        });
        And("^The user inserts a valid admin username and password$", () -> {
            validLogin();
        });
        And("^The user inserts a non present username and a password$", () -> {
            loginPage.submitForm("no", "no");
        });
        And("^The user inserts a valid username and a wrong password$", () -> {
            loginPage.submitForm("admin", "no");
        });
        And("^Presses Log in$", () -> {
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

    @Before
    public void setup() {
        setPort();
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
