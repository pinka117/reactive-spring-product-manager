package com.reactive.spring.product.manager.e2e.steps.delete;

import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

public class DeleteWebE2ESteps extends CommonWebE2ESteps {
    public DeleteWebE2ESteps() {
        super();
        When("^The user clicks \"Delete\" on id \"([^\"]*)\"$", (String id) -> {
            homePage = HomePage.to(webDriver);
            homePage.clickDelete(id);
        });
    }

    @Before
    public void setup() {
        this.restTemplate = new RestTemplate();
        setPort();
        try {
            post("1", "new name", "new location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}