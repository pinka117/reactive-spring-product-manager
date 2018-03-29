package com.reactive.spring.product.manager.controller.steps.add;

import com.reactive.spring.product.manager.controller.webdriver.pages.*;
import com.reactive.spring.product.manager.model.User;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddWebSteps implements En {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private WebDriver webDriver;
    @LocalServerPort
    private int port;
    private HomePage homePage;
    private LoginPage loginPage;
    private NewPage newPage;
    private boolean loggedIn = false;
    private AbstractPage redirectedPage;

    public AddWebSteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();
            loggedIn = true;
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
            assertTrue(homePage.getBody().contains(name));
            assertTrue(homePage.getBody().contains(location));

        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(newPage.getBody()).contains(msg);
        });

    }

    @After
    public void tearDown() {
        if (loggedIn == true) {
            AdminHomePage adminHomePage = AdminHomePage.to(webDriver);
            adminHomePage.pressLogout(AdminHomePage.class);
            loggedIn = false;
        }
    }

    @Before
    public void setup() {
        AbstractPage.port = port;
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

    private void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);

    }

    private void pressLogin() {
        loginPage.pressLogin(HomePage.class);


    }

    private void validLogin() {
        loginPage.submitForm("admin", "password");
    }

    @TestConfiguration
    public static class WebDriverConfiguration {
        @Bean
        public WebDriver getWebDriver() {
            return new HtmlUnitDriver();
        }
    }
}
