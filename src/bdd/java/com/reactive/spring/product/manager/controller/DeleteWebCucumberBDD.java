package com.reactive.spring.product.manager.controller;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/bdd/resources/delete.feature", glue = "com.reactive.spring.product.manager.controller.steps.delete")
public class DeleteWebCucumberBDD {
}
