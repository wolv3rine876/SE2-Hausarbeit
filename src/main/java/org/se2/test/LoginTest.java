package org.se2.test;

import org.junit.jupiter.api.*;

import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    // set this path to your local geckodriver
    private static final String geckodriver = "E:\\webdriver\\geckodriver.exe";
    private static final String chromedriver = "E:\\webdriver\\chromedriver.exe";
    private final String baseUrl = "http://localhost:8080";

    private static WebDriver driver = null;

    @BeforeAll
    public static void driverPathSetup() {
        System.setProperty("webdriver.gecko.driver", geckodriver);
        System.setProperty("webdriver.chrome.driver", chromedriver);
        // driver = new FirefoxDriver();
        driver = new ChromeDriver();
    }
    @BeforeEach
    public void driverSetup() throws InterruptedException {
        Thread.sleep(2000);
        driver.get(baseUrl);
        Thread.sleep(2000);
        driver.navigate().refresh();
    }

    @Test
    public void loginTest() throws InterruptedException {

        String idEmail = "emailField";
        String idPassword = "passwordField";
        String idButton = "loginButton";

        WebDriverWait wait = new WebDriverWait(driver, 25);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(idEmail)));

        driver.findElement(By.id(idEmail)).sendKeys("mr.olypia@iron-pump.com");
        Thread.sleep(1000);
        driver.findElement(By.id(idPassword)).sendKeys("IWillBeBack");
        Thread.sleep(1000);
        driver.findElement(By.id(idButton)).click();
        Thread.sleep(2000);

        assertEquals(baseUrl + "/search", driver.getCurrentUrl());
    }

    @AfterAll
    public static void cleanUp() {
        driver.close();
    }
}
