package com.lnu.qa.secondlab;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class NewMessageModal {

    private final WebDriver driver;

    public NewMessageModal(WebDriver driver) {
        this.driver = driver;
    }

    protected void openMessageWindow() {
        driver.findElement(By.className("z0")).click();
    }

    protected void fillMessage(String title, String message, String... email) {
        fillMessageReceiver(email);
        driver.findElement(By.className("aoT")).sendKeys(title);
        driver.findElement(By.className("Am")).sendKeys(message);
    }

    protected void fillMessageReceiver(String... emails) {
        if (emails.length > 0) {
            driver.findElement(By.className("vO")).sendKeys(emails[0]);
        } else {
            driver.findElement(By.className("vO")).sendKeys("yurii.malskyi.mpz.2020@lpnu.ua");
        }
    }

    protected void clearMessageReceiver(String email) {
        driver.findElement(By.className("aoD")).click();
        for (int i = 0; i < email.toCharArray().length; i++) {
            driver.findElement(By.className("vO")).sendKeys(Keys.BACK_SPACE);
        }
    }

    protected void closeMessageWindow() {
        driver.findElement(By.className("Ha")).click();
    }

    protected void sendMessage() throws InterruptedException {
        System.out.println("Gonna submit the mail");
        Thread.sleep(1000);

        driver.findElement(By.className("aoO")).click();
        Thread.sleep(1000);
    }

    protected void performDefaultSendMessageProcess(String title, String message, String email) throws InterruptedException {
        openMessageWindow();

        fillMessage(title, message, email);

        sendMessage();
    }

}
