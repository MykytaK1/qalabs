package com.lnu.qa.secondlab.mail.yahoo;

import com.lnu.qa.secondlab.ChromeTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.SignInPage;

public class YahooMailTest extends ChromeTest {

    public void logIn(){
        var driver = getWebDriver();
        driver.get(getYahooEmailUrl());

        var signInPage = new SignInPage(getWebDriver());
        signInPage.signIn(getYahooEmailAddress(), getYahooEmailPassword());
    }

    public String getYahooEmailAddress() {
        return getProperties().getProperty("yahoo.email.address");
    }

    public String getYahooEmailPassword() {
        return getProperties().getProperty("yahoo.email.password");
    }

    public String getYahooEmailUrl() {
        return getProperties().getProperty("yahoo.email.url");
    }

    public String getReceiverEmailAddress() {
        return getProperties().getProperty("receiver.email");
    }

}
