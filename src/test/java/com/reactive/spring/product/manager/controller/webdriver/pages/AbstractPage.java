package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AbstractPage {

    public static int port = 0;
    public WebDriver driver;
    @FindBy(tagName = "body")
    private WebElement body;

    public AbstractPage(WebDriver driver) {
        setDriver(driver);
    }

    static void get(WebDriver driver, String relativeUrl) {
        driver.get("http://localhost"
                + (port > 0 ? ":" + port : "")
                + "/" + relativeUrl);
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getBody() {
        return body.getText();
    }
}
