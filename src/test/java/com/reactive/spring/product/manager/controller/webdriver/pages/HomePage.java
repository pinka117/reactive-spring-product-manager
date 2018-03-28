package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {



    @FindBy(id = "login")
    private WebElement loginLink;
    @FindBy(id = "Modify")
    private WebElement modifyButton;
    @FindBy(id = "Remove")
    private WebElement removeButton;
    @FindBy(id = "Add")
    private WebElement addButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage to(WebDriver driver) {
        get(driver, "");
        return PageFactory.initElements(driver, HomePage.class);
    }


    public <T> T pressLogin(Class<T> resultPage) {
        this.loginLink.click();
        return PageFactory.initElements(driver, resultPage);
    }

    public boolean modifyIsEnabled() {
        return modifyButton.isEnabled();
    }

    public boolean removeIsEnabled() {
        return removeButton.isEnabled();
    }

    public boolean addIsEnabled() {
        return addButton.isEnabled();
    }

}
