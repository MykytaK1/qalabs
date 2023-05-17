package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import com.lnu.qa.secondlab.TestUtils;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@AllArgsConstructor
public class SignInPage {

    private WebDriver driver;

    @Step("Sign In")
    public void signIn(String email, String password) {
        driver
                .findElement(By.xpath("//input[@id='login-username']"))
                .sendKeys(email);
        driver
                .findElement(By.xpath("//input[@id='login-signin']"))
                .click();
        driver
                .findElement(By.xpath("//input[@id='login-passwd']"))
                .sendKeys(password);
        driver
                .findElement(By.xpath("//button[@id='login-signin']"))
                .click();
    }

}
