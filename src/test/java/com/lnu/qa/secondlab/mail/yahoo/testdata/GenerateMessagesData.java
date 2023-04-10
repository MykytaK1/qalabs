package com.lnu.qa.secondlab.mail.yahoo.testdata;

import com.lnu.qa.secondlab.mail.yahoo.YahooMailTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.ComposePage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MainPage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.SentMessagesPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.lnu.qa.secondlab.TestUtils.uuid;

public class GenerateMessagesData extends YahooMailTest {


    @Test
    public void messageShouldBeSent() {
        logIn();
        var composeView = new ComposePage(getWebDriver());
        for (int i = 0; i < 50; i++) {
            composeView.composeNewMail(getYahooEmailAddress(),  uuid(), uuid());
            composeView.sendNewMail();
        }
    }
}
