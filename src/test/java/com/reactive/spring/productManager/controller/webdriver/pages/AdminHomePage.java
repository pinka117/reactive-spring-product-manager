package com.reactive.spring.productManager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminHomePage extends HomePage {

    @FindBy(id = "Logout")
    private WebElement disconnectButton;

    public AdminHomePage(WebDriver driver) {
        super(driver);
    }

    public static AdminHomePage to(WebDriver driver) {
        get(driver, "");
        return PageFactory.initElements(driver, AdminHomePage.class);
    }

    public <T> T pressLogout(Class<T> resultPage) {
        this.disconnectButton.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
