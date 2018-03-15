package com.reactive.spring.productManager.controller.steps;

import com.reactive.spring.productManager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.productManager.controller.webdriver.pages.HomePage;
import com.reactive.spring.productManager.service.ItemService;
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
import org.springframework.test.context.ContextConfiguration;

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

    public LoginSteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
        });

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