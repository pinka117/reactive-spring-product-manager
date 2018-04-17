package com.reactive.spring.product.manager.controller.steps.delete;

import com.reactive.spring.product.manager.controller.steps.CommonWebSteps;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.model.Item;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteWebSteps extends CommonWebSteps {
    public DeleteWebSteps() {
        super();
        When("^The user click \"Delete\" on id \"([^\"]*)\"$", (String id) -> {
            homePage = HomePage.to(webDriver);
            homePage.clickDelete(id);
        });
    }

    @Before
    public void setup() {
        setPort();
        setUserDb();
        setItemDb();
        Item item = new Item("1", "new name", "new location");
        mongoTemplate.insert(item);
    }

    @After
    public void tearDown() {
        logout();
    }
}
