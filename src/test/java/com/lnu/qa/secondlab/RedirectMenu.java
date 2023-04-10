package com.lnu.qa.secondlab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RedirectMenu {

    public static void redirectTo(WebDriver driver, EmailTab tab) {
        switch (tab) {
            case INBOX_TAB: {
                driver.findElements(By.className("aim")).get(0).click();
                break;
            }
            case STARRED_TAB: {
                driver.findElements(By.className("aim")).get(1).click();
                break;
            }
            case SNOOZED_TAB: {
                driver.findElements(By.className("aim")).get(2).click();
                break;
            }
            case SENT_TAB: {
                driver.findElements(By.className("aim")).get(3).click();
                break;
            }
            case DRAFTS_TAB: {
                driver.findElements(By.className("aim")).get(4).click();
                break;
            }
            default: {
                break;
            }
        }
    }

}
