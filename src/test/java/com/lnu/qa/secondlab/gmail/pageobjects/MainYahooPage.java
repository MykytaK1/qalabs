package com.lnu.qa.secondlab.gmail.pageobjects;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@AllArgsConstructor
public class MainYahooPage {

    private WebDriver driver;

    public void openSentMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Sent']"))
                .click();
    }

}
