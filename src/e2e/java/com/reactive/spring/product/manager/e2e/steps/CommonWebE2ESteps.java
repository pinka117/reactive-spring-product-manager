package com.reactive.spring.product.manager.e2e.steps;

import com.reactive.spring.product.manager.controller.webdriver.pages.*;
import com.reactive.spring.product.manager.model.Item;
import cucumber.api.java8.En;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class CommonWebE2ESteps implements En {
    private static final String API = "/api";
    protected static int port =
            Integer.parseInt(System.getProperty("server.port", "8080"));
    protected static String url = "http://localhost:" + port;
    protected WebDriver webDriver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected NewPage newPage;
    protected AbstractPage redirectedPage;
    protected AbstractPage currentPage;
    protected AdminHomePage adminHomePage;
    private static final String SAVE_ENDPOINT = url + API + "/items/new";
    protected RestTemplate restTemplate;

    public CommonWebE2ESteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
        Given("^The user logged in as an admin$", () -> {
            gotoLoginPage();
            validLogin();
            pressLogin();
        });
        Then("^The user is redirected to Home Page$", () -> {

            assertThat(redirectedPage).isInstanceOf(HomePage.class);
        });
        Then("^A message \"([^\"]*)\" must be shown$", (String msg) -> {
            assertThat(currentPage.getBody()).contains(msg);
        });
        Given("^The user isn't logged in$", () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication == null)) {
                fail("User isn't logged out");
            }
        });
        When("^The user navigates on \"new\" page$", () -> {
            newPage = NewPage.to(webDriver);
            this.currentPage = newPage;
        });
        And("^There is an item with id \"([^\"]*)\" and name \"([^\"]*)\" and location \"([^\"]*)\"$", (String id, String name, String location) -> {
            homePage = HomePage.to(webDriver);
            assertTrue(homePage.getBody().contains(id));
            assertTrue(homePage.getBody().contains(name));
            assertTrue(homePage.getBody().contains(location));
        });
        And("^There isn't an item with id \"([^\"]*)\" and name \"([^\"]*)\" and location \"([^\"]*)\"$", (String id, String name, String location) -> {
            homePage = HomePage.to(webDriver);
            boolean idexp = homePage.getBody().contains(id);
            boolean nameexp = homePage.getBody().contains(name);
            boolean locationexp = homePage.getBody().contains(location);
            assertFalse(idexp && nameexp && locationexp);
        });
    }

    protected void gotoLoginPage() {
        loginPage = LoginPage.to(webDriver);
        currentPage = loginPage;
    }

    protected void pressLogin() {
        redirectedPage = loginPage.pressLogin(HomePage.class);
    }

    protected void validLogin() {
        loginPage.submitForm("admin", "admin");
    }

    protected void post(String id, String name, String location) throws JSONException {
        String plainCreds = "admin:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        JSONObject request = new JSONObject();
        request.put("id", id);
        request.put("name", name);
        request.put("location", location);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
        restTemplate.postForObject(SAVE_ENDPOINT, entity, Item.class);
    }

    protected void setPort() {
        AbstractPage.port = port;
        webDriver = new ChromeDriver();
    }
}
