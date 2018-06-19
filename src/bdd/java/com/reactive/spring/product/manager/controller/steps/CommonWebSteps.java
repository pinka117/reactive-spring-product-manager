package com.reactive.spring.product.manager.controller.steps;

import com.reactive.spring.product.manager.controller.webdriver.pages.*;
import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.model.User;
import com.reactive.spring.product.manager.service.ItemService;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class CommonWebSteps implements En {
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected ItemService itemService;
    @Autowired
    protected WebDriver webDriver;
    @LocalServerPort
    protected int port;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected AbstractPage redirectedPage;
    protected AbstractPage currentPage;
    protected AdminHomePage adminHomePage;
    protected NewPage newPage;
    protected boolean loggedIn = false;

    public CommonWebSteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
        When("^The user navigates to \"login\" page$", () -> {
            gotoLoginPage();
        });
        Then("^The user is redirected to Home Page$", () -> {
            assertThat(redirectedPage).isInstanceOf(HomePage.class);
        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(currentPage.getBody()).contains(msg);
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();
            loggedIn = true;
        });
        Given("^The user isn't logged in$", () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication == null)) {
                fail("User isn't logged out");
            }
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
        And("^There isn't an item with id \"([^\"]*)\" and name \"([^\"]*)\" and location \"([^\"]*)\"$", (String id, String name, String location) -> {
            homePage = HomePage.to(webDriver);
            boolean idexp = homePage.getBody().contains(id);
            boolean nameexp = homePage.getBody().contains(name);
            boolean locationexp = homePage.getBody().contains(location);
            assertFalse(idexp && nameexp && locationexp);
        });
        And("^There is an item with id \"([^\"]*)\" and name \"([^\"]*)\" and location \"([^\"]*)\"$", (String id, String name, String location) -> {
            homePage = HomePage.to(webDriver);
            assertTrue(homePage.getBody().contains(id));
            assertTrue(homePage.getBody().contains(name));
            assertTrue(homePage.getBody().contains(location));
        });
    }

    private void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);
        this.currentPage = loginPage;
    }

    protected void pressLogin() {
        redirectedPage = loginPage.pressLogin(HomePage.class);
        this.currentPage = redirectedPage;
    }

    protected void validLogin() {
        loginPage.submitForm("admin", "password");
    }

    protected void setUserDb() {
        String ADMIN = "admin";
        User user = new User();
        user.setId(ADMIN);
        user.setUsername(ADMIN);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("password"));
        user.addRole("ROLE_ADMIN");
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.createCollection(User.class);
        mongoTemplate.insert(user);
    }

    protected void setItemDb() {
        mongoTemplate.dropCollection(Item.class);
        mongoTemplate.createCollection(Item.class);
    }

    protected void setPort() {
        AbstractPage.port = port;
    }

    protected void logout() {
        if (loggedIn) {
            adminHomePage = AdminHomePage.to(webDriver);
            this.currentPage = adminHomePage;
            adminHomePage.pressLogout(AdminHomePage.class);
            loggedIn = false;
        }
    }

    @TestConfiguration
    public static class WebDriverConfiguration {
        @Bean
        public WebDriver getWebDriver() {
            return new HtmlUnitDriver();
        }
    }
}
