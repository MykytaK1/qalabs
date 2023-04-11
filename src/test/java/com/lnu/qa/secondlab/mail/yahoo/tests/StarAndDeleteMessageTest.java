package com.lnu.qa.secondlab.mail.yahoo.tests;

import com.lnu.qa.secondlab.mail.yahoo.YahooMailTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MainPage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MessagesActionPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class StarAndDeleteMessageTest extends YahooMailTest {

    @Test
    public void messageShouldBeSentAndRemoved() {
        //LogIn
        logIn();
        //Open inbox
        var mainPage = new MainPage(getWebDriver());
        mainPage.openInboxMessages();
        //Star messages
        var messagesActionPage = new MessagesActionPage(getWebDriver());
        var messages = messagesActionPage.getMessages()
                .stream()
                .limit(3).toList();
        var subjects = messages.stream()
                .map(WebElement::getText).toList();
        messages.forEach(messagesActionPage::selectMessage);
        messagesActionPage.starMessages();
         sleep();
        //Open starred messages
        mainPage.openStarredMessages();
         sleep();
        subjects.stream()
                .map(messagesActionPage::findMessage)
                .forEach(messagesActionPage::selectMessage);
         sleep();
        //Select and delete messages
        messagesActionPage.deleteMessage();
        sleep();
    }
}
