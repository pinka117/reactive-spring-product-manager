package com.reactive.spring.product.manager.e2e.steps.modify;

import com.reactive.spring.product.manager.e2e.steps.CommonWebE2ESteps;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ModifyWebE2ESteps extends CommonWebE2ESteps {
    public ModifyWebE2ESteps() {
        super();
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
