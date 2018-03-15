package com.reactive.spring.productManager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {

    @FindBy(id = "item_table")
    private WebElement itemTable;

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

}
