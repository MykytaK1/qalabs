package com.lnu.qa.secondlab.gmail.pageobjects;

import com.lnu.qa.secondlab.gmail.TestUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@AllArgsConstructor
public class SignInYahooPage {

    private WebDriver driver;

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
        TestUtils.sleep(Duration.ofSeconds(3));

    }

}
