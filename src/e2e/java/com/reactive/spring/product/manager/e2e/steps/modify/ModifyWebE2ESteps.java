package com.reactive.spring.product.manager.e2e.steps.modify;

import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.NewPage;
import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class ModifyWebE2ESteps extends CommonWebE2ESteps {
    public ModifyWebE2ESteps() {
        super();

        And("^The user click \"Modify\" on id \"([^\"]*)\"$", (String id) -> {
            homePage = HomePage.to(webDriver);
            redirectedPage = homePage.clickModify(id, NewPage.class);
            currentPage = redirectedPage;
        });
        And("^The user is redirected to \"edit\" page$", () -> {
            assertThat(redirectedPage).isInstanceOf(NewPage.class);
        });
        When("^Enters name \"([^\"]*)\"$", (String name) -> {
            ((NewPage) currentPage).submitFormName(name);
        });
        And("^Presses \"Ok\"$", () -> {
            redirectedPage = ((NewPage) currentPage).pressAdd(HomePage.class);
            currentPage = redirectedPage;
        });
        When("^Enters location \"([^\"]*)\"$", (String location) -> {
            ((NewPage) currentPage).submitFormLocation(location);
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
