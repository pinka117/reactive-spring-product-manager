package com.reactive.spring.product.manager.controller.steps.modify;

import com.reactive.spring.product.manager.controller.steps.CommonWebSteps;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import com.reactive.spring.product.manager.controller.webdriver.pages.NewPage;
import com.reactive.spring.product.manager.model.Item;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModifyWebSteps extends CommonWebSteps {
    public ModifyWebSteps() {
        super();
        And("^The user clicks \"Modify\" on id \"([^\"]*)\"$", (String id) -> {
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
