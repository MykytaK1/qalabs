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
                .findElement(By.xpath("//input[@id='identifierId']"))
                .sendKeys(email);
        driver
                .findElement(By.xpath("//div[@id='identifierNext']"))
                .click();
        driver
                .findElement(By.xpath("//input[@name='Passwd']"))
                .sendKeys(password);
        driver
                .findElement(By.xpath("//div[@id='passwordNext']"))
                .click();
        TestUtils.sleep(Duration.ofSeconds(3));

    }

}
