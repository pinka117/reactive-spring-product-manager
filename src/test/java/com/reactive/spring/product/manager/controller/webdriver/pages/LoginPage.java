package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {
    @FindBy(id = "username")
    private WebElement username;
    @FindBy(id = "password")
    private WebElement password;

    @FindBy(name = "Log in")
    private WebElement submit;

    @FindBy(id = "home page")
    private WebElement homePageLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public static LoginPage to(WebDriver driver) {
        get(driver, "/login");
        return PageFactory.initElements(driver, LoginPage.class);
    }


    public void submitForm(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);

    }

    public <T> T pressLogin(Class<T> resultPage) {
        this.submit.click();
        return PageFactory.initElements(driver, resultPage);
    }

    public <T> T pressBack(Class<T> resultPage) {
        this.homePageLink.click();
        return PageFactory.initElements(driver, resultPage);
    }

}