package com.reactive.spring.product.manager.controller;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/bdd/resources/add.feature", glue = "com.reactive.spring.product.manager.controller.steps.add")
public class AddWebCucumberBDD {
}
