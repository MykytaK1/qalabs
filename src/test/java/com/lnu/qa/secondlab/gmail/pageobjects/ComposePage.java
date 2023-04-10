package com.lnu.qa.secondlab.gmail.pageobjects;

import com.lnu.qa.secondlab.gmail.TestUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@AllArgsConstructor
public class ComposePage {

    private WebDriver driver;

    public void composeNewMessage(String to, String subject, String message) {
        driver
                .findElement(By.xpath("//div[text()='Compose']"))
                .click();
        driver
                .findElement(By.xpath("//form[@enctype='multipart/form-data']//input[@class='agP aFw']"))
                .sendKeys(to);
        driver
                .findElement(By.xpath("//form[@enctype='multipart/form-data']//input[@name='subjectbox']"))
                .sendKeys(subject);
        driver
                .findElement(By.xpath("//div[@aria-label='Message Body']"))
                .sendKeys(message);
        TestUtils.sleep(Duration.ofSeconds(3));


    }

    public void closeNewMessage() {
        driver
                .findElement(By.xpath("//div[@aria-label='Save & close']"))
                .click();
    }

    public void sendNewMessage() {
        driver
                .findElement(By.xpath("//div[text()='Send']"))
                .click();
    }

}
