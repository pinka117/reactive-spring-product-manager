package com.reactive.spring.product.manager.controller.steps.view;

import com.reactive.spring.product.manager.controller.steps.CommonWebSteps;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.model.Item;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewWebSteps extends CommonWebSteps {
    public ViewWebSteps() {
        super();
        And("^The db is not empty$", () -> {
            Item it = new Item("id1", "name1", "location1");
            Mono<Item> i = itemService.add(it);
            i.subscribe();
        });
        Then("^Items are displayed$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
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
        setUserDb();
        setItemDb();
    }

    @After
    public void tearDown() {
        logout();
    }
}
