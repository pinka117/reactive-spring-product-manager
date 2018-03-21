package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {

    @FindBy(id = "item_table")
    private WebElement itemTable;

    @FindBy(id = "login")
    private WebElement loginLink;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage to(WebDriver driver) {
        get(driver, "");
        return PageFactory.initElements(driver, HomePage.class);
    }


    public String getItemTableAsString() {
        return itemTable.getText();
    }

    public <T> T pressLogin(Class<T> resultPage) {
        this.loginLink.click();
        return PageFactory.initElements(driver, resultPage);
    }

}
