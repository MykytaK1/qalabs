package com.lnu.qa.secondlab.gmail;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class GmailTest {
    private final static String PROPERTIES_PATH = "selenium.properties";

    @Getter
    private WebDriver webDriver;
    private Properties properties;

    private static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(SendMessageTest.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public void firefoxSession() {
        WebDriverManager.chromedriver().setup();
        properties = loadProperties();
    }

    @BeforeMethod
    public void setupTest() {
        var chromeOptions = new ChromeOptions();
        webDriver = new ChromeDriver(chromeOptions);
        WebDriver.Options options = webDriver.manage();
        options.timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    public String getEmailAddress() {
        return properties.getProperty("email.address");
    }

    public String getEmailPassword() {
        return properties.getProperty("email.password");
    }

    public String getEmailUrl() {
        return properties.getProperty("yahoo.url");
    }


}
