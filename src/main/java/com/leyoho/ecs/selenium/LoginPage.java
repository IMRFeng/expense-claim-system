package com.leyoho.ecs.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object encapsulates the Sign-in page.
 *
 * Created by Victor Feng.
 */
public class LoginPage {

    private WebDriver driver;
    private WebElement element;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.driver.get("");
        if (!driver.getTitle().equals("Sign in page")) {
            throw new IllegalStateException("This is not sign in page, current page is: "
                    + driver.getCurrentUrl());
        }
    }

    public HomePage loginValidUser(String userName, String password) {

        element = this.driver.findElement(By.name("userName"));
        element.sendKeys(userName);

        element = this.driver.findElement(By.name("password"));
        element.sendKeys(password);

        element.submit();

        return new HomePage(driver);
    }

}
