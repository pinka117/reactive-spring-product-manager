package com.reactive.spring.product.manager.controller.steps.view;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.reactive.spring.product.manager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.product.manager.controller.webdriver.pages.AdminHomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.LoginPage;
import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.model.User;
import com.reactive.spring.product.manager.service.ItemService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewWebSteps implements En {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ItemService itemService;
    @Autowired
    private WebDriver webDriver;
    @LocalServerPort
    private int port;
    private HomePage homePage;
    private LoginPage loginPage;
    private boolean loggedIn = false;
    private AdminHomePage adminHomePage;

    public ViewWebSteps() {
        Given("^The user is on Home Page$", () -> {

            homePage = HomePage.to(webDriver);


        });
        And("^The db is not empty$", () -> {


            Item it = new Item("id1", "name1", "location1");
            Mono<Item> i = itemService.add(it);
            i.subscribe();


        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();
            loggedIn = true;
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
        loginPage.submitForm("admin", "password");
    }

    @After
    public void tearDown() {
        if (loggedIn == true) {
            adminHomePage = AdminHomePage.to(webDriver);
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
        user.addRole(ADMIN);
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.createCollection(User.class);
        mongoTemplate.insert(user);
        mongoTemplate.dropCollection(Item.class);
        mongoTemplate.createCollection(Item.class);

    }

    @TestConfiguration
    public static class WebDriverConfiguration {
        @Bean
        public WebDriver getWebDriver() {

            HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);


            return driver;
        }
    }
}
