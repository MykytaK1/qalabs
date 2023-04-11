package com.lnu.qa.secondlab;

import com.lnu.qa.secondlab.mail.yahoo.tests.SendMessageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ChromeTest {
    private final static String PROPERTIES_PATH = "selenium.properties";

    private ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    @Getter
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
        System.out.println(Thread.currentThread());
    }

    @BeforeMethod
    public void setupTest() {
        var chromeOptions = new ChromeOptions();
        webDriverThreadLocal.set(new ChromeDriver(chromeOptions));
        WebDriver.Options options = getWebDriver().manage();
        options.timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(properties.get("selenium.wait.time").toString())));
    }

    @AfterMethod
    public void tearDown() {
        getWebDriver().quit();
    }

    public WebDriver getWebDriver() {
        return webDriverThreadLocal.get();
    }

    public void sleep(){
        sleep(Integer.parseInt(properties.get("tests.sleep.time").toString()));
    }

    public void sleep(int seconds){
        TestUtils.sleep(Duration.ofSeconds(seconds));
    }

}
