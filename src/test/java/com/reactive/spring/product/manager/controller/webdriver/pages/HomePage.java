package com.reactive.spring.product.manager.controller.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {

    @FindBy(id = "login")
    private WebElement loginLink;
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
        WebElement mod = driver.findElement(By.name("modify"));
        return mod.isEnabled();
    }

    public boolean removeIsEnabled() {
        WebElement mod = driver.findElement(By.name("remove"));
        return mod.isEnabled();
    }

    public boolean addIsEnabled() {
        return addButton.isEnabled();
    }

    public <T> T clickModify(String id, Class<T> resultPage) {
        WebElement mod = driver.findElement(By.id("modify" + id));
        mod.click();
        return PageFactory.initElements(driver, resultPage);
    }

    public void clickDelete(String id) {
        WebElement del = driver.findElement(By.id("delete" + id));
        del.click();
    }
}
