package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewPage extends AbstractPage {

    @FindBy(id = "name")
    private WebElement name;
    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "Add")
    private WebElement submit;


    public NewPage(WebDriver driver) {
        super(driver);
    }

    public static NewPage to(WebDriver driver) {
        get(driver, "/new");
        return PageFactory.initElements(driver, NewPage.class);
    }

    public void submitFormName(String name) {
        this.name.clear();
        this.name.sendKeys(name);
    }

    public void submitFormLocation(String location) {
        this.location.clear();
        this.location.sendKeys(location);
    }

    public <T> T pressAdd(Class<T> resultPage) {
        this.submit.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
