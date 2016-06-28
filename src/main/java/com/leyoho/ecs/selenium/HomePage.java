package com.leyoho.ecs.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Page Object encapsulates the Home Page
 *
 * Created by Victor Feng.
 */
public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals("Home Page of logged in user")) {
            throw new IllegalStateException("This is not Home Page of logged in user, current page is: "
                    + driver.getCurrentUrl());
        }
    }

    public HomePage manageProfile() {
        // Page encapsulation to manage profile functionality
        return new HomePage(driver);
    }
}
