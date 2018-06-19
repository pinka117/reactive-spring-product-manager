package com.reactive.spring.product.manager.controller.steps.login;

import com.reactive.spring.product.manager.controller.steps.CommonWebSteps;
import com.reactive.spring.product.manager.controller.webdriver.pages.AdminHomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginWebSteps extends CommonWebSteps {
    public LoginWebSteps() {
        super();
        Then("^There isn't a button \"Logout\"$", () -> {
            assertFalse((currentPage.getBody()).contains("Logout"));
        });
        Then("^The correct username is displayed$", () -> {
            assertTrue((currentPage.getBody()).contains("admin"));
        });
        When("^Presses \"Logout\"$", () -> {
            adminHomePage = AdminHomePage.to(webDriver);
            redirectedPage = adminHomePage.pressLogout(HomePage.class);
            this.currentPage = redirectedPage;
            loggedIn = false;
        });
        Then("^Presses a link \"Login\"$", () -> {
            redirectedPage = ((HomePage) currentPage).pressLogin(LoginPage.class);
            assertThat(redirectedPage).isInstanceOf(LoginPage.class);
            this.currentPage = redirectedPage;
        });
        And("^Presses Log in$", () -> {
            pressLogin();
        });
        And("^The user inserts a valid admin username and password$", () -> {
            validLogin();
            loggedIn = true;
        });
        And("^The user inserts a non present username and a password$", () -> {
            loginPage.submitForm("no", "no");
        });
        And("^The user inserts a valid username and a wrong password$", () -> {
            loginPage.submitForm("admin", "no");
        });
    }

    @Before
    public void setup() {
        setPort();
        setUserDb();
    }

    @After
    public void tearDown() {
        logout();
    }
}