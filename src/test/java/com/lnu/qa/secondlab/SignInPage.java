package com.lnu.qa.secondlab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SignInPage {

    private final WebDriver driver;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void signIn(String email, String password) {
        driver.findElement(By.name("identifier")).sendKeys(email);

        driver.findElement(By.className("VfPpkd-RLmnJb")).click();

        driver.findElement(By.name("password")).sendKeys(password);

//        Thread.sleep(500);
        // !!
        // Якщо нажимати занадто швидко(роботизовано) - кнопки із сторінки введення пошти не встигають знищитись!
        // В такому випадку необхідна кнопка[4], у випадку коли вони встигли знищитись - кнопка[1].
        List<WebElement> submitButtons = driver.findElements(By.tagName("button"));
        if (submitButtons.size() == 3) {
            driver.findElements(By.tagName("button")).get(1).click();
        } else {
            driver.findElements(By.tagName("button")).get(4).click();
        }
    }

}
