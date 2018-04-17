package com.reactive.spring.product.manager.e2e.steps.view;

import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

public class ViewWebE2ESteps extends CommonWebE2ESteps {
    public ViewWebE2ESteps() {
        And("^The db is not empty$", () -> {
            try {
                post("id1", "name1", "location1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    @Before
    public void setup() {
        setPort();
        this.restTemplate = new RestTemplate();
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
