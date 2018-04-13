package com.reactive.spring.product.manager.e2e;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/bdd/resources/view.feature", glue = "com.reactive.spring.product.manager.e2e.steps.view")
public class ModifyWebE2E {
    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance()
                .setup();
    }
}
