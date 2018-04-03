package com.reactive.spring.product.manager.controller.steps.add;

import com.reactive.spring.product.manager.controller.steps.CommonWebSteps;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.NewPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddWebSteps extends CommonWebSteps {
    public AddWebSteps() {
        super();
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
        And("^A table must show an item with name \"([^\"]*)\", location \"([^\"]*)\" and id is positive$", (String name, String location) -> {
            homePage = HomePage.to(webDriver);
            assertTrue(homePage.getBody().contains(name));
            assertTrue(homePage.getBody().contains(location));
        });
    }

    @Before
    public void setup() {
        setPort();
        setUserDb();
        setItemDb();
    }

    @After
    public void tearDown() {
        logout();
    }

    @TestConfiguration
    public static class WebDriverConfiguration {
        @Bean
        public WebDriver getWebDriver() {
            return new HtmlUnitDriver();
        }
    }
}
