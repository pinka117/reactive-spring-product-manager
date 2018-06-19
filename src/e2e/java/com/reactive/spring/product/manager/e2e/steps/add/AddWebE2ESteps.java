package com.reactive.spring.product.manager.e2e.steps.add;

import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.NewPage;
import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static org.junit.Assert.assertTrue;

public class AddWebE2ESteps extends CommonWebE2ESteps {
    public AddWebE2ESteps() {
        super();
        When("^The user navigates to \"new\" page$", () -> {
            newPage = NewPage.to(webDriver);
            this.currentPage = newPage;
        });
        And("^Enters item name \"([^\"]*)\"$", (String name) -> {
            newPage.submitFormName(name);
        });
        And("^Enters location \"([^\"]*)\"$", (String location) -> {
            newPage.submitFormLocation(location);
        });
        And("^Presses \"Add\"$", () -> {
            redirectedPage = newPage.pressAdd(HomePage.class);
            this.currentPage = homePage;
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
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}