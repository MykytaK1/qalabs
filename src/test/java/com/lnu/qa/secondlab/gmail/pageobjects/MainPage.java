package com.lnu.qa.secondlab.gmail.pageobjects;

import com.lnu.qa.secondlab.gmail.TestUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@AllArgsConstructor
public class MainPage {

    private WebDriver driver;

    public void openSentMessages() {
        driver
                .findElement(By.xpath("//div[@data-tooltip='Sent']"))
                .click();
    }

}
